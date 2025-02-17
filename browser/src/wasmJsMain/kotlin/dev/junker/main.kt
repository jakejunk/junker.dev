package dev.junker

import dev.junker.syntax.highlightCode
import kotlinx.browser.document
import kotlinx.html.dom.append
import kotlinx.html.js.code
import org.w3c.dom.HTMLPreElement
import org.w3c.dom.asList
import kotlin.time.measureTime

fun main() {
    println("WASM works, heck yeah!")

    highlightCodeBlocks()
}

private fun highlightCodeBlocks() {
    val codeBlocks = document.querySelectorAll("pre[data-proglang]")
    if (codeBlocks.length == 0) {
        return
    }

    val time = measureTime {
        for (codeBlock in codeBlocks.asList()) {
            val codeElement = codeBlock as HTMLPreElement
            val codeString = codeElement.innerText
            codeElement.textContent = ""

            codeElement.append {
                code {
                    highlightCode(codeString)
                }
            }
        }
    }

    println("Highlighted all code in $time")
}
