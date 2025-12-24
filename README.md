A blood bank management system is a software application used to automate blood bank operations.
It helps manage the donors list, avalibility of blood group in a realiable and efficient way.
This repo is forked from from ours teams's original project repository, which was maintained under a single member's account.

**Features:**
1. Donor registration and management
2. Blood group wise inventory tracking
3. Blood request handling
4. User friendly interface

**Techonologies used:**
Programming language: Java
Database: MySql

### Database Design

Although the current implementation uses in-memory storage (ArrayList) for donor management,
the system has been designed with a relational database structure for future scalability.

A MySQL database schema was created to support:
- Persistent donor storage
- Hospital authentication
- Blood requests
- Inventory tracking

This design allows the application to be easily extended from an in-memory model to a
database-backed system using JDBC without changing the overall application flow.

### Business Logic

- Implemented donor eligibility checks based on:
  - Age
  - Weight
  - Health condition
- Blood stock levels are calculated dynamically by iterating over donor records and grouping them by blood group.
- Low stock is identified when donor count is below a defined threshold.
