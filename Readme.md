# 🚀 Android Framework Build Logic Template

A **GitHub Repository Template** for structured and reusable **Gradle build logic** in Android projects. This template
includes custom Gradle plugins for **Jetpack Compose, library management, testing, publishing, linting, and static code
analysis**, making project setup and maintenance more efficient.

## Why This Template?

The purpose of this template is to **provide an optimal developer environment** without dictating how the app's code
should be structured. Developers are free to choose their own architecture, package structure, and third-party
dependencies. This template focuses only on **best practices for tooling, testing, and project setup**, allowing
developers to focus on writing clean and efficient code.

## Features

- **Pre-configured Developer Tools:**
    -  [x]  Dependency management
    -  [x]  Git hooks for automated checks
    -  [x]  Code formatting (via Ktlint)
    -  [x]  Static analysis (via Detekt)
    -  [x]  Customizable build configurations
    -  [x]  Pull request templates for better code reviews
    -  [x]  Continuous integration with GitHub Actions

- **Additional Tools and Integrations:**
    - [ ] **Hilt** for Dependency Injection (configurable via setup.gradle).
    - [ ] **Room** for local database management (removable via setup.gradle).
    - [ ] **Paparazzi** for UI testing (removable via setup.gradle).
    - [ ] **Danger** for pull request checks to ensure code quality.

- **Scalable Architecture:**
    -  [x] Configured to easily adapt to **multi-module projects**.
    -  [x] **Gradle version catalog** for managing dependencies across modules.

---

### Key Dependencies:

- **Ktlint**: Automatic code formatting.
- **Detekt**: Static analysis to detect code smells.
- **Git Hooks**: Automatically runs static analysis checks before commit.
- **Gradle Versions Plugin**: Keeps track of the latest dependency versions.
- **GitHub Actions**: Automated CI for every pull request.
- **Paparazzi**: UI testing library.

## Danger Integration

This template integrates **Danger** to perform checks on your pull requests, ensuring consistency and high-quality code.
You can review the checks in the **Dangerfile**. To use Danger correctly, ensure that you have granted the necessary
permissions in the **Repository Settings** under **Actions > General** and enable **read and write** access to
workflows.

## 🚀 Quick Start

### 🔹 Option 1: Use This Template

Click the **"Use this template"** button on GitHub to create a new repository with this setup.

### 🔹 Option 2: Clone the Repository and Setup

```sh
git clone https://github.com/VahidGarousi/Framework.git
cd Framework

```

## Rename Project

## 📦 Features

- [x] Pre-configured Gradle Plugins – Manage Android applications and libraries efficiently
- [x] Jetpack Compose Support – Simplified setup for Compose-based projects
- [x] Code Quality Tools – Integrated support for Ktlint, Detekt, and Jacoco
- [x] Testing Support – Configured with Kotest & Android Test plugins
- [x] Multi-module Support – Structured for scalable project architectures
- [x] Easy CI/CD Integration – Preconfigured Gradle tasks for publishing

## 📌 Available Plugins

| Implementation Class                                                                                                                                                                    | ID                                    | Description                                                       |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------|-------------------------------------------------------------------|
| [AndroidApplicationComposeConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidApplicationComposeConventionPlugin.kt) | framework.android.application         | Configures Android applications with Jetpack Compose support.     |
| [AndroidApplicationConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidApplicationConventionPlugin.kt)               | framework.android.application.flavors | Configures Android applications with flavor support.              |
| [AndroidApplicationFlavorsConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidApplicationFlavorsConventionPlugin.kt) | framework.android.application.compose | Configures flavors for Android applications with Compose support. |
| [AndroidApplicationJacocoConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidApplicationJacocoConventionPlugin.kt)   | framework.android.application.jacoco  | Configures Jacoco code coverage for Android applications.         |
| [AndroidDetektConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidDetektConventionPlugin.kt)                         | framework.android.test                | Configures Detekt static code analysis tool for Android projects. |
| [AndroidLibraryComposeConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidLibraryComposeConventionPlugin.kt)         | framework.jvm.library                 | Configures Android libraries with Jetpack Compose support.        |
| [AndroidLibraryConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidLibraryConventionPlugin.kt)                       | framework.android.library             | Configures Android libraries.                                     |
| [AndroidLibraryJacocoConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidLibraryJacocoConventionPlugin.kt)           | framework.android.library.compose     | Configures Jacoco code coverage for Android libraries.            |
| [AndroidLibraryPublisherConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidLibraryPublisherConventionPlugin.kt)     | framework.android.library.jacoco      | Configures publishing for Android libraries.                      |
| [AndroidTestConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/AndroidTestConventionPlugin.kt)                             | framework.jvm.publisher               | Configures testing for Android projects.                          |
| [JvmLibraryConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/JvmLibraryConventionPlugin.kt)                               | framework.android.publisher           | Configures JVM libraries.                                         |
| [JvmLibraryPublisherConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/JvmLibraryPublisherConventionPlugin.kt)             | framework.android.ktlint              | Configures publishing for JVM libraries.                          |
| [KotestConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/KotestConventionPlugin.kt)                                       | framework.android.detekt              | Configures Kotest testing for JVM projects.                       |
| [KotlinterConventionPlugin](https://github.com/VahidGarousi/Framework/blob/develop/build-logic/convention/src/main/kotlin/KotlinterConventionPlugin.kt)                                 | framework.android.detekt              | Configures Ktlint tool for code style checks in Android projects. |

## 🤝🏻 Contribute

Any PRs are very welcome! 😍 You can fix a bug, add a feature, optimize performance, and propose a new cool approach in
code-base architecture. Feel free to make a PR! 😌