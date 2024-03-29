package dev.junker

import dev.junker.components.page.*
import dev.junker.components.renderWebPage
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testIndex() = testApplication {
        val expected = generateDocumentAsText(HomePage)
        val response = client.get("/")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun testAbout() = testApplication {
        val expected = generateDocumentAsText(AboutPage)
        val response = client.get("/about")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun testAboutWithTrailingSlash() = testApplication {
        val expected = generateDocumentAsText(AboutPage)
        val response = client.get("/about/")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun testNotFound() = testApplication {
        val expected = generateDocumentAsText(NotFoundPage)
        val response = client.get("/totalAbsoluteNonsense/")

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun testInternalServerError() = testApplication {
        routing {
            get("/broken") {
                throw RuntimeException()
            }
        }

        val expected = generateDocumentAsText(InternalServerErrorPage)
        val response = client.get("/broken")

        assertEquals(HttpStatusCode.InternalServerError, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    private fun generateDocumentAsText(page: Page) =
        "<!DOCTYPE html>\n" + createHTML()
            .html { renderWebPage(page) }
            .toString()
}
