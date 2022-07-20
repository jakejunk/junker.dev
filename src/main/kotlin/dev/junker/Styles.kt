package dev.junker

import dev.junker.index.renderIndexStyles
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.css.*

enum class SiteColor(value: String) {
    BackgroundDark("#06070A"),
    BackgroundMedium("#0B0E13"),
    BackgroundLight("#11151C"),

    Primary("#1ABC9C"),
    PrimaryBright("#54E8CA"),
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