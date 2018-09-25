# Fully Dressed Use Cases
## Table of Contents
1. [Use case name](#use-case-name)
2. [Switch Game](#switch-game)
2. [Resume Game](#resume-game)
2. [Register User](#register-user)
2. [Unregister User](#unregister-user)
3. [Reject Invitation](#invitation-rejection)
4. [Lock Game](#lock-game)
5. [Create Game](#create-game)
6. [Make Move](#make-move)
7. [View Profile](#View-Profile)
8. [End game](#end-game)
9. [Quit game](#Quit-game)
10. [Join Game](#join-game)
11. [Play Game](#play-game)
12. [Log in](#log-in)


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

### <a name="switch-game">Switch Game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-026 |
| Use Case Name | Switch Game |
| Overview | Allows player to switch from one game to another |
| Type | Primary |
| Actors | Primary Actor: Player |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | Player is already playing one game and has another game he/she can play |
| Flow | <ul><li>Main Flow<ol><li>Suspend playing of current game</li><li>Resumes another game</li></ol></li><li>Alternate Flows<ol><li>Suspend playing of current game</li><li>Create a new game</li></ol></li></ul> |
| Postconditions | Player is now playing another game |
| Cross References | <ul><li>[Play Game](#play-game)</li><li>[Resume Game](#resume-game)</li><ul> |

### <a name="resume-game">Resume Game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-044 |
| Use Case Name | Resume Game |
| Overview | Allows player to continue playing a game |
| Type | Primary |
| Actors | Primary Actor: Player |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | Player is in the main menu, and has a game they can continue playing that has not ended yet |
| Flow | <ul><li>Main Flow<ol><li>Resumes playing selected game</li></ol></li><li>Subflows - <ol><li>System loads selected game</li></ol></li></ul> |
| Postconditions | Player is now playing selected game |
| Cross References | N/A |

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

 ### <a name="respond-to-invitation">Respond to Invitation</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-xxx |
| Use Case Name | Respond to Invitation |
| Overview | If a user receives an invitation, the user can reject that invitation. The user who sent the invitation will receive a notification that their invitation was rejected. |
| Type | Primary |
| Actors | <ul><li>End User 1 [primary, initiator]</li><li>End User 2 [primary]</li> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | End User 2 has sent End User 1 an invitation to a game |
| Flow | <ul><li>Main Flow<ol><li>End User 1 rejects invitation from End User 2</li><li>End User 1 receives a notification that End User 2 has rejected the invitation</li></ol></li><li>Subflows<ol><li>User can accept invitation</li><li>User can reject invitation</li><li>Sender can cancle invitation</li></ol></li></ul> |
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

### <a name="start-game">Start game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-xxx |
| Use Case Name | Start game |
| Overview | Allows a Player to make a new game |
| Type | Primary |
| Actors | Primary Actor: Player |
| Properties | <ul><li>Performance - </li><li>Security - </li><li>Other - </li></ul> |
| Preconditions | <ul><li>A Player is logged-in</li><li>A Player is in the main menu</li></ul>  |
| Flow | <ul><li>Main Flow - <ol><li>A Player selects the option to create a new match</li><li>The system creates a new match</li></ol></li><li>Subflows - <ol><li>The system brings the Player into a game lobby</li><li>The system displays options of inviting and starting a game (Once the other player is in the game lobby)</ol></li></ul> |
| Postconditions | <ul><li>The match is created</li><li>The Player is in a game lobby</li><li>The Player has the options of inviting and starting the match (Once the other player is in the game lobby)</li></ul> |
| Cross References | 

### <a name="play-game">Play game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-012 |
| Use Case Name | Play game |
| Overview | Allows a Player to play a game |
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
| Use Case Name | Make Move |
| Overview | On a player’s turn they make a move. The player can only make a move when it is their turn. |
| Type | Primary|
| Actors | Player [primary, initiator] |
| Properties | <ul><li>Performance – N/A</li><li>Security – N/A</li><li>Other – N/A</li></ul> |
| Preconditions | <ul><li>Player must be logged in</li><li>The game must be started </li><li>It must be the Player’s turn</li> |
| Flow | <ul><li>Main Flow<ol><li>Player is notified that it’s their turn</li><li>Player makes a move </li><li>Player’s turn ends</li></ol></li><li>Subflows<ol><li>System checks for valid move</li><li>System checks for check or checkmate</li></ol></li> <li>Alternate Flows<ol><li>Player makes invalid move</li><li>Checkmate occurred, game is over. Extend: End Game</li></ol></li></ul> |
| Postconditions | It is the other Player’s turn |
| Cross References | [End game](#end-game) |

### <a name="View-Profile">View Profile</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-027 |
| Use Case Name | View Profile |
| Overview | A Player can view profiles, either their own or another player’s |
| Type | Primary |
| Actors | <ul><li>Player [Primary, initiator]</li></ol> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | <ul><li>Player is logged in</li></ul> |
| Flow | <ul><li>Main Flow<ol><li>Player views a profile </li></ol></li><li> Subflows - <ul><li>System displays the public profile information like nickname, wins, losses, etc.</li></ul></li><li>Alternate Flows - <ul><li>Profile that player desires to view does not exist</li></ul></li></ul> |
| Postconditions | N/A |
| Cross References | N/A |

### <a name="end-game">End game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-016 |
| Use Case Name | End Game |
| Overview | Checkmate, stalemate, or player quit occurred, triggering the end of the game. The win-loss record for both players will be updated and the players will be notified that the game is over |
| Type | Primary |
| Actors | <ul><li>Player 1 [Primary, initiator]</li><li>Player 2 [Primary]</li></ol> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | <ul><li>Game is being played</li><li>Checkmate, stalemate or player quit occurred</li></ul> |
| Flow | <ul><li>Main Flow<ol><li>Notify players that the game has completed</li><li>Update player stats on player profile</li></ol></li><li>Subflows</li><ul><li>System ensures that neither player can access the game any longer</li></ul></ul> |
| Postconditions | Game is over |
| Cross References | N/A |

### <a name="Quit-game">Quit game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-023 |
| Use Case Name | Quit game |
| Overview | A player can quit the game at any time |
| Type | Primary |
| Actors | <ul><li>Player 1 [Primary, initiator]</li><li>Player 2 [Primary]</li></ol> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | <ul><li>Player is logged in</li><li>Game is being played</li></ul> |
| Flow | <ul><li>Main Flow<ol><li>Player leaves the game</li><li>Include: End Game</li></ol></li></ul> |
| Postconditions | Game is over |
| Cross References | [End game](#end-game) |

### <a name="join-game">Join game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-011 |
| Use Case Name | Join Game |
| Overview | Player joins a game |
| Type | Primary |
| Actors | <ul><li>Player [Primary, initiator]</li></ul> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | <ul><li>Player must be logged in</li><li>Game must exist. Include: Create Game</li><li>Player must have active invitation to enter game</li></ul> |
| Flow | <ul><li>Main Flow<ol><li>Player joins game</li></ol></li><li>Subflows</li><ul><li>All other invitations associated with this game are void</li><li>Game locks so other players cannot enter</li></ul></ul> |
| Postconditions | Game is ready to start |
| Cross References | [Create Game](#create-game) |

### <a name="play-game">Play game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-012 |
| Use Case Name | Play Game |
| Overview | Player plays the game |
| Type | Primary |
| Actors | <ul><li>Player [Primary, initiator]</li></ul> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | <ul><li>Player must be logged in</li><li>Player must have joined an existing game</li></ul> |
| Flow | <ul><li>Main Flow<ol><li>Player participates in the game</li></ol></li><li>Subflows</li><ul><li>Play cam make moves during his turn. Include: Make Move</li><li>Player can quit game. Include: Quit Game</li></ul></ul> |
| Postconditions | N/A |
| Cross References | <ul><li>[Quit game](#Quit-game)</li><li>[Make Move](#make-move)</li></ul> |

### <a name="log-in">Log in</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-035 |
| Use Case Name |Log in |
| Overview | Player logs in |
| Type | Primary |
| Actors | <ul><li>Player [Primary, initiator]</li></ul> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | <ul><li>Player must be registered. Include: Register User</li></ul> |
| Flow | <ul><li>Main Flow<ol><li>Player enters their username</li><li>Player enters their password</li></ol></li><li>Subflows</li><ul><li>Systems checks is username field is empty</li><li>System checks is password field is empty</li></ul><li>Alternate Flows<ol><li>User enters invalid username/password combination</li></ol></li></ul> |
| Postconditions | Player logs in |
| Cross References | <ul><li>[Register User](#register-user)</li></ul> |
