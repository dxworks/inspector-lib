package dtos

class Rule(
    var tags: List<String> = emptyList(),
    var patterns: List<ILPattern> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rule

        if (tags != other.tags) return false
        if (patterns != other.patterns) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tags.hashCode()
        result = 31 * result + patterns.hashCode()
        return result
    }
}