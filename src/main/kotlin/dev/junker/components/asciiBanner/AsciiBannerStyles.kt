package dev.junker.components.asciiBanner

import dev.junker.components.SiteColor
import dev.junker.components.general.monospaceFont
import dev.junker.components.general.primaryTextGlow
import dev.junker.components.general.secondaryTextGlow
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.asciiBannerStyles() {
    rule(asciiBannerContainer.selector) {
        display = Display.flex
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
        marginTop = 1.rem
        overflowX = Overflow.hidden
    }

    rule(asciiBannerGroup.selector) {
        display = Display.flex
    }

    rule(asciiBanner.selector) {
        monospaceFont(
            fontWeight = FontWeight.normal,
            fontSize = 2.25.vw,
            lineHeight = 2.8.vw.lh
        )
        whiteSpace = WhiteSpace.pre
    }

    rule("${asciiBanner.selector}.junker") {
        primaryTextGlow()
    }

    rule("${asciiBanner.selector}.dot") {
        secondaryTextGlow()
    }

    rule("${asciiBanner.selector}.dev") {
        color = SiteColor.PrimaryText.color
    }
}

fun CSSBuilder.asciiBannerTabletStyles() {
    rule(asciiBanner.selector) {
        fontSize = LinearDimension.inherit
        lineHeight = 1.25.rem.lh
    }
}
