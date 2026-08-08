# Design Patterns Practice

A Java 21 + Maven repo for practicing the classic design patterns, one at a time.

## How this works

1. You name a pattern.
2. I add an exercise for it: a `README.md` with the scenario, some starter code with `TODO`s, and a JUnit test suite that fails.
3. You write the implementation yourself. I don't fill in the solution unless you ask.
4. Run the tests until they're green.

## Layout

```
src/main/java/patterns/<pattern-name>/    # starter code you edit
src/test/java/patterns/<pattern-name>/    # tests that define "done"
exercises/<pattern-name>.md               # the brief for each exercise
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
| _(none yet)_ | | |
