# DemoBlaze Automation Framework
<a href="https://selenium.dev"><img src="https://selenium.dev/images/selenium_logo_square_green.png" width="50" alt="Selenium"/></a>
![Java](https://img.shields.io/badge/Java-23-red) ![Selenium](https://img.shields.io/badge/Selenium-4.29-blue) ![TestNG](https://img.shields.io/badge/TestNG-7.11-orange) ![Allure](https://img.shields.io/badge/Reporting-AllureReports-green)

End-to-end test automation framework for DemoBlaze e-commerce website implementing high-level business scenarios.

### Website
Link: [demoblaze Website](https://www.demoblaze.com/)

## 🚀 Key Features

- **End-to-End Test Coverage** of core user journeys.
- **Page Object Model** and Fluent Pattern Interface design.
- **JSON Data-Driven** testing approach.
- **Multi-Layer Reporting**:
  - Allure Reports with screenshots.
  - Log4j2 execution logs.
- **Advanced Features**:
  - TestNG Listeners for custom reporting.
  - Smart element waits with fluent patterns.
  - Cross-browser support (Chrome/Firefox/Edge).
  - CI/CD ready (Jenkins/GitHub Actions compatible) [Working On It]

## 🛠️ Technologies Used

| Component          | Technology Stack |
|--------------------|------------------|
| Test Framework     | TestNG 7.11      |
| Language           | Java 23          |
| Web Automation     | Selenium 4       |
| Design Pattern     | POM + Fluent pattern  |
| Data Management    | JSON files       |
| Reporting & Logging         | Allure Reports + Log4j2|
| Build Tool         | Maven            |
| Visual Validation  | Screenshot embedding |

## 📂 Project Structure
```
demoblaze-automation/
├── src/
│ ├── main/java/
│ │ ├── pages/ # Page Objects with fluent methods
│ │ ├── core.ui/ # Reusable UI components
│ │ ├── listeners/ # Custom TestNG listeners
│ │ ├── utils/ # Helper classes
│ │ └── DriverManager/ # Driver/page factories
| | └── resources/ # All properties files
│ │
│ └── test/java/
│ ├── testcases/ # TestNG test classes
│ ├── resources ├── testdata/ # JSON test data files
│ 
│
├── logs/ # Log4j2 output
├── allure-results/ # Allure raw reports
├── screenshots/ # Captures
└── pom.xml # Maven dependencies
└── testng.xml # configuration file for organizing and executing tests in TestNG
```

### Installation
```bash
- git clone https://github.com/yourusername/demoblaze-automation.git
- cd demoblaze-automation
- use: mvn test -Pend-to-end-tests (To Run the Project Suite)
