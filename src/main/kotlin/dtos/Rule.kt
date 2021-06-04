package dtos

class Rule(
    var tags: List<String> = emptyList(),
    var patterns: List<ILPattern> = emptyList(),
    var applies_to: List<String> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rule

        if (tags != other.tags) return false
        if (patterns != other.patterns) return false
        if (applies_to != other.applies_to) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tags.hashCode()
        result = 31 * result + patterns.hashCode()
        result = 31 * result + applies_to.hashCode()
        return result
    }
}