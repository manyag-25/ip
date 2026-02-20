# Mercury – Your Personal Task Assistant

> "Stay on top of your tasks, one command at a time."

Mercury is a fast, CLI + GUI task manager built in Java. It helps you track todos, deadlines, and events with persistent storage and motivational quotes.

---

## Features

| Feature | Command | Example |
|---|---|---|
| Add Todo | `todo <desc>` | `todo buy groceries` |
| Add Deadline | `deadline <desc> /by <yyyy-mm-dd>` | `deadline submit report /by 2026-03-01` |
| Add Event | `event <desc> /from <time> /to <time>` | `event team meeting /from 2pm /to 4pm` |
| List all tasks | `list` | `list` |
| Mark as done | `mark <n>` | `mark 1` |
| Unmark task | `unmark <n>` | `unmark 1` |
| Delete task | `delete <n>` | `delete 2` |
| Find tasks | `find <keyword>` | `find report` |
| Motivation | `cheer` | `cheer` |
| Help | `help` | `help` |
| Exit | `bye` | `bye` |

---

## Quick Start

### Prerequisites
- JDK 21
- Gradle 8.x

### Running the App

```bash
./gradlew run
```

Or run the JAR directly:

```bash
java -jar build/libs/mercury.jar
```

### Setting up in IntelliJ

1. Open IntelliJ → `File` > `Open` → select the project directory
2. Set **JDK 21** as the project SDK
3. Set **Project language level** to `SDK default`
4. Run `mercury.Launcher.main()`

---

## Task Types

- **[T]** – Todo: a simple task with no date
- **[D]** – Deadline: a task with a due date (`by: MMM dd yyyy`)
- **[E]** – Event: a task with a start and end time

Status icons: `[X]` = done, `[ ]` = not done

---

## Running Tests

```bash
./gradlew test
```

For text UI tests:

```bash
cd text-ui-test && ./runtest.sh   # macOS/Linux
cd text-ui-test && runtest.bat    # Windows
```

---

## Data Storage

Tasks are saved automatically to `./data/duke.txt` in pipe-delimited format:

```
T| |buy groceries
D|X|submit report|2026-03-01
E| |team meeting|(from:2pm to:4pm)
```

---

## CI/CD

This project uses **GitHub Actions** for continuous integration. On every push to `master` or PR, the CI pipeline:

- [x] Runs all unit tests
- [x] Builds the shadow JAR

See [`.github/workflows/ci.yml`](.github/workflows/ci.yml) for details.
