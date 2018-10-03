### User
* username - User's username
* email - User's email
* hashedPassword - Hash generated from hashing password + salt
* salt - Random number used to secure password

### Player
* isTurn - Is it the player's turn?

### Board
* spaces - a 2d array which a Piece can occupy

### Invite
* inviteStatus - Can be [open, accepted, rejected]
* inviteType - Can be [sent, recieved]

### Game
* gameDuration: Time - Elaped time of game from start of game
* rules - Text player can read to understand how game is played

### Profile
* wins - Count of a profile's game wins
* losses - Count of a profile's game losses
* draws - Count of a profile's game draws

### Piece
* position - Where the piece is on the board
* isTaken - If the piece is on the board or not
* color - What color the piece belongs to