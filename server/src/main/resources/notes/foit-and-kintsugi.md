---
title: The Flash Of Invisible Text, and Kintsugi
description: Download time; issue, or source of inspiration?
creationDate: 2025.2.27
---

# The Flash Of Invisible Text, and Kintsugi

Using local fonts feels like something that should be easy.
And it is, to an extent.
Just host the font,
write the needed [`@font-face`](https://developer.mozilla.org/en-US/docs/Web/CSS/@font-face) rule,
and then take in the beauty of that revamped section header.
But wait, I think I saw something...

Well, more accurately, I didn't see anything.
The page loaded with no header in sight, and then—BAM—there it is.
It's that darn [flash of invisible text](https://fonts.google.com/knowledge/glossary/foit).

...

![GAME ON](/assets/images/game-on.jpg "Photo by 8 verthing on Unsplash")

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
works great with `font-display: swap` by scaling its parent `@font-face`,
making the swap less visually jarring.
- [`preload`](https://web.dev/articles/preload-critical-assets)!
Skip the "wait for the stylesheet to download" step and load the font right away.
- [`Cache-Control`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control)
HTTP headers can be used to tell the browser to cache the font for a bit.
I haven't actually implemented this yet, but I should.

Sweet! Using these tools addresses the loading delay—but
something about it feels, I don't know, sterile.
The text just shows up quicker and smoother than before.
Not bad, but where's the _**touch**_?

## Embracing imperfection

Time to shift perspective.
[Kintsugi](https://en.wikipedia.org/wiki/Kintsugi) shows us that
beauty can be found in imperfection,
so maybe it's worth considering that my goal shouldn't be to eliminate "download time" entirely;
perhaps it's an idea worth playing with.

_Can I fix text flashing while still conveying "latency"?_

Increasing the opacity after a delay would mask any loading...
just make sure that text doesn't pop in.
Additionally, I can take inspiration from neon signs and use `cubic-bezier` to create a subtle "flicker" effect.
This builds a fun bridge between the idea of "warming up" neon gas and
"downloading" the required font file:

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

Now, section headers avoid text flashing _with style_.

### Wow!

...

- _Confession: I always Google "em dash" when I want to use one—seriously
what on earth is `Alt + 0151`, Windows._
- _If your neon sign is **actually** flickering, it might be broken._
