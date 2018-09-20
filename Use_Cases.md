# Fully Dressed Use Cases
## Table of Contents
1. [Use case name](#use-case-name)
2. [User Registration](#user-registration)
3. [Invitation Rejection](#invitation-rejection)
4. [Player Turn](#player-turn)


## Use Cases
### <a name="use-case-name">Use Case Name</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-xxx |
| Use Case Name | Name of Use Case |
| Overview | Overview of use case |
| Type | Primary, Secondary, Optional |
| Actors | List all actors |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | List all preconditions |
| Flow | <ul><li>Main Flow<ol><li>Do some stuff</li><li>Do some more stuff</li></ol></li><li>Subflows</li><li>Alternate Flows</li></ul> |
| Postconditions | Enter any postconditions |
| Cross References | Link to any other reference here |


### <a name="user-registration">User Registration</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-002 |
| Use Case Name | User Registration |
| Overview | A user shall have the ability to register themselves with the application. This will consist of capturing an email, nickname, and password. Associating all users with unique accounts allows us to save which users are in an active game. This will make it possible for a user to enter the game, make a move, and exit (possibly into another game). |
| Type | Primary |
| Actors | End User [primary, initiator] |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | User should not already exist. |
| Flow | <ul><li>Main Flow<ol><li>User inputs email</li><li>User inputs nickname</li><li>User inputs password</li></ol></li><li>Subflows<ol><li>After email input, system checks if email is unique</li><li>After nickname input, system checks if nickname is unique</li><li>After password input, system checks if password is strong and hashes</li></ol></li><li>Alternate Flows<ol><li>Email is already in system</li><li>Nickname is already registered</li><li>User inputs weak password</li></ol></li></ul> |
| Postconditions | Account is created (i.e. email, nickname, and password (hashed) are stored in the database) |
| Cross References | N/A|

 ### <a name="invitation-rejection">Invitation Rejection</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-007 |
| Use Case Name | Invitation Rejection |
| Overview | If a user receives an invitation, the user can reject that invitation. The user who sent the invitation will receive a notification that their invitation was rejected. |
| Type | Primary |
| Actors | End User 1 [primary, initiator], End User 2 [primary] |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | End User 2 has sent End User 1 a notification |
| Flow | <ul><li>Main Flow<ol><li>End User 1 rejects invitation from End User 2</li><li>End User 1 receives a notification that End User 2 has rejected the invitation</li></ol></li><li>Subflows<ol><li>System deletes End User 1's invitation</li><li>System notifies End User 2 of invitation rejection</li></ol></li></ul> |
| Postconditions | <ul><li>End User 1's invitation will be gone</li><li>End User 2 will have a rejection notification</li></ul> |
| Cross References | N/A |

### <a name="player-turn">Player Turn</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-015 |
| Use Case Name | Player Turn |
| Overview | On a player’s turn they make a move. The player can only make a move when it is their turn. |
| Type | Primary|
| Actors | Player [primary, initiator] |
| Properties | <ul><li>Performance – N/A</li><li>Security – N/A</li><li>Other – N/A</li></ul> |
| Preconditions | <ul><li>The player must be logged in</li><li>The game must be started </li><li>It must be the player’s turn</li> |
| Flow | <ul><li>Main Flow<ol><li>Player is notified that it’s their turn</li><li>player makes a move </li><li>Player’s turn ends</li></ol></li><li>Subflows<ol><li>System checks for valid move</li><li>System checks for check or checkmate</li></ol></li> <li>Alternate Flows<ol><li>Player makes invalid move</li><li>Checkmate occurred, game is over</li></ol></li></ul> |
| Postconditions | It is the other player’s turn |
| Cross References | N/A |