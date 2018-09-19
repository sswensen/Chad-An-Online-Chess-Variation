package com.tripco.t00.server;

/** The main class for the application.
 *
 * Command line arguments are of the form:
 * [port] [teaminfo]...
 *
 * Examples that produce the same result:
 * 31400 t00 Double Aughts
 * 31400 "t00 Double Aughts"
 */
public class MyServer {

  /** Main program starts a web microserver on the specified network port
   ** @param args command line arguments optionally containing port and team name.
   */
  public static void main(String[] args) {

    MicroServer server = new MicroServer(getPort(args), getName(args));

  }

  /** Obtain the port number from the command line arguments.  Defaults if none provided.
   * @param args
   * @return
   */
  private static int getPort(String[] args) {

    if (args.length > 0)
      return Integer.parseInt(args[0]);
    else
      return 8088; // some default

  }

  /** Obtain the name from the command line arguments.  Defaults if not specified.
   * @param args
   * @return a concatenation of the arguments after the port
   */
  private static String getName(String[] args) {

    if (args.length > 1) {
      String name = args[1];
      for (int i = 2; i < args.length; i++)
        name = name + " " + args[i];
      return name;
    }
    else
      return "Unknown";
  }

}