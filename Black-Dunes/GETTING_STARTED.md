# React-Spark-Starter
## Requirements
You must have Maven, a build tool for Java, and npm, a dependency manager for Javascript development, installed. **The lab machines already have these tools installed.**
### Installing Maven:
First, check to see if you already have Maven installed:

    mvn -v
#### Mac installation
The easiest way to install Maven on Macs is to use the Homebrew package manager for OSX. Homebrew allows you to install programs from the command line, just like Linux. To install Homebrew:

    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
If the installation failed, ensure you meet the installation requirements [here](https://docs.brew.sh/Installation.html). The page also has alternative installation instructions if the above doesn't work. Once Homebrew is installed, install Maven with:

    brew update
    brew install maven
#### Linux installation
Maven should be available for install through the package manager of whatever distro you use. 
### Installing npm
First, check to see if npm is already installed:

    node -v
    npm -v # Both should output version information
npm is distributed with NodeJS. Installing Node will also install npm on your computer.
#### Mac installation
As with Maven, it is easiest to install npm using Homebrew:
    
    brew install node
#### Linux installation 
Find instructions for your particular distro [here](https://nodejs.org/en/download/package-manager/).
## Starting the production server
The easiest way to run the server and make sure everything works is to use the run script:
    
    ./run [port]
This will install all npm dependencies, bundle together all of the Javascript and Java code, and start running the server on the specified port. A default will be used if one is not specified. Note that if you change the default port, you'll need to change the `const port = 8088;` line near the top of `client/webpack.prod.config.js` before running the script.

## Loading in IntelliJ
IntelliJ is the IDE our company will be using. It has better support for the tools we will be using than Eclipse. 

### Creating an account
The Ultimate Edition of IntelliJ is installed on the lab machines. To use it, you'll need a free JetBrains student account. You can sign up for one [here](https://www.jetbrains.com/student/).

### Launching IntelliJ
IntelliJ is available on the lab machiens under the command:

    /usr/local/idea/bin/idea.sh
If you don't want to remember this, you can add an alias to `~/.bashrc`:
    
    alias intellij="/usr/local/idea/bin/idea.sh"
Then from the terminal type:

    source ~/.bashrc
Now you can simply type `intellij` into a terminal and it will launch. 

### Opening the project

When first opening IntelliJ, a window should open inviting you to open a project. Select "Check out from version control" and continue to select "GitHub" from the dropdown. 

Since this is likely your first time checking out a GitHub repository from IntelliJ,
you will need to create a GitHub API token. Click the "Create API Token" button on the right of the popup. Enter your GitHub credentials and hit confirm. API Tokens use RSA to collate your username and password into a single value and can be used in place of a password. 

Once authenticated, you'll be presented with a pull-down menu containing all GitHub 
projects you have access to.  Naturally, you will be selecting your repository at "csu314sp18/<i>t##</i>".  Unless you
want to specify a different storage folder, you can now hit "Clone".

Because we are using Maven, IntelliJ should automatically import all of the necessary dependencies for the project. This sometimes takes a while, so be patient.

### Building in IntelliJ
For the most part, the run script should be comprehensive enough to build your project. [Here are some visual instructions on opening a terminal in IntelliJ](https://www.jetbrains.com/help/idea/working-with-tool-windows.html#tool_window_quick_access). In the future we'll show you how Maven integrates with IntelliJ. 

### Home installation
There are two versions of IntelliJ, the Community Edition and the Ultimate Edition. Either should work for the scope of this project, but the Ultimate Edition integrates better with Javascript code. As mentioned above, students have free access to the Ultimate Edition and other Jetbrains products. Either edition can be downloaded [from the Jetbrains website](https://www.jetbrains.com/idea/download/#section=windows).

## Using the development server
The Webpack dev server allows you to make changes to your Javascript code without repackaging it. To use it:

    mvn package                             # Package Java code
    java -jar target/server-0.1.jar [port]  # Start the Java server on the specified port
    In another terminal:
    # Adjust the const port = 33000; line in client/webpack.dev.config.js to match the specified port
    npm --prefix client install             # Install npm dependencies (only necessary the first time and on dependency changes)
    npm --prefix client run test            # Start the test server. It should automatically start a web browser with your page
It's recommended you use a consistent port when starting the Java server, because you need to edit `client/webpack.dev.config.js` each time you change ports. 
