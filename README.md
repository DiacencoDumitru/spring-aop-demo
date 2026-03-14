# Spring AOP Example

## What is AOP?

**Aspect-Oriented Programming (AOP)** is a programming paradigm that helps improve modularity by separating cross-cutting concerns from business logic.

Common cross-cutting concerns include:

- Logging
- Exception handling
- Access control (security)

Instead of duplicating this logic across many classes, AOP extracts it into separate components called **aspects**. This keeps the core business logic cleaner and easier to maintain.

In this project:

- **Cross-cutting concerns** (logging, security, exception handling) are handled by aspects.
- **Business logic** remains focused only on application functionality.

---

## Code Impact

Applying AOP simplified the codebase:

- ❌ **Before AOP:** 67 lines of code  
- ✅ **After AOP:** 41 lines of code

This demonstrates how AOP reduces code duplication and improves maintainability.
