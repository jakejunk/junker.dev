package dev.junker.components.drawer

import dev.junker.components.*
import kotlinx.css.*
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateY

fun CSSBuilder.renderDrawerStyles() {
    rule(drawerContainer.css) {
        pointerEvents = PointerEvents.none
        position = Position.fixed
        height = 100.vh
        width = 100.vw
    }

    rule(drawerBackground.css) {
        backgroundColor = SiteColor.BackgroundDark.color
        position = Position.absolute
        height = 100.vh
        width = 100.vw
        opacity = 0
        visibility = Visibility.hidden
        transition("opacity", 300.ms)
        transition("visibility", 300.ms)
    }

    rule(drawer.css) {
        pointerEvents = PointerEvents.auto
        borderTop = light2pxBorder
        backgroundColor = SiteColor.BackgroundMedium.color
        position = Position.fixed
        bottom = 0.px
        width = 100.pct
        flexColumn()
        transform.translateY(100.pct)
        transition("transform", 300.ms)
        property("willChange", "transform")
    }

    rule(drawerButton.css) {
        pointerEvents = PointerEvents.auto
        property("touch-action", "manipulation")
        glowingPixelatedBackgroundImage("/assets/images/8x8_menu_black.png")
        position = Position.absolute
        top = LinearDimension("-6ch")
        left = LinearDimension("2ch")
        height = LinearDimension("4ch")
        width = LinearDimension("4ch")
    }

    rule("${drawerContainer.css}:focus-within ${drawerBackground.css}") {
        opacity = 0.75
        visibility = Visibility.visible
    }

    rule("${drawerContainer.css}:focus-within ${drawerButton.css}") {
        pointerEvents = PointerEvents.none
    }

    rule("${drawerContainer.css}:focus-within ${drawer.css}") {
        transform = Transforms.none
    }
}

fun CSSBuilder.renderDrawerTabletStyles() {
    tabletOrLarger {
        rule(drawerContainer.css) {
            pointerEvents = PointerEvents.unset
            position = Position.static
            height = LinearDimension.auto
            width = LinearDimension.auto
        }

        rule(drawerBackground.css) {
            height = LinearDimension.auto
            width = LinearDimension.auto
        }

        rule(drawer.css) {
            borderTop = "none"
            borderBottom = "solid 2px ${SiteColor.BackgroundLight.color.value}"
            alignItems = Align.center
            position = Position.static
            height = 100.pct
            width = LinearDimension.auto
            transform = Transforms.none
        }

        rule(drawerButton.css) {
            display = Display.none
        }
    }
}
