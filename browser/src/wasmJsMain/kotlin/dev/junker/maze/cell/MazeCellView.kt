package dev.junker.maze.cell

import dev.junker.*
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class MazeCellView private constructor(
    val index: Int,
    val root: HTMLElement
) {
    var currentState = MazeCell()

    fun updateWalls(to: MazeCell) {
        val current = currentState

        if (to.hasWall(WallDirection.NORTH)) {
            if (!current.hasWall(WallDirection.NORTH)) {
                root.classList.add(mazeNorthWall.className)
            }
        } else if (current.hasWall(WallDirection.NORTH)) {
            root.classList.remove(mazeNorthWall.className)
        }

        if (to.hasWall(WallDirection.SOUTH)) {
            if (!current.hasWall(WallDirection.SOUTH)) {
                root.classList.add(mazeSouthWall.className)
            }
        } else if (current.hasWall(WallDirection.SOUTH)) {
            root.classList.remove(mazeSouthWall.className)
        }

        if (to.hasWall(WallDirection.EAST)) {
            if (!current.hasWall(WallDirection.EAST)) {
                root.classList.add(mazeEastWall.className)
            }
        } else if (current.hasWall(WallDirection.EAST)) {
            root.classList.remove(mazeEastWall.className)
        }

        if (to.hasWall(WallDirection.WEST)) {
            if (!current.hasWall(WallDirection.WEST)) {
                root.classList.add(mazeWestWall.className)
            }
        } else if (current.hasWall(WallDirection.WEST)) {
            root.classList.remove(mazeWestWall.className)
        }

        currentState = to
    }

    fun markCellVisited() {
        root.classList.add(mazeVisited.className)
    }

    fun markStartCell() {
        root.classList.add(mazeStart.className)
    }

    fun markEndCell() {
        root.classList.add(mazeEnd.className)
    }

    fun markSideQuestCell() {
        root.classList.add(mazeSideQuest.className)
    }

    fun clearCellVisited() {
        root.classList.remove(mazeVisited.className)
    }

    fun clearStartCell() {
        root.classList.remove(mazeStart.className)
    }

    fun clearEndCell() {
        root.classList.remove(mazeEnd.className)
    }

    fun clearSideQuestCell() {
        root.classList.remove(mazeSideQuest.className)
    }

    companion object {
        fun TagConsumer<Element>.mazeCellView(index: Int): MazeCellView {
            val root = div(classes = mazeCell.className)

            return MazeCellView(index, root)
        }
    }
}
