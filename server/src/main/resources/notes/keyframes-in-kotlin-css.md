---
title: @keyframes in kotlin-css
description: Making @keyframes a bit easier to work with.
---

# `@keyframes` in `kotlin-css`

If you're looking to build a site completely in Kotlin, you'll likely run into
[`kotlin-css`](https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-css),
the CSS DSL of choice *(are there others?)*.
While fantastic for avoiding a mess of `.css` files, it's still in the early stage.
Authoring `@keyframes`, in particular, requires some extracurriculars in order to work cleanly.

## What's provided

The current DSL already has about 90% of what any site needs:

```kotlin
val styles = CssBuilder().apply {
    // Styling for HTML elements
    body {
        margin = Margin(0.px)
        padding = Padding(0.px)
    }
    
    // Styling for other selectors
    ".some-class" {
        animation += Animation(
            name = "my-animation",
            duration = 5.s
        )
    
        transform { translateX(-7.px) }
    }
}

styles.toString()
```

Additionally, `KeyframesBuilder` already exists and allows writing keyframe offsets:

```kotlin
val builder = KeyframesBuilder().apply { 
    50 {
        transform { translateX(7.px) }
    }
    100 {
        transform { translateX(52.px) }
    }
}
```

Cool! Almost there.

Sadly, this is where the API starts to feel a bit incomplete.
An `Animation` takes a name, but a `KeyframesBuilder` does not;
how can these be tied together?
And how are the keyframe values written out to the final CSS output?

## What's needed

Using nothing but the provided API, animations can be made to work like so:
```kotlin
val name = "my-animation"
val styles = CssBuilder().apply {
    // Other styles
    
    "@keyframes $name" {
        val builder = KeyframesBuilder().apply { 
            50 {
                transform { translateX(7.px) }
            }
            100 {
                transform { translateX(52.px) }
            }
        }
    
        rules += builder.rules
    }
    
    ".some-class" {
        animation += Animation(
            name = name,
            duration = 5.s
        )
    
        transform { translateX(-7.px) }
    }
}

styles.toString()
```

Could use some polishing, but it works.
The important thing of note is that in `kotlin-css`, both `rule` blocks and `KeyframesBuilder` implement `RuleContainer`,
meaning it's simply a matter of passing along the builder's rules.

After a bit of refactoring, you're left with a pretty reasonable way of defining `@keyframes`:

```kotlin
val name = "my-animation"
val styles = CssBuilder().apply {
    // Other styles
    
    keyframes(name) {
        50 {
            transform { translateX(7.px) }
        }
        100 {
            transform { translateX(52.px) }
        }
    }
    
    rule(".some-class") {
        animation += Animation(
            name = name,
            duration = 5.s
        )
    
        transform { translateX(-7.px) }
    }
}

styles.toString()

// ============================================================================
// CssBuilderExt.kt
// ============================================================================

fun CssBuilder.keyframes(
    name: String,
    block: KeyframesBuilder.() -> Unit
) {
    val builder = KeyframesBuilder().apply(block)
    rule("@keyframes $name") {
        rules += builder.rules
    }
}
```

Neat!