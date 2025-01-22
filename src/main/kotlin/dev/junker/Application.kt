package dev.junker

import dev.junker.components.pages.*
import dev.junker.components.site
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*

fun main(args: Array<String>) = EngineMain.main(args)

private val contentSecurityPolicy =
    listOf(
        "default-src 'none'",
        "base-uri 'self'",
        "connect-src 'self'",
        "font-src 'self' https://fonts.gstatic.com",
        "form-action 'self'",
        "frame-ancestors 'self'",
        "img-src 'self'",
        "manifest-src 'self'",
        "script-src 'self'",
        "style-src 'self' https://fonts.googleapis.com",
    )
    .joinToString("; ")

fun Application.webMain() {
    install(IgnoreTrailingSlash)
    // TODO: Forwarded headers
    install(DefaultHeaders) {
        HttpHeaders
        header("Content-Security-Policy", contentSecurityPolicy)
        header("Cross-Origin-Embedder-Policy", "require-corp")
        header("Cross-Origin-Opener-Policy", "same-origin")
        header("Cross-Origin-Resource-Policy", "same-site")
        header("Permission-Policy", "interest-cohort=()")
        header("Referrer-Policy", "strict-origin-when-cross-origin")
        header(HttpHeaders.Server, "app")
        header("X-Content-Type-Options", "nosniff")
        header("X-Frame-Options", "SAMEORIGIN")
    }
    install(StatusPages) {
        exception<Throwable> { call, _ ->
            call.errorPage(InternalServerErrorPage)
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.errorPage(NotFoundPage)
        }
    }

    routing { routes() }
}

private suspend fun ApplicationCall.errorPage(errorPage: Page.Error) {
    respondHtml(status = errorPage.status) {
        site(errorPage)
    }
}
