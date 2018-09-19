package com.tripco.t00.server;

/** A trivial example.
 */
public class Greeting {

  /**
   * Generates an HTML response using a parameter passed as an argument.
   * @param name is a string to include in the HTML response.
   * @return the html string to return to a browser for rendering.
   */
  public static String html(String name) {

    return "<html><head></head><body><h1>Hello "+name+"!</h1></body></html>";

  }
}