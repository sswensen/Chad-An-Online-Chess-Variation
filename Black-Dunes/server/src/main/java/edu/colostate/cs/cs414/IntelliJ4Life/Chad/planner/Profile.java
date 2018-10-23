package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

public class Profile {
    private int wins, losses, draws;

    public Profile() {
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    /************
     * Accessors
     ***********/
    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    /*************
     * Public Methods
     *************/
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
