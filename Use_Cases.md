# Fully Dressed Use Cases
## Table of Contents
1. [Switch Game](#switch-game)
2. [Register User](#register-user)
3. [Unregister User](#unregister-user)
4. [Reject Invitation](#reject-invitation)
5. [Create Game](#create-game)
6. [Make Move](#make-move)
7. [View Profile](#View-Profile)
8. [End Game](#end-game)
9. [Quit Game](#Quit-game)
10. [Join Game](#join-game)
11. [Play Game](#play-game)
12. [Log In](#log-in)
13. [Cancel Invitation](#cancel-invitation)
14. [Accept Invitation](#accept-invitation)
15. [Invitation Interaction](#invitation-interaction)
16. [Invite Players](#invite-players)


## Use Cases

### <a name="switch-game">Switch Game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-026 |
| Use Case Name | Switch Game |
| Overview | Allows player to switch from one game to another |
| Type | Primary |
| Actors | Primary Actor: Player |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Performance - N/A</li></ul> |
| Preconditions | Player is already playing one game and has another game they can play |
| Flow | <ul><li>Main Flow<ol><li>Suspend playing of current game</li><li>Starts playing another game. Include: Play Game</li></ol></li></ul> |
| Postconditions | Player is now playing another game |
| Cross References | [Play Game](#play-game) |

### <a name="register-user">Register User</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-002 |
| Use Case Name | Register User |
| Overview | A user registers themselves with the application. This will consist of capturing an email, nickname, and password. Associating all users with unique accounts allows us to save which users are in an active game. This will make it possible for a user to enter the game, make a move, and exit (possibly into another game). |
| Type | Primary |
| Actors | End User [primary, initiator] |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
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
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | User should already exist. |
| Flow | <ul><li>Main Flow<ol><li>User deletes account</li></ol></li><li>Subflows<ol><li>System deletes email</li><li>System deletes nickname</li><li>System deletes password</li></ol></li><li>Alternate Flows<ol><li>User is not in system</li></ol></li></ul> |
| Postconditions | Account is deleted (i.e. email, nickname, and password (hashed) are removed in the database) |
| Cross References | N/A|

 ### <a name="reject-invitation">Reject Invitation</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-007 |
| Use Case Name | Reject Invitation |
| Overview | If a user receives an invitation, the user can reject that invitation. The user who sent the invitation will receive a notification that their invitation was rejected. |
| Type | Primary |
| Actors | <ul><li>User 1 [primary, initiator]</li><li>User 2 [primary]</li> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | User 2 has sent User 1 an invitation to a game |
| Flow | Main Flow<ol><li>User 1 rejects invitation from User 2</li><li>User 1 receives a notification that User 2 has rejected the invitation</li></ol>|
| Postconditions | <ul><li>User 1's invitation will be gone</li><li>User 2 will have a rejection notification</li></ul> |
| Cross References | N/A |
 
### <a name="create-game">Create Game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-004 |
| Use Case Name | Create Game |
| Overview | Allows a Player to make a new game |
| Type | Primary |
| Actors | Primary Actor: Player |
| Properties | <ul><li>Performance - </li><li>Security - </li><li>Other - </li></ul> |
| Preconditions | <ul><li>A Player is logged-in</li></ul>  |
| Flow | <ul><li>Main Flow - <ol><li>A Player selects the option to create a new match</li><li>The system creates a new match</li></ol></li><li>Subflows - <ol><li>The system brings the Player into the game</li><li>The system displays options of inviting players. Include: Invite Players</ol></li></ul> |
| Postconditions | <ul><li>The match is created</li><li>The Player is in the game waiting for another player to join</li></ul> |
| Cross References | [Invite Players](#invite-players) |

### <a name="play-game">Play Game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-012 |
| Use Case Name | Play Game |
| Overview | Player can interact with the game |
| Type | Primary |
| Actors | Primary Actor: Player |
| Properties | <ul><li>Performance - </li><li>Security - </li><li>Other - </li></ul> |
| Preconditions | <ul><li>Player is in a started game</li></ul>  |
| Flow | <ul><li>Main Flow - <ol><li>Player can make a move if it is their turn. Include: Make Move</li><li>Player can quit game at any time. Include: Quit Game</li><li>Player can switch to another game. Include: Switch Game</li></ol></li></ul> |
| Postconditions | N/A |
| Cross References | <ul><li>[Make Move](#make-move)</li><li>[Quit Game](#quit-game)</li><li>[Switch Game](#switch-game)</li></ul> | 

### <a name="make-move">Make Move</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-015 |
| Use Case Name | Make Move |
| Overview | On a player’s turn they make a move. The player can only make a move when it is their turn. |
| Type | Primary|
| Actors | Player [primary, initiator] |
| Properties | <ul><li>Performance – N/A</li><li>Security – N/A</li><li>Other – N/A</li></ul> |
| Preconditions | It is the Player’s turn |
| Flow | <ul><li>Main Flow<ol><li>Player is notified that it’s their turn</li><li>Player makes a move </li><li>Player’s turn ends</li></ol></li><li>Subflows<ol><li>System checks for valid move</li><li>System checks for check or checkmate</li></ol></li> <li>Alternate Flows<ol><li>Player makes invalid move</li><li>Checkmate occurred, game is over. Extend: End Game</li></ol></li></ul> |
| Postconditions | It is the other Player’s turn |
| Cross References | [End Game](#end-game) |

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

### <a name="end-game">End Game</a>
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

### <a name="Quit-game">Quit Game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-023 |
| Use Case Name | Quit Game |
| Overview | A player can quit the game at any time |
| Type | Primary |
| Actors | <ul><li>Player 1 [Primary, initiator]</li><li>Player 2 [Primary]</li></ol> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | (Inherited from [Play Game](#play-game)) |
| Flow | <ul><li>Main Flow<ol><li>Player leaves the game</li><li>Include: End Game</li></ol></li></ul> |
| Postconditions | Game is over |
| Cross References | [End game](#end-game) |

### <a name="join-game">Join Game</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-011 |
| Use Case Name | Join Game |
| Overview | Player joins a game |
| Type | Primary |
| Actors | <ul><li>Player [Primary, initiator]</li></ul> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | <ul><li>Player must be logged in</li><li>Game must exist. Extend: Create Game</li><li>The game must not be started</li></ul> |
| Flow | <ul><li>Main Flow<ol><li>Player joins game</li></ol></li></ul> |
| Postconditions | Game starts if two players have joined. Extend: Play Game |
| Cross References |<ul><li>[Play Game](#play-game)</li><li>[Create Game](#create-game)</li></ul> |

### <a name="log-in">Log In</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-035 |
| Use Case Name |Log In |
| Overview | Player logs in |
| Type | Primary |
| Actors | <ul><li>Player [Primary, initiator]</li></ul> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | <ul><li>Player must be registered. Include: Register User</li></ul> |
| Flow | <ul><li>Main Flow<ol><li>Player enters their username</li><li>Player enters their password</li></ol></li><li>Subflows</li><ul><li>Systems checks is username field is empty</li><li>System checks is password field is empty</li></ul><li>Alternate Flows<ol><li>User enters invalid username/password combination</li></ol></li></ul> |
| Postconditions | Player logs in |
| Cross References | <ul><li>[Register User](#register-user)</li></ul> |

 ### <a name="cancel-invitation">Cancel Invitation</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-038 |
| Use Case Name | Cancel Invitation |
| Overview | If a user sends an invitation, the user can cancel that invitation. The invitation will show as cancelled. |
| Type | Primary |
| Actors | <ul><li>User 1 [primary, initiator]</li><li>End User 2 [primary]</li> |
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | User 1 has sent User 2 an invitation to a game |
| Flow | <ul><li>Main Flow<ol><li>User 1 cancels invitation sent to User 2</li></ol></li></ul> |
| Postconditions | User 2's invitation will show as cancelled.|
| Cross References | N/A |
 
  ### <a name="accept-invitation">Accept Invitation</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-037 |
| Use Case Name | Accept Invitation |
| Overview | User accepts an invitation to join a game. |
| Type | Primary |
| Actors | User [primary, initiator]|
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | User has received an invitation to join a game |
| Flow | <ul><li>Main Flow<ol><li>User accepts invitation to play a game</li></ol></li><li>Subflows<ol><li>System checks if User can join the game.<ol><li>If so, User joins the game. Include: Join Game</li></ol></li></ol></li><li>Alternate Flows<ol><li>User cannot join the game since the game has started.</li></ol></li></ul> |
| Postconditions | <ul><li>User will join the game</li><li>The game will start</li><li>User's invitation will be deleted</li></ul>|
| Cross References | [Join Game](#join-game) |

  ### <a name="invitation-interaction">Invitation Interaction</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-039 |
| Use Case Name | Invitation Interaction |
| Overview | User interacts with any available invitations. |
| Type | Primary |
| Actors | User [primary, initiator]|
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | User has active invitations |
| Flow | <ul><li>Main Flow<ol><li>User chooses to interact with active invitations</li><li>Choices:<ol><li>Reject Invitation</li><li>Accept Invitation</li><li>Cancel Invitation</li></ol></li></ol></li><li>Subflows<ol><li>System checks if User has invitation notifications.<ol><li>If so, the notifications are cleared</li></ol></li></ol></li></ul> |
| Postconditions | User's invitation notifications are cleared. |
| Cross References | <ul><li>[Reject Invitation](#reject-invitation)</li><li>[Accept Invitation](#accept-invitation)</li><li>[Cancel Invitation](#cancel-invitation)</li></ul> |

  ### <a name="invite-players">Invite Players</a>
| Section | Description |
| ------- | ----------- |
| Use Case Id | EU-039 |
| Use Case Name | Invite Players |
| Overview | Player invites any other Users to join the newly created game. |
| Type | Primary |
| Actors | Player [primary, initiator]|
| Properties | <ul><li>Performance - N/A</li><li>Security - N/A</li><li>Other - N/A</li></ul> |
| Preconditions | No other Player has joined the game |
| Flow | <ul><li>Main Flow<ol><li>Player invites other players to the game</li></ol></li><li>Subflows<ol><li>System sends out invitations to all Users chosen to invite.<li>System notifies Users of new invitation.</li></ol></li></ul> |
| Postconditions | <ul><li>Users have new invitations to join game</li><li>Users have new notifications about invitation</li></ul> |
| Cross References | N/A |
