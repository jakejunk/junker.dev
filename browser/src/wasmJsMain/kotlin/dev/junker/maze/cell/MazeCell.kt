package dev.junker.maze.cell

import dev.junker.BitField16

enum class WallDirection(val index: UShort) {
    NONE(0u),
    NORTH(1u),
    EAST(2u),
    SOUTH(4u),
    WEST(8u);

    companion object {
        val ALL: UShort
            get() = (NORTH.index + EAST.index + SOUTH.index + WEST.index).toUShort()
    }

    fun opposite(): WallDirection {
        return when (this) {
            NONE -> NONE
            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
        }
    }
}

value class MazeCell(
    val value: BitField16 = BitField16(WallDirection.NONE.index),
) {
    fun hasWall(wall: WallDirection): Boolean {
        return value.getBit(wall.index.toInt())
    }

    fun addWall(wall: WallDirection): MazeCell {
        return MazeCell(value.setBit(wall.index.toInt(), true))
    }

    fun removeWall(wall: WallDirection): MazeCell {
        return MazeCell(value.setBit(wall.index.toInt(), false))
    }
}
