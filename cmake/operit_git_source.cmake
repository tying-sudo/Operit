include_guard(GLOBAL)

include(FetchContent)

function(operit_normalize_source_token out_var token)
    string(TOLOWER "${token}" normalized_token)
    string(REGEX REPLACE "[^a-z0-9_.-]" "_" normalized_token "${normalized_token}")
    set(${out_var} "${normalized_token}" PARENT_SCOPE)
endfunction()

function(operit_git_ref_var out_var dependency_name git_ref)
    string(TOUPPER "${dependency_name}" dependency_key)
    string(REPLACE "-" "_" dependency_key "${dependency_key}")

    set(ref_var "OPERIT_${dependency_key}_GIT_REF")
    if(DEFINED ENV{${ref_var}})
        # 本地构建环境变量覆盖（离线构建/锁定依赖版本时使用）
        set(${ref_var} "$ENV{${ref_var}}" CACHE STRING "Git ref used to fetch ${dependency_name}" FORCE)
    elseif(NOT DEFINED ${ref_var})
        set(${ref_var} "${git_ref}" CACHE STRING "Git ref used to fetch ${dependency_name}")
    elseif(NOT "${${ref_var}}" STREQUAL "${git_ref}")
        # 调用点钉版 SHA 更新后迁移旧缓存（含由分支名创建的历史缓存）
        set(${ref_var} "${git_ref}" CACHE STRING "Git ref used to fetch ${dependency_name}" FORCE)
    endif()

    set(${out_var} "${ref_var}" PARENT_SCOPE)
endfunction()

function(operit_resolve_git_ref out_var repository git_ref)
    string(LENGTH "${git_ref}" git_ref_length)
    if(git_ref_length EQUAL 40 AND "${git_ref}" MATCHES "^[0-9a-fA-F]+$")
        string(TOLOWER "${git_ref}" resolved_sha)
        set(${out_var} "${resolved_sha}" PARENT_SCOPE)
        return()
    endif()

    execute_process(
        COMMAND git ls-remote "${repository}" "${git_ref}" "refs/heads/${git_ref}" "refs/tags/${git_ref}"
        OUTPUT_VARIABLE remote_refs
        ERROR_VARIABLE remote_error
        RESULT_VARIABLE remote_result
        OUTPUT_STRIP_TRAILING_WHITESPACE
        ERROR_STRIP_TRAILING_WHITESPACE
    )

    if(NOT remote_result EQUAL 0 OR "${remote_refs}" STREQUAL "")
        message(FATAL_ERROR "Unable to resolve ${repository} ref '${git_ref}': ${remote_error}")
    endif()

    string(REGEX MATCH "^[0-9a-fA-F]+" resolved_sha "${remote_refs}")
    if("${resolved_sha}" STREQUAL "")
        message(FATAL_ERROR "Unable to parse ${repository} ref '${git_ref}' from: ${remote_refs}")
    endif()

    string(TOLOWER "${resolved_sha}" resolved_sha)
    set(${out_var} "${resolved_sha}" PARENT_SCOPE)
endfunction()

function(operit_github_archive_url out_var repository resolved_sha)
    set(repository_without_suffix "${repository}")
    string(REGEX REPLACE "\\.git$" "" repository_without_suffix "${repository_without_suffix}")
    string(REGEX REPLACE "^https://github.com/([^/]+)/(.+)$" "https://github.com/\\1/\\2/archive/${resolved_sha}.tar.gz" archive_url "${repository_without_suffix}")

    if("${archive_url}" STREQUAL "${repository_without_suffix}")
        message(FATAL_ERROR "Only GitHub archive sources are supported by operit_github_archive_url: ${repository}")
    endif()

    set(${out_var} "${archive_url}" PARENT_SCOPE)
endfunction()

function(operit_declare_git_source dependency_name repository git_ref)
    operit_git_ref_var(ref_var "${dependency_name}" "${git_ref}")
    operit_resolve_git_ref(resolved_sha "${repository}" "${${ref_var}}")
    operit_github_archive_url(archive_url "${repository}" "${resolved_sha}")

    set(deps_root "${CMAKE_SOURCE_DIR}/.cxx/operit_deps")
    if(DEFINED ENV{OPERIT_DEPS_ROOT})
        # Windows MAX_PATH 限制下，本地构建可用 OPERIT_DEPS_ROOT 指定短路径依赖根，
        # 并截断目录名中的 SHA 以缩短 ninja 文件中的路径长度
        set(deps_root "$ENV{OPERIT_DEPS_ROOT}")
        string(SUBSTRING "${resolved_sha}" 0 12 resolved_sha)
    endif()
    operit_normalize_source_token(source_token "${dependency_name}-${resolved_sha}")

    if(DEFINED ENV{OPERIT_DEPS_OFFLINE})
        # 离线模式：从本地归档目录提供依赖源码，不访问 GitHub
        set(archive_url "${deps_root}/archives/${source_token}.tar")
    elseif(EXISTS "${deps_root}/archives/${source_token}.tar")
        # 本地已预置归档时直接使用，避免访问 GitHub（网络受限环境）
        set(archive_url "${deps_root}/archives/${source_token}.tar")
    endif()
    set(source_dir "${deps_root}/${source_token}-src")
    set(binary_dir "${deps_root}/${source_token}-build")

    FetchContent_Declare(
        ${dependency_name}
        URL "${archive_url}"
        SOURCE_DIR "${source_dir}"
        BINARY_DIR "${binary_dir}"
        ${ARGN}
    )
endfunction()

function(operit_populate_git_source out_source_dir out_binary_dir dependency_name)
    string(TOLOWER "${dependency_name}" dependency_var_prefix)

    FetchContent_GetProperties(${dependency_name})
    if(NOT ${dependency_var_prefix}_POPULATED)
        FetchContent_Populate(${dependency_name})
    endif()

    set(${out_source_dir} "${${dependency_var_prefix}_SOURCE_DIR}" PARENT_SCOPE)
    set(${out_binary_dir} "${${dependency_var_prefix}_BINARY_DIR}" PARENT_SCOPE)
endfunction()

function(operit_prepare_git_source out_source_dir out_binary_dir dependency_name repository git_ref)
    operit_declare_git_source("${dependency_name}" "${repository}" "${git_ref}" ${ARGN})
    operit_populate_git_source(${out_source_dir} ${out_binary_dir} "${dependency_name}")
    set(${out_source_dir} "${${out_source_dir}}" PARENT_SCOPE)
    set(${out_binary_dir} "${${out_binary_dir}}" PARENT_SCOPE)
endfunction()
