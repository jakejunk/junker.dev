package dev.junker.pages

import dev.junker.components.asciiBanner.asciiBanner
import dev.junker.components.tagline.tagline
import kotlinx.html.*

data object HomePage : Page.Content {
    override val slug = "/"
    override val title = null
    override val description = "Mild-mannered software developer trying to make his way in the universe."
    override val content: FlowContent.() -> Unit = {
        asciiBanner()
        tagline("Searching for the art in computation.")
    }
}
