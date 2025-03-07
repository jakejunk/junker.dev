package dev.junker.components.general

import dev.junker.components.SiteColor
import dev.junker.markdown.exclamation
import kotlinx.css.*
import kotlinx.css.properties.*

fun CssBuilder.generalStyles() {
    keyframes("flickerIn") {
        0 {
            opacity = 0
        }
        33 {
            opacity = .66
        }
        66 {
            opacity = .33
        }
        100 {
            opacity = 1
        }
    }

    fontFace {
        fontFamily = "Dogica Pixel"
        property("font-display", "swap")
        property("size-adjust", "75%")
        property("src", "local('Dogica Pixel'), url(/assets/fonts/dogicapixel.woff2) format('woff2')")
    }

    "h1, h2" {
        pixelFont()
        primaryTextGlow(1.ch)
        animation += Animation(
            name = "flickerIn",
            duration = 0.75.s,
            timing = cubicBezier(0.1, 1.1, 0.22, -1.0),
            iterationCount = 1.times,
            direction = AnimationDirection.normal,
            fillMode = FillMode.forwards,
            playState = PlayState.running
        )
        marginTop = 2.rem
        opacity = 0
        textAlign = TextAlign.center
    }

    h1 {
        marginBottom = 4.rem
        animationDelay = 0.25.s
    }

    h2 {
        marginBottom = 2.rem
        animationDelay = 0.33.s
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
            vertical = 3.rem,
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
}

fun CssBuilder.generalTabletStyles() {
    h2 {
        textAlign = TextAlign.left
    }

    imageTabletStyles()
}

// ====================================================================================================================

val cornerRadius = 9.px
val cornerRadiusRounder = 16.px

fun light2pxBorder(): Border {
    return lightBorder(2.px)
}

fun lightBorder(width: LinearDimension): Border {
    return Border(width, BorderStyle.solid, SiteColor.BackgroundLight.color)
}

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
    fontFamily = "Dogica Pixel, Courier New, Courier, monospace"
    fontSize = 2.3.rem
    fontWeight = FontWeight.lighter
    lineHeight = 2.5.rem.lh
}

fun StyledElement.flexRow(
    rowGap: LinearDimension? = null
) {
    display = Display.flex
    flexDirection = FlexDirection.row
    rowGap?.also { gap = it }
}

fun StyledElement.wrappingRow(
    rowGap: LinearDimension? = null,
    columnGap: LinearDimension? = null
) {
    display = Display.flex
    flexDirection = FlexDirection.row
    flexWrap = FlexWrap.wrap
    rowGap?.also { this.rowGap = it }
    columnGap?.also { this.columnGap = it }
}

fun StyledElement.flexColumn(
    columnGap: LinearDimension? = null
) {
    display = Display.flex
    flexDirection = FlexDirection.column
    columnGap?.also { gap = it }
}

fun StyledElement.grid3x3(
    cellGap: LinearDimension? = null
) {
    display = Display.grid
    gridTemplateColumns = GridTemplateColumns.repeat("3, 1fr")
    cellGap?.also { gap = it }
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
    color: Color = SiteColor.BackgroundDark.color,
    alpha: Double = 0.33
) {
    backdropFilter = "blur(16px)"
    backgroundColor = when (color) {
        Color.transparent -> color
        else -> color.changeAlpha(alpha)
    }
}

fun StyledElement.property(name: String, value: String) {
    put(name, value)
}
