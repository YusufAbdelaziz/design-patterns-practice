# Command (Behavioral)

**Intent:** encapsulate a request as an object, thereby letting you parameterize
clients with different requests, queue or log requests, and support undoable
operations.

> **Format:** tests-first, **no starter code**. The suite
> ([`UndoRedoTest.java`](../../../../test/java/patterns/command/UndoRedoTest.java))
> is the spec — it pins the class names, constructors, and behavior. The
> structure — what abstraction ties your commands together, how each command
> reverses itself, how the history drives undo/redo — is what *you* design.

## What the pattern is

Normally a caller invokes an operation directly: `receiver.doThing(args)`. That
call happens *now* and vanishes — you can't store it, pass it around, queue it,
log it, or take it back. The Command pattern turns the request itself into an
**object**: a little class that bundles *the receiver, the method to call, and
the arguments* behind a uniform `execute()` method (and, when you need it, an
`undo()`). Once a request is an object, you can put it in a list (a history, a
queue, a macro), hand it to an "invoker" that knows nothing about what it does,
and reverse it later.

## The anti-pattern it replaces

Without it, an invoker hard-codes every operation — and, for undo, every *inverse*:

```java
// Smell: one place knows every operation AND how to reverse each one
void run(String op, String arg) {
    switch (op) {
        case "append": doc += arg; log.add("append:" + arg); break;
        case "delete": /* remove, and stash what was removed, somewhere... */ break;
        // add an operation -> edit this switch...
    }
}
void undo() {
    String last = log.removeLast();
    // ...and re-implement the inverse of every operation type here, by hand
}
```

Add an operation and you edit two switches; the undo logic and the do logic drift
apart; the invoker is welded to every concrete operation.

**What it solves:** each request becomes a self-contained object that knows how to
`execute()` *and* `undo()` itself. The invoker just calls those and keeps a
history — it never grows a switch. A new operation is a new class; queueing,
logging, macros, and redo all fall out because the request is now a value you can
hold.

## Recall hook

- **The smell that triggers it:** dispatching actions by a string/enum + `switch`,
  *especially* with a parallel "how to undo each action" switch — or any time you
  want undo/redo, a queue of operations, macros, or a log you can replay.
- **In one line:** *wrap a request as an object with `execute()` (and maybe
  `undo()`) so you can store, pass, queue, and reverse it.*
- **Analogy:** a restaurant order slip — the waiter writes the request down (an
  object); the kitchen executes it; slips can be queued, re-fired, or voided —
  the waiter never cooks.
- **Tell it apart:** **Strategy** swaps *how one operation is done* (an
  interchangeable algorithm, invoked now); **Command** packages *a whole request*
  to run later, queue, or undo. (And vs **Memento**: Memento snapshots *state* to
  restore; a Command reverses the *operation*.)

## Real-life use case

A **text editor's undo / redo**. Every edit — type, delete, paste — is captured as
a command object and pushed onto a history stack. **Undo** pops the last command
and calls its `undo()`; **redo** re-runs it. Because each edit knows how to
reverse itself, the editor supports unlimited undo without the editor core
knowing anything about individual edit types — and the same objects power macros
and collaborative-edit logs.

## The pattern shape (map it onto the problem yourself)

```mermaid
classDiagram
    class Command {
        <<interface>>
        +execute()
        +undo()
    }
    class ConcreteCommand {
        -Receiver receiver
        +execute()
        +undo()
    }
    class Receiver {
        +action()
    }
    class Invoker {
        -history
        +run(Command)
        +undo()
        +redo()
    }

    Command <|.. ConcreteCommand
    ConcreteCommand --> Receiver : calls
    Invoker --> Command : holds &amp; triggers
```

That's the pattern in the abstract. Deciding what the *receiver*, the *concrete
commands*, and the *invoker* are for a text editor — and giving each command
enough captured state to reverse itself — is your job.

## Your task

Build the classes, from scratch, that make the suite pass. Read
[`UndoRedoTest.java`](../../../../test/java/patterns/command/UndoRedoTest.java)
first — it fixes the public API. The behavior it expects:

- **execute** runs a command and records it as the new "last" command;
- **undo** reverses the last executed command (and remembers it for redo);
- **redo** re-applies the most recently undone command;
- running a **new** command **clears** the redo history (you've branched);
- **undo / redo with nothing to do** are silent no-ops (never throw).

### Rules of the game (what makes it Command, not a snapshot hack)

- Each command must reverse **itself** in `undo()`, capturing whatever it needs at
  `execute()` time (e.g. a delete must remember the text it removed). The
  invoker/history must **not** store `Document` text snapshots or contain any
  per-operation reverse logic.
- The history must be **open/closed**: adding a brand-new kind of edit is a new
  command class and requires **zero** changes to the history/invoker. If you'd
  have to touch the invoker to add an operation, the abstraction is wrong.
- Don't edit the test to fit your design — design to fit the test.

### Done means

```powershell
mvn test -Dtest='UndoRedoTest'
```

is green. Right now the package has no classes, so it won't compile — the
"cannot find symbol" errors name exactly the types you need to create.

## Stretch goals (optional, no tests)

- Add a `ReplaceCommand(Document, int from, String with)` — proof that a new edit
  is a new class and the history is untouched.
- Add a **macro command**: one command composed of a list of commands, whose
  `execute()`/`undo()` run the children forward / in reverse. (Composite × Command.)
- Give `CommandHistory` a `void replay()` that re-runs the whole history from a
  clean document — the "log of requests" superpower.
