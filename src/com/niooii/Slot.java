package com.niooii;

public class Slot {
    // encapsulation too much work
    // players: 0, 1
    public final int row, col;
    private boolean state;
    private int player;
    public Slot(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = false;
    }

    public void setState(boolean state){
        this.state = state;
    }

    public boolean getState(){
        return this.state;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public boolean isValidAdjacent(Slot slot){
        return this.state && slot.getState() && this.player == slot.getPlayer();
    }
}
