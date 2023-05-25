package com.niooii;

public class Grid {
    //◉⬤
    public Slot[][] grid;
    private int winner = -1;

    public Grid(int rows, int cols){
        grid = new Slot[rows][cols];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Slot(i, j);
    }
    public boolean placeThingy(int player, int col) throws Exception {
        boolean successful = false;
        col -= 1;
        if(col >= grid[0].length || col < 0){
            System.out.println("you are actually mentally ill thanks for playing");
            throw new YouTried("Segmentation fault (core dumped)");
        }
        if(columnIsFull(col))
            return successful;
        // loop through possible spots from the bottom up
        for(int i = grid.length - 1; i >= 0; i--){
            if(!grid[i][col].getState()){
                grid[i][col].setState(true);
                grid[i][col].setPlayer(player);
                successful = true;
                break;
            }
        }
        return successful;
    }
    public boolean columnIsFull(int col) {
        return grid[0][col].getState();
    }
    public void printGrid(){
        char x;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(j == 0)
                    System.out.print("| ");
                if(grid[i][j].getState()){
                    if(grid[i][j].getPlayer() == 0)
                        x = '◌';
                    else
                        x = '●';
                }
                else
                    x = ' ';
                System.out.print(x + " | ");
            }
            System.out.println();
        }
    }

    public boolean isFull(){
        for(Slot[] x : grid){
            for(Slot y : x){
                if(!y.getState())
                    return false;
            }
        }
        return true;
    }

    public boolean checkAllHoz() {
        for(int i = 0; i < grid.length; i++){
            if(isFourHorizontal(i))
                return true;
        }
        return false;
    }

    public boolean isFourHorizontal(int row) {
        int count = 1;
        for(int i = 0; i < grid[0].length - 1; i++){
            if(grid[row][i].isValidAdjacent(grid[row][i+1])){
                count++;
                if(count == 4) {
                    winner = grid[row][i].getPlayer();
                    return true;
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    public boolean checkAllVertical() {
        for(int i = 0; i < grid[0].length; i++){
            if(isFourVertical(i))
                return true;
        }
        return false;
    }

    public boolean isFourVertical(int col) {
        int count = 1;
        for(int i = 0; i < grid.length - 1; i++){
            if(grid[i][col].isValidAdjacent(grid[i+1][col])){
                count++;
                if(count == 4) {
                    winner = grid[i][col].getPlayer();
                    return true;
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    public boolean isFourRightDiagonal(int row, int col){
        int count = 1;
        for(int k = 0; k < 4; k++){
            if(++col != grid[0].length && --row != -1 && grid[row+1][col-1].isValidAdjacent(grid[row][col])){
                count++;
                if(count == 4) {
                    winner = grid[row][col].getPlayer();
                    return true;
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    // implement checkAllLeftDiagonal()
    public boolean checkAllRightDiagonal() {
        int count = 1;
        for(int i = grid.length-1; i >= 3; i--){
            for(int j = 0; j <= grid[0].length - 4; j++){
                if(isFourRightDiagonal(i, j))
                    return true;
            }
        }
        return false;
    }

    public int updAndGetWinner(){
        checkAllHoz();
        checkAllVertical();
        checkAllRightDiagonal();
        return winner;
    }
}
