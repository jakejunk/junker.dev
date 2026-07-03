package dev.junker.maze

import dev.junker.maze.cell.MazeCell
import dev.junker.maze.cell.WallDirection
import kotlin.random.Random

class Maze private constructor(
    val seed: Int,
    val sideLength: Int,
    val cells: List<MazeCell>,
    val startIndex: Int,
    val endIndex: Int
) {
    fun Int.toPosition(): Position {
        return Position(this % sideLength, this / sideLength)
    }

    fun Position.toIndex(): Int? {
        val i = x + y * sideLength

        return when {
            i >= cells.size -> null
            else -> i
        }
    }

    fun neighbors(index: Int): List<Int> {
        val cell = cells[index]
        val position = index.toPosition()

        return buildList {
            if (!cell.hasWall(WallDirection.NORTH) && position.y > 0) {
                add(index - sideLength)
            }

            if (!cell.hasWall(WallDirection.SOUTH) && position.y < sideLength - 1) {
                add(index + sideLength)
            }

            if (!cell.hasWall(WallDirection.EAST) && position.x < sideLength - 1) {
                add(index + 1)
            }

            if (!cell.hasWall(WallDirection.WEST) && position.x > 0) {
                add(index - 1)
            }
        }
    }

    fun distancesFromIndex(start: Int): IntArray {
        val distances = IntArray(cells.size) { -1 }
        distances[start] = 0

        val stack = ArrayDeque<Int>()
        stack.addLast(start)

        while (stack.isNotEmpty()) {
            val current = stack.removeLast()

            for (neighbor in neighbors(current)) {
                if (distances[neighbor] == -1) {
                    distances[neighbor] = distances[current] + 1
                    stack.addLast(neighbor)
                }
            }
        }

        return distances
    }

    companion object {
        fun simple(
            seed: Int,
            sideLength: Int
        ): Maze {
            if (sideLength < 2) {
                throw IllegalArgumentException("sideLength must be at least 2")
            }

            val mazeCells = MutableList(sideLength * sideLength) { MazeCell.ALL_FLAGS }
            val startMaze = Maze(seed, sideLength, mazeCells, 0, mazeCells.lastIndex)
            val uniqueRooms = ArrayList<MutableList<Position>>()
            val wallsToRemove = ArrayList<Wall>()

            for (y in 0..<sideLength)
            {
                for (x in 0..<sideLength)
                {
                    val p = Position(x, y)

                    uniqueRooms.add(MutableList(1) { p })

                    if (x + 1 < sideLength) {
                        wallsToRemove.add(Wall(p, p.plusX(1), WallDirection.EAST))
                    }

                    if (y + 1 < sideLength) {
                        wallsToRemove.add(Wall(p, p.plusY(1), WallDirection.SOUTH))
                    }
                }
            }

            val rng = Random(seed)

            while (wallsToRemove.isNotEmpty()) {
                val i = rng.nextInt(wallsToRemove.size)

                val wall = wallsToRemove.removeAt(i)

                val aRoomIndex = uniqueRooms.indexOfFirst { set -> set.contains(wall.aRoomPosition) }
                val bRoomIndex = uniqueRooms.indexOfFirst { set -> set.contains(wall.bRoomPosition) }

                if (aRoomIndex == bRoomIndex) {
                    continue
                }

                with(startMaze) {
                    val aCellIndex = wall.aRoomPosition.toIndex()!!
                    val bCellIndex = wall.bRoomPosition.toIndex()!!

                    mazeCells[aCellIndex] = mazeCells[aCellIndex].removeWall(wall.aWall)
                    mazeCells[bCellIndex] = mazeCells[bCellIndex].removeWall(wall.bWall)
                }

                val toMerge = uniqueRooms.removeAt(bRoomIndex)

                val targetIndex = when {
                    bRoomIndex < aRoomIndex -> aRoomIndex - 1
                    else -> aRoomIndex
                }

                uniqueRooms[targetIndex].addAll(toMerge)
            }

            val (startIndex, endIndex) = determineLongestPath(startMaze)

            return Maze(
                seed = startMaze.seed,
                sideLength = startMaze.sideLength,
                cells = mazeCells,
                startIndex = startIndex,
                endIndex = endIndex
            )
        }

        fun determineLongestPath(maze: Maze): Pair<Int, Int> {
            val a = maze.distancesFromIndex(0).let { it.indices.maxBy { i -> it[i] } }
            val b = maze.distancesFromIndex(a).let { it.indices.maxBy { i -> it[i] } }

            return a to b
        }
    }
}

private data class Wall(
    val aRoomPosition : Position,
    val bRoomPosition : Position,
    val aWall: WallDirection
) {
    val bWall: WallDirection
        get() = aWall.opposite()
}
