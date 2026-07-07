package dev.junker.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

class Throttler(
    private val scope: CoroutineScope,
    private val waitTime: Duration
) {
    private var isThrottled = false

    fun post(action: () -> Unit) {
        if (isThrottled) {
            return
        }

        action()
        isThrottled = true

        scope.launch {
            delay(waitTime)
            isThrottled = false
        }
    }
}
