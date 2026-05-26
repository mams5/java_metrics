# 📘 Code Readability Analyzer

A tool for analyzing **Java code readability**, developed as an academic project for the Software Readability course.

---

## 🚀 Purpose

The tool performs analysis on Java source files to identify aspects related to code readability, generating metrics and scores that help evaluate software quality.

### 📊 Analyzed Metrics

- 📏 Line length
- 📝 Comment proportion
- 📐 Spacing and formatting
- 🧱 Structural density
- 📊 Readability metrics

---

# 🏗️ Project Architecture

```text
src/main
│
├── analyzer
│   ├── lineLength.java
│   ├── methodSizes.java
│   ├── propComments.java
│   ├── spacingLines.java
│   └── structuresDensity.java
│
├── context
│   └── codeContext.java
│
├── report
│   └── reportGenerator.java
│
├── scoring
│   └── scoringEngine.java
│
└── Main.java
```

---

## 📂 Module Description

### 🔍 analyzer

Responsible for code readability analysis metrics.  
Each class implements a specific readability metric.

| Class | Responsibility |
|---|---|
| `lineLength` | Analyzes line length |
| `methodSizes` | Checks method sizes |
| `propComments` | Evaluates comment proportion |
| `spacingLines` | Analyzes spacing and organization |
| `structuresDensity` | Measures code structural density |

---

### 🧠 context

Stores and shares the analyzed code context.

---

### 📄 report

Responsible for generating analysis reports.

---

### 📊 scoring

Centralizes the final score calculation logic.

---

### ▶️ Main

Main entry point of the application.

---

# ⚙️ Technologies Used

- ☕ Java
- 📦 Maven
- 🌳 JavaParser

---

# 🧩 Dependencies

## JavaParser

JavaParser is used to perform Java source code parsing and syntactic analysis, enabling the interpretation of structures such as:

- Classes
- Methods
- Comments
- Blocks
- Conditional structures

---

## Maven Installation Guide (Windows)

Follow the steps below to install and configure Apache Maven on Windows.

### 1. Install Java JDK

Make sure Java is installed before configuring Maven.

Check your Java installation:

```bash
java -version
```

If Java is not installed, download and install a JDK:

- https://adoptium.net/temurin/releases/

---

## 2. Download Maven

Download the latest binary version of Maven from the official website:

- https://maven.apache.org/download.cgi

Download the file named:

```text
Binary zip archive
```

Example:

```text
apache-maven-3.x.x-bin.zip
```

---

## 3. Extract Maven

Extract the downloaded ZIP file to a directory of your choice.

Example:

```text
C:\Program Files\Maven
```

After extraction, the folder structure should look like this:

```text
Maven
 ├── bin
 ├── boot
 ├── conf
 └── lib
```

---

## 4. Configure Environment Variables

Open:

```text
Control Panel → System → Advanced System Settings → Environment Variables
```

### Create a new system variable

Variable name:

```text
MAVEN_HOME
```

Variable value:

```text
C:\Program Files\Maven
```

---

### Update the `Path` variable

Add the following entry:

```text
%MAVEN_HOME%\bin
```

---

## 5. Restart the Terminal

Close and reopen your terminal (CMD or PowerShell).

---

## 6. Verify Installation

Run the following command:

```bash
mvn -version
```

Expected output:

```text
Apache Maven x.x.x
Java version: xx
```

If the version information appears, Maven has been successfully installed and configured.

---

# ▶️ How to Run

## 1️⃣ Clone the repository

```bash
git clone <repository-url>
```

## 2️⃣ Access the project folder

```bash
cd project-name
```

## 3️⃣ Build with Maven

```bash
mvn clean install
```

## 4️⃣ Run the project

```bash
mvn exec:java
```

---

# 📈 Future Improvements

- [ ] Graphical interface
- [ ] PDF report export
- [ ] Metrics dashboard
- [ ] Multiple file support
- [ ] Additional readability metrics
- [ ] CI/CD integration

---

# 👥 Team

Project developed for the **Software Readability** course.

---

# 📚 Applied Concepts

- Static code analysis
- Software Engineering
- Quality metrics
- Code readability
- Modular architecture
- Clean Code

---

# 📄 License

Academic project developed for educational purposes.