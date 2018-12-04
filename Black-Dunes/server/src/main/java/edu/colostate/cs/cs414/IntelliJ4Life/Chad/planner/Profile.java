package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

public class Profile {
    private int wins, losses, draws;

    /**
     * Object that represents a user's profile
     */
    public Profile() {
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    /**
     * Accessors that gets the wins, losses, and draws of a user
     * @return
     */
    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    /**
     * Setters that adds a win, loss, or draw to a user's profile
     */
    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.losses++;
    }

    public void addDraw() {
        this.draws++;
    }
}
