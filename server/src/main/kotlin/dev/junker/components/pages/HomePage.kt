package dev.junker.components.pages

import dev.junker.components.asciiBanner.asciiBanner
import dev.junker.components.tagline.tagline
import kotlinx.html.*

data object HomePage : Page.Content {
    override val title = "Jake Junker"
    override val name = "junker.dev"
    override val slug = "/"
    override val description = "Mild-mannered software developer trying to make his way in the universe."
    override val content: FlowContent.() -> Unit = {
        asciiBanner()
        tagline("Searching for the art in computation.")
    }
}

