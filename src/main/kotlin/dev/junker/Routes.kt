package dev.junker

import dev.junker.components.page.AboutPage
import dev.junker.components.page.HomePage
import dev.junker.components.page.Page
import dev.junker.components.renderWebPage
import dev.junker.components.renderWebPageStyles
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CSSBuilder

fun Routing.routes() {
    static("/") {
        staticBasePackage = "static"

        resources("favicon")
        static("/assets") {
            static("images") { resources("images") }
        }
    }

    get("/styles.css") {
        call.respondCss { renderWebPageStyles() }
    }

    getContentPage(HomePage)
    getContentPage(AboutPage)
}

private fun Routing.getContentPage(page: Page.Content) {
    get(page.slug) {
        call.respondHtml {
            renderWebPage(page)
        }
    }
}

private suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
