package dtos

import services.createPackageUrl

data class Dependency(
    var project: String? = null,
    var name: String? = null,
    var version: String? = null,
    var provider: String? = null,
    var url: String? = null
) {
    val purl: String? by lazy { createPackageUrl(this) }

    val data: MutableMap<String, String> = LinkedHashMap()

    var libraryInformation: LibraryInformation? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Dependency

        if (name != other.name) return false
        if (version != other.version) return false
        if (provider != other.provider) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (version?.hashCode() ?: 0)
        result = 31 * result + (provider?.hashCode() ?: 0)
        return result
    }
}
