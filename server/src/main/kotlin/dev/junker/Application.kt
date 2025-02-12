package dev.junker

import dev.junker.components.site
import dev.junker.markdown.MarkdownMetadata
import dev.junker.markdown.parseMetadata
import dev.junker.pages.InternalServerErrorPage
import dev.junker.pages.NotFoundPage
import dev.junker.pages.Page
import dev.junker.pages.routes
import dev.junker.util.allResources
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import kotlin.time.measureTimedValue

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
        "script-src 'self' 'wasm-unsafe-eval'",
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

    val (metaData, duration) = measureTimedValue { collectNoteMetadata() }
    log.debug("Cached all note metadata in ${duration.inWholeSeconds} seconds")

    routing { routes(metaData) }
}

private suspend fun ApplicationCall.errorPage(errorPage: Page.Error) {
    respondHtml(status = errorPage.status) {
        site(errorPage)
    }
}

private fun collectNoteMetadata(): Sequence<MarkdownMetadata> {
    val notes = allResources("/notes")
        .mapNotNull { note -> note.takeIf { it.extension == "md" } }
        .map { note -> parseMetadata(note.nameWithoutExtension, note.readText()).first }

    return notes
}
