package dev.junker.components

import dev.junker.components.asciiBanner.asciiBannerStyles
import dev.junker.components.asciiBanner.asciiBannerTabletStyles
import dev.junker.components.background.backgroundStyles
import dev.junker.components.code.codeStyles
import dev.junker.components.code.codeTabletStyles
import dev.junker.components.drawer.*
import dev.junker.components.footer.footerStyles
import dev.junker.components.footer.footerTabletStyles
import dev.junker.components.general.*
import dev.junker.components.main.commandLineStyles
import dev.junker.components.main.commandLineTabletStyles
import dev.junker.components.main.mainContentStyles
import dev.junker.components.main.mainContentTabletStyles
import dev.junker.components.quote.quoteStyles
import dev.junker.components.sudoku.sudokuStyles
import dev.junker.components.sudoku.sudokuTabletStyles
import dev.junker.components.grid.gridStyles
import dev.junker.components.grid.gridTabletStyles
import dev.junker.components.tagline.taglineStyles
import dev.junker.components.tagline.taglineTabletStyles
import kotlinx.css.*

fun CssBuilder.siteStyles() {
    "*, ::after, ::before" {
        boxSizing = BoxSizing.inherit
    }

    html {
        monospaceFont()
        backgroundColor = SiteColor.BackgroundMedium.color
        color = SiteColor.PrimaryText.color
        boxSizing = BoxSizing.borderBox
        // HACK: How to store multiple values for a property
        declarations["height"] = "100vh; height: 100dvh"
        overflowX = Overflow.hidden
    }

    body {
        flexColumn()
        margin = Margin(
            top = 0.px,
            right = 100.pct - 100.vw,
            bottom = 0.px,
            left = 0.px
        )
        minHeight = 100.pct
    }

    generalStyles()
    backgroundStyles()
    drawerStyles()
    headerStyles()
    footerStyles()
    mainContentStyles()
    commandLineStyles()
    asciiBannerStyles()
    taglineStyles()
    codeStyles()
    quoteStyles()
    gridStyles()
    sudokuStyles()

    // Make sure to render all query-dependent styles last.
    // CSS DSL tries to get clever and combine things, changing around order.
    tabletOrLarger {
        generalTabletStyles()
        drawerTabletStyles()
        headerTabletStyles()
        footerTabletStyles()
        mainContentTabletStyles()
        commandLineTabletStyles()
        asciiBannerTabletStyles()
        taglineTabletStyles()
        codeTabletStyles()
        gridTabletStyles()
        sudokuTabletStyles()
    }
}
