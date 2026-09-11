# tetris-java

A playable Tetris that runs in the terminal, built to be a clean example of design patterns and thorough testing rather than just a game.

## What it does

The classic falling-block game: tetrominoes drop, full rows clear, score and speed climb. It adds a pause, a special line-selection bonus mode, a start menu and a game-over screen, and a soundtrack.

The point of the project was the architecture. It is a strict **MVC** design with several patterns doing real work:

- **State** — start menu, playing, game over.
- **Command** — every input (move, rotate, drop, pause) is a command object.
- **Strategy** — rotate-left and rotate-right as interchangeable strategies.
- **Factory** — piece creation and random piece selection.
- **Observer** — the board notifies views of changes.

## Stack

Java 21, [Lanterna](https://github.com/mabe02/lanterna) for terminal graphics, Gradle. Tested with JUnit 5 and Mockito, with **JaCoCo** coverage and **PIT** mutation testing.

## How to run

```bash
./gradlew run     # play
./gradlew test    # run the test suite
```

## What I built

Group project of three for the Software Design and Testing Lab course (2024/25). My part:

- Restructured the code into the **MVC** architecture and introduced the **Factory**, **Command** and **Strategy** patterns.
- Built the **bonus mode** and its interface.
- Wrote most of the model, state and view **tests**, and set up **PIT mutation testing** and **JaCoCo** coverage.

Teammates wrote the initial piece, board and score classes and the documentation and screenshots.

## What I would do differently

Decouple the sound system from the game loop (it currently blocks in places), and drive rendering from the observer events more consistently instead of redrawing whole views. I would also raise the mutation-test score on the controller, which is weaker than the model.
