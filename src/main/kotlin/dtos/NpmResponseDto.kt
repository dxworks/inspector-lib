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
    var versions: Map<String, NpmResponseVersionDto> = emptyMap()

    @Key
    var time: Map<String, String> = emptyMap()

}

class NpmResponseVersionDto : GenericJson() {
    @Key
    var name: String? = null

    @Key
    var version: String? = null

    @Key("_id")
    var id: String? = null
}