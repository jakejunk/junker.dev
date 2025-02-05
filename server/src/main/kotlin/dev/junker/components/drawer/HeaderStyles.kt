package dev.junker.components.drawer

import dev.junker.components.*
import dev.junker.components.general.*
import kotlinx.css.*
import kotlinx.css.properties.*

val logoTwitchKeyFrames = KeyframesBuilder().apply {
    8.invoke {
        transform { rotateZ(30.deg) }
    }
    16.invoke {
        transform { rotateZ((-15).deg) }
    }
    24.invoke {
        transform { rotateZ(7.deg) }
    }
    32.invoke {
        transform { rotateZ(0.deg) }
    }
    100.invoke {
        transform { rotateZ(0.deg) }
    }
}

fun CSSBuilder.headerStyles() {
    rule("@keyframes logoTwitch") {
        rules += logoTwitchKeyFrames.rules
    }

    rule(mainHeader.selector) {
        display = Display.flex
        alignItems = Align.center
        flexDirection = FlexDirection.column
        flexGrow = 1.0
    }

    rule(siteLogo.selector) {
        glowingPixelatedBackgroundImage("/assets/images/16x16_logo_black.png")
        margin(24.px)
        height = 64.px
        width = 64.px
    }

    rule("${siteLogo.selector}:hover") {
        animation += Animation(
            duration = 1.s,
            timing = Timing.easeInOut,
            delay = 0.s,
            iterationCount = 1.times,
            direction = AnimationDirection.normal,
            fillMode = FillMode.none,
            playState = PlayState.running,
            name = "logoTwitch"
        )
    }

    rule("${siteLogo.selector}${error.selector}") {
        filter = "invert(60%) sepia(30%) saturate(645%) hue-rotate(318deg) brightness(106%) contrast(90%) drop-shadow(0px 0px 1ch ${SiteColor.Secondary.color.value})"
    }

    rule(headerNav.selector) {
        display = Display.flex
        justifyContent = JustifyContent.center
        flexWrap = FlexWrap.wrap
        columnGap = ColumnGap("1ch")
        rowGap = RowGap("1ch")
        width = 100.pct
        padding(left = 24.px, right = 24.px, bottom = 24.px)
    }

    rule(navLink.selector) {
        backgroundColor = SiteColor.BackgroundMedium.color
        border = light2pxBorder
        borderRadius = cornerRadius
        padding(vertical = 1.rem, horizontal = 2.ch)
        textWithShadow(SiteColor.PrimaryText.color, "none")
        textDecoration = TextDecoration.none
        whiteSpace = WhiteSpace.nowrap
    }

    rule("${navLink.selector}:hover") {
        backgroundColor = SiteColor.BackgroundLight.color
    }

    rule("${navLink.selector}.selected") {
        primaryTextGlow()
    }
}

fun CSSBuilder.headerTabletStyles() {
    rule(mainHeader.selector) {
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.spaceBetween
        maxWidth = 1300.px
        width = 100.pct
    }

    rule(headerNav.selector) {
        alignSelf = Align.flexEnd
        rowGap = RowGap("1rem")
        width = LinearDimension.auto
        padding(left = 24.px, right = 24.px, top = 24.px, bottom = 0.px)
    }

    rule(navLink.selector) {
        borderBottomLeftRadius = 0.px
        borderBottomRightRadius = 0.px
        borderBottom = "none"
        marginBottom = (-1.8).px
    }
}
