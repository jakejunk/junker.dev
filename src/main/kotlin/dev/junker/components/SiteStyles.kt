package dev.junker.components

import dev.junker.components.asciiBanner.asciiBannerStyles
import dev.junker.components.asciiBanner.asciiBannerTabletStyles
import dev.junker.components.background.backgroundStyles
import dev.junker.components.drawer.*
import dev.junker.components.footer.footerStyles
import dev.junker.components.footer.footerTabletStyles
import dev.junker.components.general.*
import dev.junker.components.main.mainContentStyles
import dev.junker.components.main.mainContentTabletStyles
import kotlinx.css.*

fun CSSBuilder.siteStyles() {
    rule("*, ::after, ::before") {
        boxSizing = BoxSizing.inherit
    }

    html {
        monospaceFont()
        backgroundColor = SiteColor.BackgroundMedium.color
        color = SiteColor.PrimaryText.color
        boxSizing = BoxSizing.borderBox
        height = 100.pct
        overflowX = Overflow.hidden
    }

    body {
        flexColumn()
        height = 100.pct
        margin(top = 0.px, right = 100.pct - 100.vw, bottom = 0.px, left = 0.px)
    }

    generalStyles()
    backgroundStyles()
    drawerStyles()
    headerStyles()
    footerStyles()
    mainContentStyles()
    asciiBannerStyles()

    // Make sure to render all query-dependent styles last.
    // CSS DSL tries to get clever and combine things, changing around order.
    tabletOrLarger {
        generalTabletStyles()
        drawerTabletStyles()
        headerTabletStyles()
        footerTabletStyles()
        mainContentTabletStyles()
        asciiBannerTabletStyles()
    }
}
