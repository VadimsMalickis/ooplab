## OOP Lab

This project is an Object-Oriented Programming lab application focused on demonstrating and practicing core OOP principles.

### Purpose
- To practice object-oriented design and implementation
- To organize logic into reusable classes and modules
- To support learning concepts such as encapsulation, inheritance, polymorphism, and abstraction

### Technologies Used
The project is intended to use standard application development technologies for an OOP-based codebase, including:
- An object-oriented programming language
- The language's standard library
- Basic build and runtime tooling for the selected development environment

### Libraries
- SQLite (via JDBC): Used for persistent local data storage.
- Java Swing: Used to build the desktop graphical user interface.

### Project Scope
This application is likely designed as a lab or coursework project rather than a production system. Its main goal is to demonstrate clean object-oriented structure and coding practice.

### Explanation of Architecture
The application follows a layered, object-oriented desktop architecture:

- **Presentation Layer (Swing UI):** Handles user interaction through frames, panels, buttons, forms, and tables. It captures input and displays results.
- **Business/Domain Layer:** Contains core classes and logic that model the system behavior. This layer applies OOP principles such as encapsulation, inheritance, and polymorphism.
- **Data Access Layer (JDBC + SQLite):** Manages database connectivity and CRUD operations. DAO-style classes isolate SQL and persistence details from business logic.

### Explanation of used design patterns
The project likely uses a small set of common design patterns suitable for a Java Swing application with SQLite:

- **Model-View-Controller (MVC):** Helps separate the user interface, application logic, and data handling. In a Swing-based application, frames and panels act as the view, domain/services act as the model, and event-handling logic often plays the controller role.
- **Singleton:** May be used for shared resources such as a database connection manager or application-wide configuration. This ensures controlled access to a single instance. Used in TodoSQLite class.
- **Builder:** Can be used to construct complex domain objects or UI forms step by step. This improves readability and makes object creation more flexible when many fields or configuration options are involved. Used in TodoItem class.
- **Observer:** Common in GUI applications where components respond to user actions and state changes through listeners and events. Swing's event model naturally supports this pattern. Used with Java Swing library events.




