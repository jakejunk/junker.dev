package dev.junker

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.css.*

enum class SiteColor(value: String) {
    Primary("#1ABC9C"),
    PrimaryBackground("#11151C"),
    PrimaryText("#F0E7D8"),
    Highlight("#ED6A5A");

    val color = Color(value)
}

fun CSSBuilder.renderStyles() {
    body {
        backgroundColor = SiteColor.PrimaryBackground.color
        margin(0.px)
    }

    rule("h1.page-title") {
        color = SiteColor.PrimaryText.color
    }
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}