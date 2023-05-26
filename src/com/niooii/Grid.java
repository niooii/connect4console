package com.niooii;

import java.io.PrintWriter;

public class Grid {
    //◉⬤
    public Slot[][] grid;
    private int winner = -1;
    private int winningNum;
    PrintWriter printWriter = new PrintWriter(System.out,true);
    public static final char playerZeroChar = 'X';//◌;
    public static final char playerOneChar = 'O';//●;

    public Grid(int rows, int cols, int winningNum){
        this.winningNum = winningNum;
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
                    printWriter.print("| ");
                if(grid[i][j].getState()){
                    if(grid[i][j].getPlayer() == 0)
                        x = playerZeroChar;//◌
                    else
                        x = playerOneChar;//●
                }
                else
                    x = ' ';
                printWriter.print(x + " | ");
            }
            printWriter.println();
        }
        StringBuilder bottomline = new StringBuilder();
        for(int i = 0; i < grid[0].length * 4 + 1; i++)
            bottomline.append('-');
        printWriter.println(bottomline);
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
                if(count == winningNum) {
                    winner = grid[row][i].getPlayer();
                    System.out.println("win at row " + row + ", col " + i + ".");
                    System.out.println("won by: horizontal");
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
                if(count == winningNum) {
                    winner = grid[i][col].getPlayer();
                    System.out.println("win at row " + i + ", col " + col + ".");
                    System.out.println("won by: vertical");
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
        for(int k = 0; k < winningNum; k++){
            if(++col < grid[0].length && --row > -1){
                if(grid[row+1][col-1].isValidAdjacent(grid[row][col])) {
                    count++;
                    if (count == winningNum) {
                        winner = grid[row][col].getPlayer();
                        System.out.println("win at row " + row + ", col " + col + ".");
                        System.out.println("won by: right diagonal");
                        return true;
                    }
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    // implement checkAllLeftDiagonal()
    public boolean checkAllRightDiagonal() {
        for(int i = grid.length-1; i >= winningNum - 1; i--){
            for(int j = 0; j <= grid[0].length - winningNum; j++){
                if(isFourRightDiagonal(i, j))
                    return true;
            }
        }
        return false;
    }

    public boolean isFourLeftDiagonal(int row, int col){
        int count = 1;
        for(int k = 0; k < winningNum; k++){
            if(--col > -1 && --row > -1 && grid[row+1][col+1].isValidAdjacent(grid[row][col])){
                count++;
                if(count == winningNum) {
                    winner = grid[row][col].getPlayer();
                    System.out.println("win at row " + row + ", col " + col + ".");
                    System.out.println("won by: left diagonal");
                    return true;
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    // implement checkAllLeftDiagonal()
    public boolean checkAllLeftDiagonal() {
        for(int i = grid.length-1; i >= winningNum - 1; i--){
            for(int j = winningNum - 1; j <= grid[0].length - 1; j++){
                if(isFourLeftDiagonal(i, j))
                    return true;
            }
        }
        return false;
    }

    public int updAndGetWinner(){
        checkAllHoz();
        checkAllVertical();
        checkAllRightDiagonal();
        checkAllLeftDiagonal();
        return winner;
    }

    public int getWinner(){
        return winner;
    }
}
