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

    @Key("upload_time_iso_8601")
    var uploadTimeIso8601: String? = null
}

class PypiResponseInfoDto {
    @Key("bugtrack_url")
    var bugtrackUrl: String? = null

    @Key("download_url")
    var downloadUrl: String? = null

    @Key("home_page")
    var homePage: String? = null

    @Key
    var keywords: String? = null

    @Key
    var license: String? = null

    @Key
    var name: String? = null

    @Key("package_url")
    var packageUrl: String? = null

    @Key("project_url")
    var projectUrl: String? = null

    @Key("release_url")
    var releaseUrl: String? = null

    @Key("requires_dist")
    var requiresDist: List<String>? = emptyList()

    @Key
    var summary: String? = null

    @Key
    var version: String? = null
}
