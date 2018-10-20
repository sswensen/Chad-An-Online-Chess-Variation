package edu.colostate.cs.cs414.IntelliJ4Life.Chad.planner;

public abstract class Piece {
    enum Color {
        WHITE, BLACK;
    }
    
    private int[] position;
    private boolean isTaken;
    private Color color;

    public int[] getPosition() {
        return this.position;
    }
    
    public void setPosition(int[] position) {
        this.position = position;
    }

    public boolean isTaken() {
        return this.isTaken;
    }

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void initializePiece(Color color) {
        this.color = color;
        this.isTaken = false;
        this.position = new int[] {-1, -1};
    }

    abstract public int[][] validMoves();

    abstract public boolean isValid(int[] move);

    public void move(int[] move) {
        if (isValid(move)) {
            this.position = move;
            // @TODO: Update board with this piece
        }
        else {
            // @TODO: Handle invalid move
        }
    }

    public void take() {
        this.isTaken = true;
        int[] removedPosition = {-1, -1};
        this.position = removedPosition;
    }
}