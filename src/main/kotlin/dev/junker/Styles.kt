package dev.junker

import dev.junker.index.renderIndexStyles
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.css.*

enum class SiteColor(value: String) {
    BackgroundDark("#090C10"),
    TerminalHeader("#11151C80"),
    TerminalBackground("#11151C"),
    Primary("#1ABC9C"),
    PrimaryText("#F0E7D8"),
    Highlight("#ED6A5A");

    val color = Color(value)
}

fun CSSBuilder.renderStyles() {
    renderIndexStyles()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}