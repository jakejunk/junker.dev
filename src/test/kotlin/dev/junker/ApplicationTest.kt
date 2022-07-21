package dev.junker

import dev.junker.about.renderAbout
import dev.junker.index.renderIndex
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testIndex() = testApplication {
        val expected = generateDocumentAsText { renderIndex() }
        val response = client.get("/")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun testAbout() = testApplication {
        val expected = generateDocumentAsText { renderAbout() }
        val response = client.get("/about")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun testAboutWithTrailingSlash() = testApplication {
        val expected = generateDocumentAsText { renderAbout() }
        val response = client.get("/about/")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    private inline fun generateDocumentAsText(crossinline block: HTML.() -> Unit) = "<!DOCTYPE html>\n" + createHTML().html { block() }.toString()
}
