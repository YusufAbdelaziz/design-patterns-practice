---
name: pattern-package-intro
description: Each pattern's Java package has ONE comprehensive README.md — the single source for that pattern (context + exercise)
metadata:
  type: feedback
---

Every design pattern practiced here has exactly **one** Markdown file: a
comprehensive `README.md` inside its Java package
(`src/main/java/patterns/<pattern>/README.md`). There is no separate
`exercises/<pattern>.md` — the user wanted a single file per pattern so they don't
go back and forth between two documents. (The old `exercises/` folder was removed.)

**Why:** everything about a pattern lives in one place, right next to the code, so
the user (or anyone on the repo) never hunts across files.

**How to apply:** create/update this one README when scaffolding a new pattern. It
must contain, in this order:

1. **An explanation of the pattern** — the intent plus a couple of sentences on
   *how it works* / the problem it solves. Not just a one-line definition.
2. **The anti-pattern it replaces** — a short, concrete **code smell** (a small
   Java snippet of the messy version you'd write without the pattern), followed by
   a one-line **"What it solves"**. This is what makes the pattern *recognizable*.
3. **A recall hook** — a compact memory aid so the pattern is easy to recall:
   - *the smell that triggers it* (what you'd see in code that means "reach for
     this"),
   - *in one line* (the pattern's essence in a sentence),
   - *an analogy* (a real-world metaphor),
   - *tell it apart* from any look-alike pattern, when there is one.
4. **A real-life use case** — a concrete, believable scenario where the pattern
   is needed (see [[pattern-scenarios]]).
5. **A UML diagram in Mermaid** — a ```mermaid `classDiagram` fenced block
   illustrating the participants and their relationships (renders on GitHub and
   in the IDE).
6. **What's in this package** — a table of the participant classes (link to the
   sibling `.java` files, which are same-directory relative links).
7. **The exercise** — the task / `TODO`s, any behavior table the tests assert,
   the rules of the game, a "Done means" with the `mvn test -Dtest='...'` command,
   and optional stretch goals.

Links to sibling source files are just the filename (same dir); the test file is
`../../../../test/java/patterns/<pattern>/<Name>Test.java`.