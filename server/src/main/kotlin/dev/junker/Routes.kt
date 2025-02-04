package dev.junker

import dev.junker.components.pages.AboutPage
import dev.junker.components.pages.HomePage
import dev.junker.components.pages.Page
import dev.junker.components.site
import dev.junker.components.siteStyles
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CSSBuilder

const val stylesRoute = "/styles.css"

fun Routing.routes() {
    staticResources("/", "/static/favicon")
    staticResources("/assets", "/static")

    get(stylesRoute) {
        call.respondCss { siteStyles() }
    }

    contentPage(HomePage)
    contentPage(AboutPage)
}

private fun Routing.contentPage(page: Page.Content) {
    get(page.slug) {
        call.respondHtml {
            site(page)
        }
    }
}

private suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
