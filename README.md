# Custom Checkstyle Rule: Ensure `@Column` annotations have `columnDefinition` attribute

## Overview

This custom Checkstyle rule is designed to enforce a coding standard where every `@Column` annotation in your Java code
must have a `columnDefinition` attribute defined. This rule helps ensure that database column definitions are explicitly
specified, promoting better database schema management and reducing potential issues during database migrations.

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
    - [Configuration](#configuration)
- [Example](#example)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Before using this custom Checkstyle rule, ensure that you have the following prerequisites in place:

- Java Development Kit (JDK) 8 or higher
- A Java project using Checkstyle where you want to apply this custom rule.

### Installation

To use this custom Checkstyle rule in your project, you can add it as a dependency from a Maven repository. Include it
in your project's Checkstyle configuration, and Checkstyle will download it automatically during the build.

Add the following dependency to your project's build configuration (e.g., `build.gradle` for Gradle or `pom.xml` for
Maven):

#### Gradle:

```groovy
dependencies {
  // Other dependencies
  checkstyle 'tech.baseblocks:checkstyle-columndefinition-rule:0.0.1'
}
```
#### Maven:

```xml
<dependency>
    <!-- Other dependencies -->
    <groupId>tech.baseblocks</groupId>
    <artifactId>checkstyle-columndefinition-rule</artifactId>
    <version>0.0.1</version>
    <scope>test</scope>
</dependency>
```
## Usage
Configure Checkstyle to use this custom rule in your Checkstyle configuration file (e.g., checkstyle.xml):
```xml
<module name="Checker">
  <module name="TreeWalker">
    <!-- Other Checkstyle modules -->
      <module name="ColumnAnnotationChecker"/>
  </module>
</module>
```

### Configuration
This custom rule does not require any specific configuration. It will check all @Column annotations for the presence of the columnDefinition attribute by default.

## Example
Let's consider a simple example of how this custom rule works:
```java
import javax.persistence.Column;

@Entity
@Table(name = "employee")
public class Employee {
    
@Column(name = "employee_name", nullable = false, length = 50)
private String name; // This will trigger a violation because 'columnDefinition' is missing.
}
```
In this example, the absence of the `columnDefinition` attribute in the `@Column` annotation will trigger a violation when you run Checkstyle.

## Contributing
If you find any issues, have suggestions, or would like to contribute to this custom Checkstyle rule, please feel free to open an issue or submit a pull request on the GitHub repository.

## License
This custom Checkstyle rule is licensed under Apache License, Version 2.0

---
Disclaimer: This custom Checkstyle rule is provided as-is without any warranty. Use it at your own risk.