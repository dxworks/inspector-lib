package dtos

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class NpmResponseDto : GenericJson() {
    @Key
    var name: String? = null

    @Key
    var description: String? = null

    @Key("dist-tags")
    var distTags: Map<String, String>? = emptyMap()

    @Key
    var license: String? = null

    @Key
    var homepage: String? = null

    @Key
    var repository: NpmResponseRepositoryDto? = null

    @Key
    var bugs: NpmResponseIssueTrackingDto? = null

    @Key
    var versions: Map<String, NpmResponseVersionDto> = emptyMap()

    @Key
    var time: Map<String, String> = emptyMap()

}

class NpmResponseIssueTrackingDto : GenericJson() {
    @Key
    var url: String? = null
}

class NpmResponseVersionDto : GenericJson() {
    @Key
    var name: String? = null

    @Key
    var version: String? = null

    @Key("_id")
    var id: String? = null
}

class NpmResponseRepositoryDto : GenericJson() {
    @Key
    var type: String? = null

    @Key
    var url: String? = null
}