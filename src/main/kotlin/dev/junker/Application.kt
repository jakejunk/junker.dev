package dev.junker

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CSSBuilder
import kotlinx.html.FlowContent

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.webMain() {
    install(IgnoreTrailingSlash)

    routing {
        static("/assets") {
            staticBasePackage = "static"
            static("images") { resources("images") }
        }

        get("/styles.css") {
            call.respondCss { renderBaseStyles() }
        }

        getPage(Location.Home) {
            +"Under construction :)"
        }

        getPage(Location.About) {
            +"I'll tell you later."
        }
    }
}

private fun Routing.getPage(location: Location, block: FlowContent.() -> Unit) {
    get(location.href) {
        call.respondHtmlTemplate(BasePage(location)) {
            content {
                block()
            }
        }
    }
}

private suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
