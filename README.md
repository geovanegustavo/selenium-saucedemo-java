# UI-E2E-Selenium-Saucedemo

End-to-end UI test automation for [Sauce Demo](https://www.saucedemo.com/) (login, inventory and shopping cart flows), built with **Selenium WebDriver + Java + JUnit 5**, following the **Page Object Model** pattern.

## Tech Stack

| Technology              | Purpose                                        |
| ----------------------- | ---------------------------------------------- |
| Java 17                 | Language                                       |
| Maven                   | Build / dependency management                  |
| Selenium WebDriver 4    | Browser automation                             |
| WebDriverManager        | Automatic driver download & management         |
| JUnit 5                 | Test framework (parallel execution)            |
| Allure                  | Test reporting (steps, screenshots, attachments) |
| java-dotenv             | Configuration via `.env` / environment variables |

## Test Scenarios

**Login (`LoginTest`)**
- Login with valid credentials
- Logout after a successful login
- Login with invalid password (error message)
- Login with a locked-out user (error message)
- Login with empty fields (error message)

**Inventory (`InventoryTest`)**
- Add a product to the cart
- Remove a product from the cart

**Cart (`CartTest`)**
- Verify the correct product (name, price) appears in the cart after being added

Tests can run in **parallel** (each scenario opens its own browser instance) — see `src/test/resources/junit-platform.properties`.

## Project Structure

```
├── .env.example              # Template for configuration values
├── .github/workflows/ci.yml  # GitHub Actions CI pipeline
├── pom.xml                   # Maven configuration
└── src
    ├── main/java/com/saucedemo
    │   ├── pages             # Page Objects (LoginPage, InventoryPage, CartPage, ...)
    │   └── utils             # DriverFactory, DriverManager, EnvConfig, ElementHighlighter
    └── test/java/com/saucedemo
        ├── base              # BaseTest, TestData, AllureListener
        └── tests             # LoginTest, InventoryTest, CartTest
```

## Prerequisites

- Java 17 (JDK)
- Maven 3.9+
- A browser: **Chrome** (default), **Firefox** or **Edge**

The matching browser driver (chromedriver, geckodriver, msedgedriver) is downloaded automatically by **WebDriverManager** — no manual setup needed.

## Configuration

The project reads its configuration from a `.env` file at the project root. Environment variables are also supported (useful for CI/CD).

1. Create the file from the template:

```bash
cp .env.example .env
```

2. Fill in the values (see the reference below):

| Variable                | Description                                    |
| ----------------------- | ---------------------------------------------- |
| `BASE_URL`              | Base URL of the site under test                |
| `STANDARD_USER`         | Valid standard user                            |
| `STANDARD_PASSWORD`     | Password for the standard user                 |
| `LOCKED_OUT_USER`       | Locked-out user (negative login test)          |
| `PROBLEM_USER`          | Optional: problem user                         |
| `PERFORMANCE_GLITCH_USER`| Optional: performance glitch user             |
| `INVALID_PASSWORD`      | Deliberately invalid password (negative test)  |

> `.env` is gitignored. On CI (GitHub Actions) the values are injected as environment variables / secrets, so no `.env` file is required — `EnvConfig` falls back to system environment variables when the file is missing.

## Running the Tests

Run the full suite (headed mode, Chrome by default):

```bash
mvn test
```

### Options

| Option                                        | Description                             |
| --------------------------------------------- | --------------------------------------- |
| `-Dheadless=true`                             | Run in headless mode                    |
| `-Dbrowser=chrome` \| `firefox` \| `edge`     | Choose the browser                      |
| `-Dtest=LoginTest`                            | Run only one test class                 |
| `-Dtest=LoginTest#deveRealizarLoginComSucesso`| Run a single test method                |

Examples:

```bash
# Headless Chrome (great for CI)
mvn test -Dheadless=true

# Firefox, headed
mvn test -Dbrowser=firefox

# Only login tests
mvn test -Dtest=LoginTest
```

The same options can be set through environment variables: `HEADLESS=true`, `BROWSER=firefox`.

## Reports

### Allure

Test execution produces Allure results in `target/allure-results`.

Serve a local HTML report:

```bash
mvn allure:serve
```

Or generate the report to disk (`target/site/allure-maven-plugin`):

```bash
mvn allure:report
```

On failure, the `AllureListener` attaches a **screenshot** to the report and also saves it to `target/screenshots`.

## CI/CD (GitHub Actions)

The repository includes a workflow (`.github/workflows/ci.yml`) that runs the tests on every push/PR to `main` (and can be triggered manually):

- Ubuntu runner with JDK 17 (Temurin) and Maven caching
- Tests run headless in Chrome
- Generates the Allure HTML report
- Uploads as artifacts: `allure-results`, `allure-report` and `screenshots` (even when tests fail)

The configuration values are provided through **GitHub Secrets** (Settings → Secrets and variables → Actions): `BASE_URL`, `STANDARD_USER`, `STANDARD_PASSWORD`, `LOCKED_OUT_USER`, `PROBLEM_USER`, `PERFORMANCE_GLITCH_USER`, `INVALID_PASSWORD`.
