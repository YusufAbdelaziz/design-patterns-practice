---
name: pattern-package-intro
description: Default workflow — tests-first briefs; the user builds every class to deduce the pattern's structure themselves
metadata:
  type: feedback
---

**Default workflow for a new pattern is TESTS-FIRST, no starter code.** The user
wants to build the intuition of *deducing* a pattern's structure — which class is
the hub, who references whom, whether there's an interface — so DO NOT pre-build
the production classes with fields/method signatures. Pre-built classes make the
pattern obvious and rob the user of the actual skill. (Decided 2026-08-28, applies
to all future patterns. The earlier solved patterns — iterator, chainofresponsibility,
state — were built in an older "fill in the `TODO`s" style; leave them as-is.)

**Why:** recognizing the smell and designing the participants IS the pattern skill;
filling method bodies in given classes is not.

**How to set up a new pattern (phase 1 — the exercise):**

1. Create the package `src/main/java/patterns/<pattern>/` with **only** a
   `README.md` (the brief). No `.java` production classes.
2. Create a failing JUnit suite in `src/test/java/patterns/<pattern>/` that pins
   the required **public API** (class names, constructors, methods) and behavior
   (incl. any exact output format). The test won't compile — the "cannot find
   symbol" errors are the user's to-build list. That is the intended red start.
3. The brief `README.md` contains, in order:
   - a short **format note** saying it's tests-first and they build every class;
   - **explanation** of the pattern (intent + how it works);
   - **the anti-pattern it replaces** — a concrete code smell + one-line "what it
     solves";
   - a **recall hook** (smell that triggers it / one-line essence / analogy /
     tell-it-apart from look-alikes);
   - a **real-life use case** (see [[pattern-scenarios]]);
   - a **GENERIC pattern UML** in Mermaid — the abstract *roles* (e.g. Mediator /
     Colleague), NOT a concrete solution diagram, with a note to map the roles
     onto the problem themselves;
   - **the task** ("build the classes to pass the tests; the tests define the
     API") and **rules of the game** — including the pattern's real *contract* so
     the tests can't be gamed by the anti-pattern (e.g. "a colleague must not
     reference another colleague");
   - **Done means** with the `mvn test -Dtest='...'` command;
   - optional **stretch goals**.

   Do **NOT** include a concrete participants table or a concrete solution UML in
   phase 1 — those give away the structure.

**Phase 2 — after the user solves it (optional, on request or when tidying):**
backfill the README with the concrete "What's in this package" participants table
and a concrete solution UML, so it becomes a complete reference like the earlier
solved patterns.

Links to sibling source files are just the filename (same dir); the test file is
`../../../../test/java/patterns/<pattern>/<Name>Test.java`.
