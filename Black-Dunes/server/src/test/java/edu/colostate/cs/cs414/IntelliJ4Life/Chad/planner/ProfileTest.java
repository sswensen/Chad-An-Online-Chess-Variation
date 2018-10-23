package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    private Profile testProfile;

    @BeforeEach
    void setUp() {
        testProfile = new Profile();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testProfile.getWins());
        assertEquals(0, testProfile.getLosses());
        assertEquals(0, testProfile.getDraws());
    }

    @Test
    void addWin() {
        testProfile.addWin();
        assertEquals(1, testProfile.getWins());
    }

    @Test
    void addLoss() {
        testProfile.addLoss();
        assertEquals(1, testProfile.getLosses());
    }

    @Test
    void addDraw() {
        testProfile.addDraw();
        assertEquals(1, testProfile.getDraws());
    }

}