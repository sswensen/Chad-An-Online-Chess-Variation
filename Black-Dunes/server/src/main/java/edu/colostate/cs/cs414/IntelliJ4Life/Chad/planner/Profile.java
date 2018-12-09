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
    /**
     * Gets the wins for the player
     *
     * @return - integer for the wins of the player
     */
    public int getWins() {
        return wins;
    }

    /**
     * Gets the losses for the player
     *
     * @return - integer for the losses of the player
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Gets the draws for the player
     *
     * @return - integer for the draws of the player
     */
    public int getDraws() {
        return draws;
    }

    /*************
     * Public Methods
     *************/
    /**
     * increments the win count for the player
     */
    public void addWin() {
        this.wins++;
    }

    /**
     * increments the loss count for the player
     */
    public void addLoss() {
        this.losses++;
    }

    /**
     * increments the draw count for the player
     */
    public void addDraw() {
        this.draws++;
    }
}
