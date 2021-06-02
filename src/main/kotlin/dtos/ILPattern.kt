package dtos

class ILPattern(
    var pattern: String,
    var type: String,
    var scopes: List<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ILPattern

        if (pattern != other.pattern) return false
        if (type != other.type) return false
        if (scopes != other.scopes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pattern.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + scopes.hashCode()
        return result
    }
}