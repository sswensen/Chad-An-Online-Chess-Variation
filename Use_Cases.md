# Fully Dressed Use Cases
## Table of Contents
1. [Use case name](#use-case-name)
2. [User Registration](#user-registration)
3. [Player-turn](#Player-turn)
4. [Lock Game](#lock-game)


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


### <a name="Player-turn">Player turn</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-015 |
| Use Case Name | Player turn |
| Overview | On a player’s turn they make a move. The player can only make a move when it is their turn |
| Type | Primary|
| Actors | Primary Actor: Player |
| Properties | <ul><li>Performance – N/A</li><li>Security – N/A</li><li>Other – N/A</li></ul> |
| Preconditions | <ul><li>The player must be logged in</li><li>The game must be started </li><li>It must be the player’s turn</li> |
| Flow | <ul><li>Main Flow - <ol><li>Player is notified that it’s their turn</li><li>player makes a move </li><li>Player’s turn ends</li></ol></li><li>Subflows - <ol><li>System checks for valid move</li><li>System checks for check or checkmate</li></ol></li> <li>Alternate Flows - <ol><li>player makes invalid move</li><li>Checkmate occurred, game is over</li></ol></li></ul> |
| Postconditions | <ul><li>It is the other player’s turn</li></ul> |
| Cross References | N/A |


### <a name="lock-game">Lock game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-012 |
| Use Case Name | Lock game |
| Overview | Once two players join a game, other players who received invitations cannot enter the game |
| Type | Primary |
| Actors | End User[Primary, Initiator] |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | Player sends out invitations and is waiting for another player to join the game |
| Flow | <ul><li>Main Flow<ol><li>End User 1 accepts invitation</li><li>End User 1 joins match created by End User 2</li></ol></li><li>Subflows<ol><li>System blocks other users from joining game</li><li>System deletes other invitations End User 2 sent out for this game</li></ol></li></ul>|
| Postconditions | <ul><li>The match begins with exactly 2 players</li><li>All other invitations to this match will be deleted</li></ul> |
| Cross References | N/A |