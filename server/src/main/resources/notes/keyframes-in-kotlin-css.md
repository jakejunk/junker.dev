---
title: @keyframes in kotlin-css
description: Making @keyframes a bit easier to work with.
modifiedDate: 2025-2-13
---

# @keyframes in kotlin-css

_The following is relevant for `kotlin-css:2025.2.4` and below;
2025.2.5 will include `CssBuilder::keyframes`. Sweet!_

If you're looking to build a site completely in Kotlin, you'll likely run into
[`kotlin-css`](https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-css),
the CSS DSL of choice *(according to me)*.
While a great alternative to handwriting `.css` files,
some things can be more difficult than expected due to the "low-level" design—getting it to produce
[`@keyframes`](https://developer.mozilla.org/en-US/docs/Web/CSS/@keyframes),
in particular, requires some extra patience.

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

    ".some-class" {
        animation += Animation(
            name = "my-animation",
            duration = 5.s
        )

        transform { translateX((-7).px) }
    }
}

styles.toString()
```

Out of the box, `kotlin-css` provides:
- `CssBuilder`, for authoring styling rules.
- `KeyframesBuilder`, for building out animation `@keyframes`.

## What's missing?

As of version `2025.2.4`, `CssBuilder` has no built-in handling for `KeyframesBuilder`.
It's not entirely obvious how to ensure that the keyframe offsets get rendered into the final stylesheet.

## What's needed

```kotlin
const val myAnimation = "my-animation"
val styles = CssBuilder().apply {
    "@keyframes myAnimation" {
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

    // `rule` block
    ".some-class" {
        animation += Animation(
            name = myAnimation,
            duration = 5.s
        )

        transform { translateX((-7).px) }
    }
}

styles.toString()
```

Since both `rule` blocks and `KeyframesBuilder` implement `RuleContainer`,
it's just a matter of manually passing along the builder's rules in a `"@keyframes $name"` block.

After a bit of refactoring, you're left with a pretty reasonable way of defining `@keyframes`:

```kotlin
fun CssBuilder.keyframes(
    name: String,
    block: KeyframesBuilder.() -> Unit
) {
    val builder = KeyframesBuilder().apply(block)
    rule("@keyframes $name") {
        rules += builder.rules
    }
}

const val myAnimation = "my-animation"
val styles = CssBuilder().apply {
    keyframes(myAnimation) {
        50 {
            transform { translateX(7.px) }
        }
        100 {
            transform { translateX(52.px) }
        }
    }
    
    rule(".some-class") {
        animation += Animation(
            name = myAnimation,
            duration = 5.s
        )
    
        transform { translateX((-7).px) }
    }
}

styles.toString()
```

### Neat!