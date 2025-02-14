---
title: @keyframes in kotlin-css
description: Making @keyframes a bit easier to work with.
modifiedDate: 2025-2-13
---

# @keyframes in kotlin-css

_The following is relevant for `kotlin-css:2025.2.4` and below. 
`kotlin-css:2025.2.5` will include `CssBuilder::keyframes`. Sweet!_

If you're looking to build a site completely in Kotlin, you'll likely run into
[`kotlin-css`](https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-css),
the CSS DSL of choice *(according to me)*.
Great for avoiding a mess of stylesheets, but still in its early phase.
Authoring `@keyframes`, in particular, requires some extracurriculars in order to work cleanly.

## What's provided

```kotlin
val styles = CssBuilder().apply {
    // Nice syntax, but doesn't do anything
    KeyframesBuilder().apply { 
        50 {
            transform { translateX(7.px) }
        }
        100 {
            transform { translateX(52.px) }
        }
    }

    // `rule` block
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

Out of the box, `kotlin-css` provides:
- `CssBuilder`, for creating styling rules.
- `KeyframesBuilder`, for building out animation `@keyframes`.

So what's the issue?

As of version `2025.2.4`, `CssBuilder` has no built-in handling for `KeyframesBuilder`,
so keyframes will be lost whenever the final CSS is produced.
Not to mention that the keyframe builder doesn't even have a name to reference.

## What's needed

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

Since both `rule` blocks and `KeyframesBuilder` implement `RuleContainer`,
it's simply a matter of manually passing along the builder's rules in a `"@keyframes $name"` block.

After a bit of refactoring, you're left with a pretty reasonable way of defining `@keyframes`:

```kotlin
// CssBuilderExt.kt

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
```

Neat!