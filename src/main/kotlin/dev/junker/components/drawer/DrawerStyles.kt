package dev.junker.components.drawer

import dev.junker.components.*
import dev.junker.components.main.mainContainer
import kotlinx.css.*
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateY

val drawerButtonHeight = 32.px

fun CSSBuilder.renderDrawerStyles() {
    rule(drawerContainer.selector) {
        pointerEvents = PointerEvents.none
        position = Position.fixed
        height = 100.vh
        width = 100.vw
    }

    rule(drawerBackground.selector) {
        backgroundColor = SiteColor.BackgroundDark.color
        position = Position.absolute
        height = 100.vh
        width = 100.vw
        opacity = 0
        visibility = Visibility.hidden
        transition("opacity", 300.ms)
        transition("visibility", 300.ms)
    }

    rule(drawer.selector) {
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

    rule(drawerButton.selector) {
        pointerEvents = PointerEvents.auto
        property("touch-action", "manipulation")
        glowingPixelatedBackgroundImage("/assets/images/8x8_menu_black.png")
        backgroundColor = Color.transparent
        border = "none"
        position = Position.absolute
        top = (-34).px - 1.5.rem
        left = 50.pct - 16.px
        height = drawerButtonHeight
        width = drawerButtonHeight
    }

    rule("${drawerContainer.selector}:focus-within ${drawerBackground.selector}") {
        opacity = 0.75
        visibility = Visibility.visible
    }

    rule("${drawerContainer.selector}:focus-within ${drawerButton.selector}") {
        pointerEvents = PointerEvents.none
    }

    rule("${drawerContainer.selector}:focus-within + ${mainContainer.selector}") {
        pointerEvents = PointerEvents.none
    }

    rule("${drawerContainer.selector}:focus-within ${drawer.selector}") {
        transform = Transforms.none
    }
}

fun CSSBuilder.renderDrawerTabletStyles() {
    tabletOrLarger {
        rule(drawerContainer.selector) {
            pointerEvents = PointerEvents.unset
            position = Position.static
            height = LinearDimension.auto
            width = LinearDimension.auto
        }

        rule(drawerBackground.selector) {
            height = LinearDimension.auto
            width = LinearDimension.auto
        }

        rule(drawer.selector) {
            borderTop = "none"
            borderBottom = "solid 2px ${SiteColor.BackgroundLight.color.value}"
            alignItems = Align.center
            position = Position.static
            height = 100.pct
            width = LinearDimension.auto
            transform = Transforms.none
        }

        rule(drawerButton.selector) {
            display = Display.none
        }
    }
}
