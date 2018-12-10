# DevOps Manual

## Running the servers (standalone)

### Run Server:
* Use your favorite IDE.
* Navagate to and open the Server.java file (`Black-Dunes/server/src/main/java/edu/colostate/cs/cs414/IntelliJ4Life/Chad/server/`).
* Run main within Server.java
* Now the server is running.
* Note: You can specify what port you want your server to run on by adding it to `run configuration -> program arguments`.

### Run Client:
* Navagate to the the client folder in terminal (Black-Dunes/client).
* Have npm installed on your machine. Link: https://www.npmjs.com/
* In your terminal run `npm install`
* Next, in your terminal run `npm run dev`
* Now the front end should be running and open in your browser window.
* Note: If errors are encountered when running `npm run dev`, try deleting your `node_modules` folder and doing an `npm install again`.

### To run tests

Use `mvn test` to run all the unit tests for the system

<hr>

## An easier way
You can also use the `run` script located in the `Black-Dunes` root to do all of the above (including running tests) in one simple step.

You can run the script by itself to run on the default port or you can specify your own by adding a port number as a script argument. This is the port the webserver will listen for incoming http connections.

Examples:

```bash
./run      # Puts the frontend and backend server up using defaults
```

```bash
./run 7777 # Puts the servers up on port 7777 (localhost:7777)
```

You can then navigate to our site by opening a web browser (such as Google Chrome) and navigating to `localhost:xxxx`.
Note: `xxxx` denotes the port the server is listening on.
