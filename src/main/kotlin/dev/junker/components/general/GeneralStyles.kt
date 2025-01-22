package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.Float
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.lh

fun CSSBuilder.generalStyles() {
    rule("h1, h2") {
        monospaceFont(
            fontWeight = FontWeight.lighter,
            lineHeight = 1.75.rem.lh
        )
        margin(vertical = 2.rem)
        primaryTextGlow()
    }

    h1 {
        fontSize = 2.5.rem
        textAlign = TextAlign.center
    }

    rule("h1::before") {
        content = "/".quoted
        color = SiteColor.PrimaryText.color
    }

    rule("h1.error") {
        secondaryTextGlow()
    }

    rule("h1.error::before") {
        content = "!".quoted
    }

    h2 {
        textAlign = TextAlign.center
    }

    rule("h2::before") {
        content = "#".quoted
        color = SiteColor.PrimaryText.color
    }

    hr {
        backgroundColor = SiteColor.BackgroundLight.color
        borderColor = SiteColor.BackgroundLight.color
        borderStyle = BorderStyle.solid
        height = 2.px
        maxWidth = 128.px
        margin(vertical = 3.rem, horizontal = LinearDimension.auto)
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

    rule(".img-right") {
        border = light2pxBorder
        borderRadius = cornerRadiusRounder
        display = Display.block
        height = 200.px
        margin(left = LinearDimension.auto, right = LinearDimension.auto, top = 0.px, bottom = 2.rem)
        padding(8.px)
        width = 200.px
    }

    linkStyles()
    inlineCodeStyles()
}

fun CSSBuilder.generalTabletStyles() {
    rule("h1") {
        fontSize = 3.rem
        textAlign = TextAlign.center
    }

    rule("h2") {
        fontSize = 2.rem
        textAlign = TextAlign.left
    }

    rule(".img-right") {
        float = Float.right
        height = 256.px
        marginLeft = 2.rem
        width = 256.px
    }
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

fun CSSBuilder.glowingPixelatedBackgroundImage(url: String) {
    pixelatedBackgroundImage(url, BackgroundRepeat.noRepeat)
    filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
}

fun CSSBuilder.pixelatedBackgroundImage(url: String, repeat: BackgroundRepeat) {
    backgroundImage = Image("url($url)")
    backgroundSize = "contain"
    backgroundRepeat = repeat
    property("image-rendering", "pixelated")
}

fun CSSBuilder.flexColumn() {
    display = Display.flex
    flexDirection = FlexDirection.column
}

fun CSSBuilder.primaryTextGlow() {
    textWithShadow(
        textColor = SiteColor.PrimaryBright.color,
        blurRadius = 8.px,
        color = SiteColor.Primary.color
    )
}

fun CSSBuilder.secondaryTextGlow() {
    textWithShadow(
        textColor = SiteColor.SecondaryBright.color,
        blurRadius = 8.px,
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

val Number.dvh: LinearDimension get() = LinearDimension("${this}dvh")
