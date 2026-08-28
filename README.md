# Design Patterns Practice

A Java 21 + Maven repo for practicing the classic design patterns, one at a time.

## How this works

1. You name a pattern.
2. I add an exercise for it: one `README.md` in the pattern's package (explanation, real-life use case, UML, the task, and rules), some starter code with `TODO`s, and a JUnit test suite that fails.
3. You write the implementation yourself. I don't fill in the solution unless you ask.
4. Run the tests until they're green.

## Layout

```
src/main/java/patterns/<pattern-name>/README.md   # the one comprehensive brief for the pattern
src/main/java/patterns/<pattern-name>/            # starter code you edit
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
