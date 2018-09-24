# Fully Dressed Use Cases
## Table of Contents
1. [Use case name](#use-case-name)
2. [Register User](#register-user)
2. [Unregister User](#unregister-user)
3. [Reject Invitation](#invitation-rejection)
4. [Lock Game](#lock-game)
5. [Create Game](#create-game)
6. [Make Move](#make-move)


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


### <a name="register-user">Register User</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-002 |
| Use Case Name | Register User |
| Overview | A user registers themselves with the application. This will consist of capturing an email, nickname, and password. Associating all users with unique accounts allows us to save which users are in an active game. This will make it possible for a user to enter the game, make a move, and exit (possibly into another game). |
| Type | Primary |
| Actors | End User [primary, initiator] |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | User should not already exist. |
| Flow | <ul><li>Main Flow<ol><li>User inputs email</li><li>User inputs nickname</li><li>User inputs password</li></ol></li><li>Subflows<ol><li>After email input, system checks if email is unique</li><li>After nickname input, system checks if nickname is unique</li><li>After password input, system checks if password is strong and hashes</li></ol></li><li>Alternate Flows<ol><li>Email is already in system</li><li>Nickname is already registered</li><li>User inputs weak password</li></ol></li></ul> |
| Postconditions | Account is created (i.e. email, nickname, and password (hashed) are stored in the database) |
| Cross References | N/A|

### <a name="unregister-user">Unregister User</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-003 |
| Use Case Name | Unregister User |
| Overview | A user unregisters themselves with the application. This will consist of removing an email, nickname, and password. Removing a user unlinks them and deassociates them with all their games, as well as removing their account. |
| Type | Primary |
| Actors | End User [primary, initiator] |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | User should already exist. |
| Flow | <ul><li>Main Flow<ol><li>User deletes account</li></ol></li><li>Subflows<ol><li>System deletes email</li><li>System deletes nickname</li><li>System deletes password</li></ol></li><li>Alternate Flows<ol><li>User is not in system</li></ol></li></ul> |
| Postconditions | Account is deleted (i.e. email, nickname, and password (hashed) are removed in the database) |
| Cross References | N/A|

 ### <a name="invitation-rejection">Reject Invitation</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-007 |
| Use Case Name | Reject Invitation |
| Overview | If a user receives an invitation, the user can reject that invitation. The user who sent the invitation will receive a notification that their invitation was rejected. |
| Type | Primary |
| Actors | <ul><li>End User 1 [primary, initiator]</li><li>End User 2 [primary]</li> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | End User 2 has sent End User 1 an invitation to a game |
| Flow | <ul><li>Main Flow<ol><li>End User 1 rejects invitation from End User 2</li><li>End User 1 receives a notification that End User 2 has rejected the invitation</li></ol></li><li>Subflows<ol><li>System deletes End User 1's invitation</li><li>System notifies End User 2 of invitation rejection</li></ol></li></ul> |
| Postconditions | <ul><li>End User 1's invitation will be gone</li><li>End User 2 will have a rejection notification</li></ul> |
| Cross References | N/A |

### <a name="lock-game">Lock game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-012 |
| Use Case Name | Lock Game |
| Overview | Once two players join a game, other players who received invitations cannot enter the game |
| Type | Primary |
| Actors | End User[Primary, Initiator] |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | Player sends out invitations and is waiting for another player to join the game |
| Flow | <ul><li>Main Flow<ol><li>End User 1 accepts invitation</li><li>End User 1 joins match created by End User 2</li></ol></li><li>Subflows<ol><li>System blocks other users from joining game</li><li>System deletes other invitations End User 2 sent out for this game</li></ol></li></ul>|
| Postconditions | <ul><li>The match begins with exactly 2 players</li><li>All other invitations to this match will be deleted</li></ul> |
| Cross References | N/A |

### <a name="create-game">Create game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-004 |
| Use Case Name | Create game |
| Overview | Allows a Player to make a new game |
| Type | Primary |
| Actors | Primary Actor: Player |
| Properties | <ul><li>Performance - </li><li>Security - </li><li>Other - </li></ul> |
| Preconditions | <ul><li>A Player is logged-in</li><li>A Player is in the main menu</li></ul>  |
| Flow | <ul><li>Main Flow - <ol><li>A Player selects the option to create a new match</li><li>The system creates a new match</li></ol></li><li>Subflows - <ol><li>The system brings the Player into a game lobby</li><li>The system displays options of inviting and starting a game (Once the other player is in the game lobby)</ol></li></ul> |
| Postconditions | <ul><li>The match is created</li><li>The Player is in a game lobby</li><li>The Player has the options of inviting and starting the match (Once the other player is in the game lobby)</li></ul> |
| Cross References | 

### <a name="make-move">Make Move</a>
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
