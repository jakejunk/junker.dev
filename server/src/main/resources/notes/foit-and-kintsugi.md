---
title: The Flash Of Invisible Text, and Kintsugi
description: Issue, or inspiration?
creationDate: TODO
---

# The Flash Of Invisible Text, and Kintsugi

Look at that section header ☝🏼!

![Wow!](https://tenor.com/tZXzxAjRGED.gif "Wow!")

## Late to the party

Using local fonts feel like something that should be easy.
And it is, to an extent.
Just host the font on the server,
write the needed [`@font-face`](https://developer.mozilla.org/en-US/docs/Web/CSS/@font-face) rule,
and then take in the beauty of that revamped section header.
But wait, I think I saw something...

Well, more accurately, I didn't see anything.
The page loaded, I didn't see a header, and then—BAM—there it is.
It's that darn [flash of invisible text](https://fonts.google.com/knowledge/glossary/foit).

## Download speed, a law of nature

No matter how fast any connection is, downloading fonts takes time.
With the browser asking "What should I do until the font gets here?",
it's time to reach into the web dev toolbox and see what can be done:
- [`font-display`](https://developer.mozilla.org/en-US/docs/Web/CSS/@font-face/font-display)
provides the loading "fallback strategy"
(show nothing, show a backup font, etc.).
Using `swap` at least downgrades our issue into the
[flash of unstyled text](https://fonts.google.com/knowledge/glossary/fout).
- [`size-adjust`](https://developer.mozilla.org/en-US/docs/Web/CSS/@font-face/size-adjust)
works great with `font-display: swap`,
by adjusting the scale of its parent `@font-face`, making the swap less visually jarring.
- [`preload`](https://web.dev/articles/preload-critical-assets)!
Skip the "wait for the stylesheet to download" step and get that font ready for action.
- [`Cache-Control`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control):
I haven't actually implemented this yet, but I should.

Sweet! One massive step in the right direction.

## Embracing imperfection

The loading delay has been addressed, great;
but something about it feels, I don't know, sterile.
After all this work, the text just shows up quicker and smoother than before.
Not bad, but where's the _**touch**_?

According to [kintsugi](https://en.wikipedia.org/wiki/Kintsugi),
beauty can be found in brokenness and imperfection.
While I'm not exactly repairing ceramic with gold,
it's worth considering that maybe my goal shouldn't be to completely eliminate "download time";
perhaps it's an idea worth highlighting and playing with.


Taking inspiration from traditional neon signs,
we can tweak the opacity to mimic a flicker effect.
Combine this with a slight animation delay to mask the effects of loading,
and we're cooking with gas now.

```css
@keyframes flickerIn {
    0% {
        opacity: 0;
    }
    33% {
        opacity: 0.66;
    }
    66% {
        opacity: 0.33;
    }
    100% {
        opacity: 1;
    }
}

h1, h2 {
    /* Other styles */
    animation:
        flickerIn
        0.75s
        cubic-bezier(0.1, 1.1, 0.22, -1)
        forwards;
}

h1 {
    animation-delay: 0.25s;
}

h2 {
    animation-delay: 0.33s;
}
```

### Wow!

_Confession: I always Google "em dash" when I want to use one.
Like seriously what on earth is `Alt + 0151`, Windows._
