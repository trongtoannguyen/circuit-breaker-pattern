# Circuit Breaker Pattern Implementation in Java

**Description:**
A production-ready circuit breaker library implementing the Circuit Breaker Pattern using the State Pattern. Provides fault tolerance for distributed systems with support for both synchronous and asynchronous execution, configurable failure thresholds, automatic recovery, and event-driven monitoring.

**Features:**
- ✅ Thread-safe state management (Closed, Open, Half-Open)
- ✅ Synchronous and asynchronous execution support
- ✅ Configurable failure thresholds and timeouts
- ✅ Automatic circuit reset with configurable intervals
- ✅ Event listener interface for monitoring
- ✅ Comprehensive unit tests with JUnit 5 and Mockito
- ✅ Sample application demonstrating usage

**Tech Stack:** Java, Maven, JUnit 5, Mockito, CompletableFuture