# DevOps Manual

### To run

Type `mvn package` while at the `Black-Dunes` root to package all backend code into a jar.

Next, run `java -jar target/server-*.jar` from the `Black-Dunes` root to start the jar. Now, you should see the login/register prompt. Follow onscreen instructions to play the game. 

Note: Selecting the Login option will login with a hardcoded player. Selecting the Register option will allow the user to register a new account with the database, however, this new account will not be able to start new games (not implemented yet).
