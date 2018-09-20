# Fully Dressed Use Cases
## Table of Contents
1. [Use case name](#use-case-name)
2. [User Registration](#user-registration)


## Use Cases
### <a name="use-case-name">Use Case Name</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-xxx |
| Use Case Name | Name of Use Case |
| Overview | Overview of use case |
| Type | Primary, Secondary, Optional |
| Actors | List all actors |
| Properties | <ul><li>Performance - </li><li>Security - </li><li>Performance - </li></ul> |
| Preconditions | List all preconditions |
| Flow | <ul><li>Main Flow - <ol><li>Do some stuff</li><li>Do some more stuff</li></ol></li><li>Subflows - </li><li>Alternate Flows - </li></ul> |
| Postconditions | Enter any postconditions |
| Cross References | Link to any other reference here


### <a name="user-registration">User Registration</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-002 |
| Use Case Name | User Registration |
| Overview | A user shall have the ability to register themselves with the application. This will consist of capturing an email, nickname, and password. Associating all users with unique accounts allows us to save which users are in an active game. This will make it possible for a user to enter the game, make a move, and exit (possibly into another game). |
| Type | Primary |
| Actors | End User [primary, initiator] |
| Properties | <ul><li>Performance - </li><li>Security - </li><li>Performance - </li></ul> |
| Preconditions | User should not already exist. |
| Flow | <ul><li>Main Flow - <ol><li>User inputs email</li><li>User inputs nickname</li><li>User inputs password</li></ol></li><li>Subflows - <ol><li>After email input, system checks if email is unique</li><li>After nickname input, system checks if nickname is unique</li><li>After password input, system checks if password is strong and hashes</li></ol></li><li>Alternate Flows - <ol><li>Email is already in system</li><li>Nickname is already registered</li><li>User inputs weak password</li></ol></li></ul> |
| Postconditions | Account is created (i.e. email, nickname, and password (hashed) are stored in the database) |
| Cross References | |
