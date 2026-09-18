# Design Patterns Practice

A Java 25 + Maven repo for practicing the classic design patterns, one at a time.

## How this works

1. You name a pattern.
2. I set it up **tests-first**: a `README.md` brief in the pattern's package (explanation, the anti-pattern it replaces, a recall hook, real-life use case, and a *generic* pattern UML) plus a JUnit suite that fails to compile. There's **no starter code** — the point is for you to deduce the structure.
3. You design and build every class yourself to make the tests pass. I don't hand you the solution unless you ask.
4. Run the tests until they're green.

## Layout

```
src/main/java/patterns/<pattern-name>/README.md   # the brief for the pattern
src/main/java/patterns/<pattern-name>/            # the classes you design and create
src/test/java/patterns/<pattern-name>/            # tests that define "done"
.claude/memory/pattern-scenarios.md               # the catalog of real-life scenarios
```

## Commands

```powershell
mvn test                                        # run everything
mvn test -Dtest='StrategyTest'                  # run one exercise's tests
mvn test -Dtest='patterns.strategy.*'           # run one package
mvn -q compile                                  # just check it compiles
```

## Progress

| Pattern | Category | Status |
| --- | --- | --- |
| [Iterator](src/main/java/patterns/iterator/README.md) | Behavioral | ✅ Done |
| [Chain of Responsibility](src/main/java/patterns/chainofresponsibility/README.md) | Behavioral | ✅ Done |
| [State](src/main/java/patterns/state/README.md) | Behavioral | ✅ Done |
| [Mediator](src/main/java/patterns/mediator/README.md) | Behavioral | 🔴 Not started |
| [Command](src/main/java/patterns/command/README.md) | Behavioral | 🔴 Not started |
