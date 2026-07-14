package dev.junker.util

import dev.junker.maze.Direction
import org.w3c.dom.HTMLElement
import org.w3c.dom.TouchEvent
import org.w3c.dom.events.Event
import org.w3c.dom.get
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

class InputAdapter(
    private val target: HTMLElement,
    private val onSwipe: (Direction) -> Unit
) : AutoCloseable {
    private val minSwipeDistance = 52
    private var touchStartX = 0
    private var touchStartY = 0

    init {
        target.addEventListener("touchstart", ::handleTouchStart)
        target.addEventListener("touchend", ::handleTouchEnd)
    }

    private fun handleTouchStart(e: Event) {
        val touchEvent = e as TouchEvent
        if (touchEvent.touches.length <= 0) {
            return
        }

        val touch = touchEvent.touches[0]!!

        touchStartX = touch.clientX
        touchStartY = touch.clientY

        e.preventDefault()
    }

    private fun handleTouchEnd(e: Event) {
        val touchEvent = e as TouchEvent
        if (touchEvent.changedTouches.length <= 0) {
            return
        }

        val touch = touchEvent.changedTouches[0]!!
        val endX = touch.clientX
        val endY = touch.clientY

        val dx = endX - touchStartX
        val dy = endY - touchStartY

        if (abs(dx) * abs(dx) + abs(dy) * abs(dy) < minSwipeDistance * minSwipeDistance) {
            return
        }

        val degrees = atan2(-dy.toDouble(), dx.toDouble()) * (180.0 / PI)
        val direction = when (degrees) {
            in 45.0..135.0 -> Direction.UP
            in -135.0..-45.0 -> Direction.DOWN
            in -45.0..45.0 -> Direction.RIGHT
            else -> Direction.LEFT
        }

        onSwipe(direction)

        e.preventDefault()
    }

    override fun close() {
        target.removeEventListener("touchstart", ::handleTouchStart)
        target.removeEventListener("touchend", ::handleTouchEnd)
    }
}
