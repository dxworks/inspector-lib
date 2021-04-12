package dtos

data class Dependency (
    var name: String? = null,
    var version: String? = null,
    var provider: String? = null,
    var url: String? = null
)
