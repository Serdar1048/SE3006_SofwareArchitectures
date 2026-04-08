# Lab 01: Layered Architecture Report

## Objective
Implement a three-layer software architecture using pure Java with manual dependency injection to understand strict layering principles and object connectivity without using frameworks like Spring Boot.

## Architecture Overview
The system follows a strict layered architecture with unidirectional data flow from top to bottom:

1. **Presentation Layer (OrderController)** - Handles user requests
2. **Business Layer (OrderService)** - Implements business logic
3. **Persistence Layer (ProductRepository)** - Manages data access

## Implementation Summary

### Task 1: Persistence Layer - ProductRepository
**Status:** ✅ Completed

Implemented data access functionality:
- Created `HashMap<Long, Product>` to store products with IDs
- Implemented `findById(Long id)` method to retrieve products from database
- Implemented `save(Product product)` method to persist product changes
- Initialized with sample data: MacBook Pro (5 units) and Logitech Mouse (20 units)

### Task 2: Business Layer - OrderService
**Status:** ✅ Completed

Implemented business logic:
- Defined `ProductRepository` dependency
- Implemented constructor injection to receive ProductRepository
- Completed `placeOrder(Long productId, int quantity)` method:
  - Retrieves product via repository
  - Validates product existence (throws `IllegalArgumentException` if not found)
  - Checks stock availability (throws exception if insufficient)
  - Reduces product stock by quantity
  - Persists updated product using repository

### Task 3: Presentation Layer - OrderController
**Status:** ✅ Completed

Implemented request handling:
- Defined `OrderService` dependency
- Implemented constructor injection
- Completed `handleUserRequest(Long productId, int quantity)` method:
  - Displays request information
  - Executes order placement in try-catch block
  - Prints "✅ Order Confirmed" on success
  - Prints "❌ ERROR: [message]" on failure

### Task 4: System Bootstrapping - Main Class
**Status:** ✅ Completed

Implemented system initialization:
- Created ProductRepository (bottom layer)
- Created OrderService with repository injection (middle layer)
- Created OrderController with service injection (top layer)
- Executed test scenarios:
  - Valid orders with sufficient stock
  - Orders exceeding available stock
  - Orders for non-existent products

## Architectural Compliance
✅ **Strict Layering:** Each layer only knows its immediate lower layer
- Presentation knows only Business
- Business knows only Persistence
- Persistence knows nothing about other layers

✅ **Unidirectional Data Flow:** All dependencies flow downward
✅ **Manual Dependency Injection:** All dependencies injected via constructors
✅ **No Comments:** Code follows business requirements without comment lines
✅ **English Implementation:** All code written in English

## Test Results
The system successfully demonstrated:
- Successful order placement when products exist and stock is available
- Constraint violations (insufficient stock) properly caught and reported
- Non-existent product handling with appropriate error messages

## Conclusion
The lab successfully demonstrated how strict layering enforces separation of concerns and maintains clear boundaries between technical responsibilities. Manual dependency injection provides explicit control over object collaboration without framework assistance.
