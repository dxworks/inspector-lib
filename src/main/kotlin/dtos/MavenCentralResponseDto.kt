package dtos

import com.google.api.client.util.Key

class MavenCentralResponseDto {
    @Key
    var response: MavenCentralResponsesDto? = null
}

class MavenCentralResponsesDto {
    @Key
    var numFound = 0

    @Key
    var start = 0

    @Key
    var docs: List<MavenCentralResponsesDocsDto>? = emptyList()
}

class MavenCentralResponsesDocsDto {
    @Key
    var id: String? = null

    @Key
    var g: String? = null

    @Key
    var a: String? = null

    @Key
    var v: String? = null

    @Key
    var j: String? = null

    @Key
    var timestamp: Long = 0

    @Key
    var tags: List<String>? = emptyList()
}