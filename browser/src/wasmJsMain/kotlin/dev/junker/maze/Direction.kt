package dev.junker.maze

enum class Direction {
    HORIZONTAL,
    VERTICAL;

    override fun toString(): String {
        return when (this) {
            HORIZONTAL -> "_"
            VERTICAL -> "|"
        }
    }
}

fun String?.tryParseDirection(): Direction? {
    return when (this) {
        "_" -> Direction.HORIZONTAL
        "|" -> Direction.VERTICAL
        else -> null
    }
}
