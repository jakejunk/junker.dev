package dev.junker

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.html.FlowContent
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testIndex() = testApplication {
        val expected = generateDocumentAsText(Page.Home)
        val response = client.get("/")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun testAbout() = testApplication {
        val expected = generateDocumentAsText(Page.About)
        val response = client.get("/about")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun testAboutWithTrailingSlash() = testApplication {
        val expected = generateDocumentAsText(Page.About)
        val response = client.get("/about/")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    private fun generateDocumentAsText(page: Page) =
        "<!DOCTYPE html>\n" + createHTML()
            .html { renderPage(page) }
            .toString()
}
