package dev.junker.maze

data class Position(
    val x: Int,
    val y: Int
) {
    fun plusX(x: Int): Position {
        return Position(this.x + x, this.y)
    }

    fun plusY(y: Int): Position {
        return Position(this.x, this.y + y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}
