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