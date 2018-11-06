package edu.colostate.cs.cs414.IntelliJ4Life.Chad.server;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class Config {

  private short version = 1;
  private String type = "config";

  // We can convert this to grab a list of users when sending names for invites
  private List<String> units = Arrays.asList("miles", "kilos");

  static String getConfig() {
    Config conf = new Config();
    Gson gson = new Gson();

    return gson.toJson(conf);
  }
}
