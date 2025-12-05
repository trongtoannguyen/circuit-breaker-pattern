# Circuit Breaker Java

[![Maven Central](https://img.shields.io/maven-central/v/io.github.trongtoannguyen/circuit4J)](https://search.maven.org/artifact/io.github.trongtoannguyen/circuit4J)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

A production-ready Circuit Breaker pattern implementation for Java using the State Pattern.

## Features

- ✅ Thread-safe state management (Closed, Open, Half-Open)
- ✅ Synchronous and asynchronous execution support
- ✅ Configurable failure thresholds and timeouts
- ✅ Automatic circuit reset with configurable intervals
- ✅ Event listener interface for monitoring
- ✅ Zero runtime dependencies
- ✅ Java 11+ compatible

## Installation

### Maven

```xml
<dependency>
    <groupId>io.github.trongtoannguyen</groupId>
    <artifactId>circuit4J</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

implementation 'io.github.trongtoannguyen:circuit4J:1.0.0'

<!-- ## Quick Start
## Documentation

[Link to detailed docs] -->

## License

Apache License 2.0 - see [LICENSE](LICENSE) file