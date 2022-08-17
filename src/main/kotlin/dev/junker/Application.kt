package dev.junker

import dev.junker.components.page.*
import dev.junker.components.renderWebPage
import dev.junker.components.renderWebPageStyles
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CSSBuilder

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.webMain() {
    install(IgnoreTrailingSlash)
    install(StatusPages) {
        exception<Throwable> { call, _ ->
            call.renderErrorPage(InternalServerErrorPage)
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.renderErrorPage(NotFoundPage)
        }
    }

    routing {
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
}

private fun Routing.getContentPage(page: Page.Content) {
    get(page.slug) {
        call.respondHtml {
            renderWebPage(page)
        }
    }
}

private suspend fun ApplicationCall.renderErrorPage(errorPage: Page.Error) {
    this.respondHtml(status = errorPage.status) { renderWebPage(errorPage) }
}

private suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
