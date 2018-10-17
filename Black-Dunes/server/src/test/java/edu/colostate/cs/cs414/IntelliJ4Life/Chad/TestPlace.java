package edu.colostate.cs.cs414.IntelliJ4Life.Chad;

import edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner.Place;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/*
  This class contains tests for the Trip class.
 */
@RunWith(JUnit4.class)
public class TestPlace {
  Place place;

  // Setup to be done before every test in TestPlan
  @Before
  public void initialize() {
    place = new Place();
    place.id = "dnvr";
    place.name="Denver";
  }

  @Test 
  public void testPlace() {
    String id = "dnvr";
    String name = "Denver";
    assertEquals(place.id, id);
    assertEquals(place.name,name);
  }

}
