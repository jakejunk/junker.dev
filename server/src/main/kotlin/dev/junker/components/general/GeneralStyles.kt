package dev.junker.components.general

import dev.junker.components.SiteColor
import dev.junker.markdown.exclamation
import kotlinx.css.*
import kotlinx.css.properties.*

fun CssBuilder.generalStyles() {
    fontFace {
        fontFamily = "Dogica Pixel"
        property("src", "local('Dogica Pixel'), url(/assets/fonts/dogicapixel.otf) format('opentype')")
    }

    "h1, h2" {
        pixelFont()
        primaryTextGlow(1.ch)
        textAlign = TextAlign.center
        marginTop = 2.rem
    }

    h1 {
        marginBottom = 4.rem
    }

    h2 {
        marginBottom = 2.rem
    }

    "h1.error" {
        secondaryTextGlow()
    }

    hr {
        backgroundColor = SiteColor.BackgroundLight.color
        borderColor = SiteColor.BackgroundLight.color
        borderStyle = BorderStyle.solid
        height = 1.px
        width = 128.px
        margin = Margin(
            vertical = 2.rem,
            horizontal = LinearDimension.auto
        )
    }

    sup {
        lineHeight = 0.rem.lh
    }

    i {
        color = SiteColor.SubtleText.color
    }

    em {
        color = SiteColor.SubtleText.color
    }

    s {
        property("text-decoration-thickness", "from-font")
    }

    exclamation.selector {
        // TODO: Consolidate with similar rules in imageStyles
        pixelFont()
        transform { rotateZ((-15).deg) }
        display = Display.inlineBlock
        whiteSpace = WhiteSpace.nowrap
    }

    ".footnotes" {
        color = SiteColor.SubtleText.color
        fontStyle = FontStyle.italic
    }

    imageStyles()
    linkStyles()
    codeStyles()
}

fun CssBuilder.generalTabletStyles() {
    h2 {
        textAlign = TextAlign.left
    }

    imageTabletStyles()
    codeTabletStyles()
}

// ====================================================================================================================

val light2pxBorder = Border(2.px, BorderStyle.solid, SiteColor.BackgroundLight.color)
val cornerRadius = 9.px
val cornerRadiusRounder = 16.px

fun CssBuilder.tabletOrLarger(block: RuleSet) = media("(min-width: 768px)", block)

fun CssBuilder.monospaceFont(
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

fun StyledElement.pixelFont() {
    fontFamily = "Dogica Pixel, serif"
    fontSize = 1.75.rem
    fontWeight = FontWeight.lighter
    lineHeight = 2.5.rem.lh
}

fun StyledElement.flexColumn() {
    display = Display.flex
    flexDirection = FlexDirection.column
}

fun StyledElement.primaryTextGlow(glowRadius: LinearDimension = 8.px) {
    textWithShadow(
        textColor = SiteColor.PrimaryBright.color,
        shadowColor = SiteColor.Primary.color,
        blurRadius = glowRadius
    )
}

fun StyledElement.secondaryTextGlow(glowRadius: LinearDimension = 8.px) {
    textWithShadow(
        textColor = SiteColor.SecondaryBright.color,
        shadowColor = SiteColor.Secondary.color,
        blurRadius = glowRadius
    )
}

fun StyledElement.textWithShadow(textColor: Color, shadowColor: Color, blurRadius: LinearDimension) {
    textWithShadow(textColor, "0 0 $blurRadius ${shadowColor.value}")
}

fun StyledElement.textWithShadow(textColor: Color, value: String) {
    color = textColor
    property("text-shadow", value)
}

fun StyledElement.frostedGlass(
    siteColor: SiteColor = SiteColor.BackgroundDark,
    alpha: Double = 0.33
) {
    backdropFilter = "blur(16px)"
    backgroundColor = siteColor.color.changeAlpha(alpha)
}

fun StyledElement.property(name: String, value: String) {
    put(name, value)
}
