package dev.junker

import dev.junker.index.renderIndex
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.webMain() {
    routing {
        get("/styles.css") {
            call.respondCss { renderStyles() }
        }
        get("/") {
            call.respondHtml { renderIndex() }
        }
    }
}
