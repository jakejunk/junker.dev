package dev.junker.components

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.*

fun CSSBuilder.renderTerminalHeaderStyles() {
    rule(".terminal-drawer") {
        position = Position.fixed
        height = 100.vh
        width = 100.vw
        pointerEvents = PointerEvents.none
    }

    rule(".terminal-drawer-bg") {
        position = Position.absolute
        backgroundColor = SiteColor.BackgroundDark.color
        height = 100.vh
        width = 100.vw
        opacity = 0
        visibility = Visibility.hidden
        transition("opacity", 300.ms)
        transition("visibility", 300.ms)
    }

    rule(".terminal-drawer-button") {
        position = Position.absolute
        backgroundColor = Color.red
        top = LinearDimension("-8ch")
        right = LinearDimension("2ch")
        height = LinearDimension("5ch")
        width = LinearDimension("5ch")
        pointerEvents = PointerEvents.auto
        property("touch-action", "manipulation")
    }

    rule(".terminal-header") {
        beveledTerminalSurface()
        flexColumn()
        alignItems = Align.center
        bottom = 0.px
        position = Position.fixed
        width = 100.pct
        transform.translateY(100.pct)
        transition("transform", 300.ms)
        property("willChange", "transform")
        pointerEvents = PointerEvents.auto
    }

    rule(".terminal-drawer:focus-within > .terminal-drawer-bg") {
        opacity = 0.75
        visibility = Visibility.visible
    }

    rule(".terminal-drawer:focus-within .terminal-drawer-button") {
        pointerEvents = PointerEvents.none
    }

    rule(".terminal-drawer:focus-within .terminal-header") {
        transform = Transforms.none
    }

    rule(".site-logo-image") {
        backgroundImage = Image("url(/assets/images/16x16_logo_black.png)")
        backgroundSize = "contain"
        backgroundRepeat = BackgroundRepeat.noRepeat
        filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
        height = LinearDimension("8ch")
        property("image-rendering", "pixelated")
        width = LinearDimension("8ch")
        margin(2.rem, 2.rem, 2.rem, 2.rem)
    }

    rule(".site-logo-image.error") {
        filter = "invert(60%) sepia(30%) saturate(645%) hue-rotate(318deg) brightness(106%) contrast(90%) drop-shadow(0px 0px 1ch ${SiteColor.Secondary.color.value})"
    }

    rule(".terminal-nav") {
        alignSelf = Align.flexEnd
        columnGap = ColumnGap("1ch")
        display = Display.flex
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
        marginBottom = 1.rem
        paddingLeft = 1.rem
        paddingRight = 1.rem
        rowGap = RowGap("1ch")
        width = 100.pct
    }

    rule(".nav-link") {
        backgroundColor = SiteColor.BackgroundMedium.color
        borderRadius = LinearDimension("1ch")
        color = SiteColor.PrimaryText.color
        padding(vertical = 1.rem, LinearDimension("3ch"))
        textDecoration = TextDecoration.none
        whiteSpace = WhiteSpace.nowrap
    }

    rule(".nav-link:hover") {
        backgroundColor = SiteColor.BackgroundDark.color
    }

    rule(".nav-link.selected") {
        primaryTextGlow()
    }
}

fun CSSBuilder.renderTerminalHeaderTabletStyles() {
    tabletOrLarger {
        rule(".terminal-drawer") {
            position = Position.static
            height = LinearDimension.auto
            width = LinearDimension.auto
            pointerEvents = PointerEvents.unset
        }

        rule(".terminal-drawer-bg") {
            height = LinearDimension.auto
            width = LinearDimension.auto
        }

        rule(".terminal-drawer-button") {
            display = Display.none
        }

        rule(".terminal-header") {
            borderTopLeftRadius = LinearDimension("1ch")
            borderBottomLeftRadius = LinearDimension("1ch")
            height = 100.pct
            position = Position.static
            width = LinearDimension.auto
            transform = Transforms.none
        }

        rule(".terminal-nav") {
            alignItems = Align.flexEnd
            flexDirection = FlexDirection.column
            margin(vertical = 2.rem)
            paddingRight = 0.px
        }

        rule(".nav-link") {
            borderTopRightRadius = 0.px
            borderBottomRightRadius = 0.px
        }
    }
}
