package dev.junker.util

import kotlinx.css.CssBuilder
import kotlinx.css.properties.KeyframesBuilder

fun CssBuilder.keyframes(
    name: String,
    block: KeyframesBuilder.() -> Unit
) {
    val builder = KeyframesBuilder().apply(block)
    "@keyframes $name" {
        rules += builder.rules
    }
}
