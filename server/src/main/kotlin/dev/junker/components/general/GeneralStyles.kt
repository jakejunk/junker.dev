package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.Float
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.lh

fun CSSBuilder.generalStyles() {
    fontFace {
        fontFamily = "Dogica Pixel"
        property("src", "local('Dogica Pixel'), url(/assets/fonts/dogicapixel.otf) format('opentype')")
    }

    rule("h1, h2") {
        pixelFont()
        textAlign = TextAlign.center
        margin(bottom = 2.rem)
        primaryTextGlow(1.ch)
    }

    h1 {
        before {
            content = "/".quoted
            color = SiteColor.PrimaryText.color
        }
    }

    rule("h1.error") {
        secondaryTextGlow()

        before {
            content = "!".quoted
        }
    }

    hr {
        backgroundColor = SiteColor.BackgroundLight.color
        borderColor = SiteColor.BackgroundLight.color
        borderStyle = BorderStyle.solid
        height = 2.px
        width = 128.px
        margin(vertical = 2.rem, horizontal = LinearDimension.auto)
    }

    sup {
        lineHeight = 0.rem.lh
    }

    i {
        color = SiteColor.SubtleText.color
    }

    rule(".footnotes") {
        color = SiteColor.SubtleText.color
        fontStyle = FontStyle.italic
    }

    imageStyles()
    linkStyles()
    inlineCodeStyles()
}

fun CSSBuilder.generalTabletStyles() {
    h2 {
        textAlign = TextAlign.left
    }

    imageTabletStyles()
}

// ====================================================================================================================

val light2pxBorder = "solid 2px ${SiteColor.BackgroundLight.color.value}"
val cornerRadius = 9.px
val cornerRadiusRounder = 16.px

fun CSSBuilder.tabletOrLarger(block: RuleSet) = media("(min-width: 768px)", block)

fun CSSBuilder.monospaceFont(
    fontWeight: FontWeight? = null,
    fontSize: LinearDimension? = null,
    lineHeight: LineHeight? = null
) {
    fontFamily = "Source Code Pro, Courier New, Courier, monospace"

    if (fontWeight != null) {
        this.fontWeight = fontWeight
    }

    if (fontSize != null) {
        this.fontSize = fontSize
    }

    if (lineHeight != null) {
        this.lineHeight = lineHeight
    }
}

fun CSSBuilder.pixelFont() {
    fontFamily = "Dogica Pixel, serif"
    fontSize = 1.75.rem
    fontWeight = FontWeight.lighter
    lineHeight = 2.5.rem.lh
}

fun CSSBuilder.flexColumn() {
    display = Display.flex
    flexDirection = FlexDirection.column
}

fun CSSBuilder.primaryTextGlow(glowRadius: LinearDimension = 8.px) {
    textWithShadow(
        textColor = SiteColor.PrimaryBright.color,
        blurRadius = glowRadius,
        color = SiteColor.Primary.color
    )
}

fun CSSBuilder.secondaryTextGlow(glowRadius: LinearDimension = 8.px) {
    textWithShadow(
        textColor = SiteColor.SecondaryBright.color,
        blurRadius = glowRadius,
        color = SiteColor.Secondary.color
    )
}

fun CSSBuilder.property(name: String, value: String) = put(name, value)

fun CSSBuilder.textWithShadow(textColor: Color, blurRadius: LinearDimension, color: Color) {
    textWithShadow(textColor, "0 0 $blurRadius ${color.value}")
}

fun CSSBuilder.textWithShadow(textColor: Color, value: String) {
    color = textColor
    property("text-shadow", value)
}

val Number.ch get() = LinearDimension("${this}ch")
