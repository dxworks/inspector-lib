package dtos

import com.google.api.client.util.Key

class PypiResponseDto {
    @Key
    var info: PypiResponseInfoDto? = null

    @Key
    var releases: Map<String, List<PypiResponseReleasesDto>>? = null
}

class PypiResponseReleasesDto {
    @Key
    var filename: String? = null

    @Key
    var upload_time: String? = null

    @Key
    var upload_time_iso_8601: String? = null
}

class PypiResponseInfoDto {
    @Key
    var bugtrack_url: String? = null

    @Key
    var download_url: String? = null

    @Key
    var home_page: String? = null

    @Key
    var keywords: String? = null

    @Key
    var license: String? = null

    @Key
    var name: String? = null

    @Key
    var package_url: String? = null

    @Key
    var project_url: String? = null

    @Key
    var release_url: String? = null

    @Key
    var requires_dist: List<String>? = emptyList()

    @Key
    var summary: String? = null

    @Key
    var version: String? = null
}
