# DevVault

A simple code snippet manager written in Java using Swing and SQLite.

## Description
DevVault is a desktop Java application that allows storing, managing, and searching code snippets. It uses a local SQLite database (`DevVault.db`) for persistence and provides a graphical user interface (Swing) for easy use.

Main features:
- Add / Edit / Delete snippets (CRUD).
- Search by title, content, language, or tags.
- Tags stored as a comma-separated string in the database but handled as a List<String> in the Java model.
- Unit tests with JUnit 5 for basic behavior coverage.

## Features
- GUI form fields for:
  - Title (unique)
  - Programming Language
  - Tags (comma separated)
  - Code Content (monospaced)
- Table showing: ID, Title, Language, Tags, Timestamp
- Validation: Title and Content are required
- SQLite database via JDBC (`jdbc:sqlite:DevVault.db`)
- Automatic creation of the `snippets` table at startup if it does not exist

## Database Schema
The `snippets` table is created with the following schema:

- id INTEGER PRIMARY KEY AUTOINCREMENT
- title TEXT NOT NULL UNIQUE
- snippet_code TEXT NOT NULL
- programming_language TEXT NOT NULL
- tags TEXT
- timestamp DATETIME NOT NULL

(The table is created automatically on startup by the `InitializeTable` class.)

## Files / Packages (identified)
- `src/Main.java` — Application entry point (creates the table and opens the GUI).
- `src/GUI/SnippetGUI.java` — Swing UI, forms, listeners, and the table.
- `src/model/Snippet.java` — POJO for Snippet (getters/setters, matches, isValid, etc.).
- `src/daoMethods/SnippetDAO.java` — DAO for database operations (add, delete, update, findAll, findByTitle).
- `src/DataBaseHandling/DatabaseConnection.java` — Connection to SQLite (`jdbc:sqlite:DevVault.db`).
- `src/DataBaseHandling/InitializeTable.java` — Creates the `snippets` table if absent.
- `src/tests/` — JUnit tests:
  - `DatabaseConnectionTest`
  - `SnippetTest`
  - `SnippetDAOTest`
- `LICENSE` — MIT License

## Requirements
- Java SE (recommended Java 11 or newer)
- sqlite-jdbc driver on the classpath (e.g., org.xerial:sqlite-jdbc)
- JUnit 5 for running tests

> Note: The repository may not include a build system file (pom.xml or build.gradle). If you don't use an IDE that manages dependencies, ensure the `sqlite-jdbc` jar is on the classpath when compiling/running.

## Installation & Run Instructions

1. Clone the repo
```bash
git clone https://github.com/Xaralampos-Makridhs/DevVault.git
cd DevVault
```

2. Compile (if not using Maven/Gradle)
- On Unix-like systems (general approach):
```bash
# Assuming you have sqlite-jdbc.jar in lib/
# compile
javac -d out -cp "lib/sqlite-jdbc.jar" $(find src -name "*.java")

# run
java -cp "out:lib/sqlite-jdbc.jar" Main
```
- On Windows, classpath separators are `;` instead of `:`:
```cmd
javac -d out -cp "lib\sqlite-jdbc.jar" (list_of_java_files)
java -cp "out;lib\sqlite-jdbc.jar" Main
```

3. Run from an IDE
- Open the project in IntelliJ/Eclipse, add the `sqlite-jdbc` dependency (or place the jar in the project), and run the `Main` class.

On first run the `DevVault.db` file will be created in the current working directory and the `snippets` table will be created if absent.

## GUI Usage (quick)
- Fill Title, Programming Language, Tags (e.g. `java, jdbc`), and Code Content.
- Click "Add Snippet" to save.
- Select a row in the table to load fields, then click "Update Selected" to update.
- Select a row and click "Delete" to remove it.
- Use the search field + "Search" to filter the list (looks through title, content, language, tags).
- "Show All" displays all snippets.

## Tests
Tests use JUnit 5:
- Run tests from the IDE or a build tool that supports JUnit 5.
- Existing tests:
  - DatabaseConnectionTest — checks DB connection.
  - SnippetTest — validates model logic (matches, tags conversion, validation).
  - SnippetDAOTest — tests DAO flows: insert -> update -> findAll -> deleteById.

Note: Tests operate on the real `DevVault.db`. For isolated tests, consider using a separate database or switch tests to an in-memory SQLite database (`jdbc:sqlite::memory:`).

## Suggestions / Improvements
- Add a build file (Maven `pom.xml` or Gradle `build.gradle`) for dependency management and CI integration.
- Improve validation and error handling (better logging and user feedback).
- Add export/import (JSON, CSV) for snippets.
- Improve UI/UX (theme, layout) or migrate to JavaFX.
- Use in-memory DB for test isolation or create a temporary test DB to avoid modifying production data during tests.
- Add a GitHub Actions workflow to automatically run tests on push/PR.

## Contributing
To contribute:
- Fork, create a branch, commit your changes, and open a PR.
- For larger features, open an issue first to discuss design.

## License
This project is licensed under the MIT License — see the `LICENSE` file in the repository.
