package dev.junker.splice

enum class Direction {
    VERTICAL,
    HORIZONTAL;

    override fun toString(): String {
        return when (this) {
            VERTICAL -> "|"
            HORIZONTAL -> "_"
        }
    }
}
