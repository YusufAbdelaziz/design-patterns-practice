---
name: pattern-scenarios
description: For every design pattern practiced here, motivate it with a concrete real-life scenario; catalog of scenarios chosen per pattern
metadata:
  type: project
---

# Pattern Scenarios

For **every** design pattern we practice in this repo, pin down a concrete
**real-life scenario that genuinely needs the pattern** — not an abstract
textbook definition. The scenario should make it obvious *why* the pattern
exists: what pain shows up without it, and what the pattern buys you.

**Why:** patterns stick when tied to a believable problem, not to memorized
intent sentences.

**How to apply:** prefer scenarios from real software domains (media apps,
banking, e-commerce, editors, network clients) over toy examples. Put each
pattern's scenario in its package README's "Real-life use case" section (see
[[pattern-package-intro]]), and append a section here as the running catalog.

---

## Iterator (Behavioral)

**Scenario: a music streaming app (Spotify-style).**

Picture the queue in a music player. Several parts of the app need to walk the
**same** playlist, and none of them should care *how* it's stored:

- The **now-playing bar** steps through one song at a time.
- The **full queue screen**, open at the same moment, is scrolled to a different
  spot — it holds its **own** position in the same list.
- A **"previous track"** gesture walks the list **backwards**.
- Under the hood the playlist might be a plain array today, a linked list
  tomorrow, or **pages streamed from the server** next release.

**Without the pattern:** every screen reaches into the playlist's guts
(`items[i]`, `node.next`, page-fetch logic) and they all trip over each other
sharing one position counter. Change storage from array to linked list and every
screen breaks.

**With the pattern:** each walker gets its **own cursor object**.
- Independent positions → the now-playing bar and the queue screen never
  interfere.
- Representation-blind → screens call `hasNext()` / `next()`, never touching the
  array.
- Multiple orders over one collection → forward for playback, `reversed()` for
  "go back".

Modeled here as a generic `Playlist<T>` (`patterns/iterator/`) that hands out
hand-written `Iterator<T>` cursors plus a `reversed()` view.

---

## Chain of Responsibility (Behavioral)

**Scenario: the expense-approval feature of a company purchasing tool**
(Concur / Expensify style, or GitHub required reviewers).

An employee files a purchase request; who may approve depends on the amount —
Team Lead (≤ $1,000), Manager (≤ $10,000), Director (≤ $100,000), CFO
(≤ $1,000,000), else denied. The employee shouldn't know the org chart; they
submit and the system routes it. When a "VP" tier is inserted later, the submit
code must not change.

**Without the pattern:** the requester (or one giant method) holds a tangle of
`if amount <= 1000 ... else if <= 10000 ...`; every reorg edits that tangle and
welds the sender to every receiver.

**With the pattern:** each approver is an object that either handles the request
or passes it to the next; they're linked into a chain; the sender fires at the
head and stays ignorant of who handles it. Reorg = rewiring the chain, not
editing handlers.

Same shape elsewhere: HTTP middleware pipelines, support-ticket escalation, UI
event bubbling, servlet filters.

Modeled here as an abstract `Approver` (`patterns/chainofresponsibility/`) with a
`linkTo`/`next` chain and a single inherited `handle()`, plus concrete
`TeamLead` / `Manager` / `Director` / `CFO` approvers.

---

## State (Behavioral)

**Scenario: the lifecycle of an e-commerce order.**

An order moves *pending → paid → shipped → delivered*, or off to *cancelled*. The
same event means different things per stage: `cancel()` voids a pending order,
voids *and refunds* a paid one, and is *refused* on a shipped one; `ship()` before
payment and `deliver()` before shipping are illegal.

**Without the pattern:** a `status` field plus a fat `switch (status)` duplicated
in every method (`pay`, `ship`, `deliver`, `cancel`); adding a stage means editing
all of them.

**With the pattern:** each stage is its own class implementing a shared `State`;
the order (context) forwards each event to the current state, which handles it
and, when the transition is legal, tells the order to switch states. New stage =
new class, no edits to existing methods.

Same shape elsewhere: payment flows, document publishing workflows, media players
(stopped/playing/paused), TCP sockets.

Modeled here as an `Order` context (`patterns/state/`) delegating to an abstract
`State`, with concrete `PendingState` / `PaidState` / `ShippedState` /
`DeliveredState` / `CancelledState`.

---

## Mediator (Behavioral)

**Scenario: a group chat channel (Slack channel / WhatsApp group).**

Members don't hold direct connections to each other; they post to the channel and
it fans the message out to the other members. Joining, leaving, muting, or
changing delivery rules is a change to the channel — the members are untouched.

**Without the pattern:** every participant holds references to every other and
calls them directly — an all-to-all mesh (up to *n²* links) with the interaction
rules duplicated in each participant.

**With the pattern:** each participant knows only the mediator; the mediator holds
the participants and owns all the "who receives what" logic in one place. Add,
remove, or re-route participants by changing the mediator alone.

Same shape elsewhere: air-traffic control tower, UI dialog coordinating its
widgets, message brokers.

Set up **tests-first** (see [[pattern-package-intro]]): `patterns/mediator/` has a
brief README and a failing `ChatRoomTest`; the user designs and builds the classes
(a chat-room hub + participants) themselves.

---

## Command (Behavioral)

**Scenario: a text editor's undo / redo.**

Every edit (type, delete, paste) is captured as a command object pushed onto a
history stack. Undo pops the last command and calls its `undo()`; redo re-runs it.
Because each edit knows how to reverse itself, the editor gets unlimited undo
without its core knowing any edit type — and the same objects power macros and
replayable edit logs.

**Without the pattern:** the invoker hard-codes every operation in a `switch`, and
undo needs a *parallel* switch re-implementing every inverse by hand; adding an
operation edits both, and do/undo logic drift apart.

**With the pattern:** each request is a self-contained object with `execute()` and
`undo()`; the invoker just calls those and keeps a history — never a switch. New
operation = new class; queueing, logging, macros, and redo fall out for free.

Same shape elsewhere: remote-control buttons, job/task queues, transactions,
GUI actions (toolbar + menu + shortcut sharing one command).

Set up **tests-first** (see [[pattern-package-intro]]): `patterns/command/` has a
brief README and a failing `UndoRedoTest`; the user designs the command
abstraction, the concrete commands, the receiver, and the undo/redo history.
