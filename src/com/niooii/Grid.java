package com.niooii;

import java.io.PrintWriter;

public class Grid {
    //◉⬤
    public Slot[][] grid;
    private int winner = -1;
    private final int winningNum;
    private int lastMoveColIndex = -1;
    private int colAboutToWinHoz = -1;
    private int colAboutToWinVert = -1;
    private boolean setToggle = false;
    private final String[] insults = { // was about to make the computer throw insults at you, but am too lazy.
            "",
    };
    PrintWriter printWriter = new PrintWriter(System.out,true);
    public static char playerZeroChar = 'X';//◌;
    public static char playerOneChar = 'O';//●;

    public Grid(int rows, int cols, int winningNum){
        this.winningNum = winningNum;
        grid = new Slot[rows][cols];
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Slot(i, j);
    }
    public boolean placeThingy(int player, int col) {
        col -= 1;
        if(col >= grid[0].length || col < 0){
            return false;
        }
        if(columnIsFull(col))
            return false;
        int slot = nextFreeSlot(col);
        // loop through possible spots from the bottom up
        if(slot != -1){
            grid[slot][col].setState(true);
            grid[slot][col].setPlayer(player);
            lastMoveColIndex = col;
            return true;
        }
        return false;
    }
    public boolean isValidMove(int col){
        return col > 0 && col <= grid[0].length;
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

    public int nextFreeSlot(int col){
        for(int i = grid.length - 1; i >= 0; i--){
            if(!grid[i][col].getState()){
                return i;
            }
        }
        return -1;
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

    public void checkAllHoz() {
        for(int i = 0; i < grid.length; i++){
            if(isFourHorizontal(i))
                return;
        }
    }

    public boolean isFourHorizontal(int row) {
        int count = 1;
        for(int i = 0; i < grid[0].length - 1; i++){
            if(grid[row][i].isValidAdjacent(grid[row][i+1])){
                count++;
                if(count == winningNum) {
                    winner = grid[row][i].getPlayer();
                    System.out.println("win at row " + (grid.length - row) + ", col " + (i + 1) + ".");
                    System.out.println("won by: horizontal");
                    return true;
                } else if(isValidMove(i+3)) {
                    if(count == winningNum - 1 && !grid[row][i+2].getState() && nextFreeSlot(i+2) == row) {
                        if(setToggle){
                            setToggle = false;
                            colAboutToWinHoz = i + 2;
//                            System.out.println("[by horizontal] col about to win: " + colAboutToWinHoz);
                        }
                    } else {
                        if(setToggle){
                            colAboutToWinHoz = -1;
                        }
                    }
                } else {
                    if(setToggle){
                        colAboutToWinHoz = -1;
                    }
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    public void checkAllVertical() {
        for(int i = 0; i < grid[0].length; i++){
            if(isFourVertical(i))
                return;
        }
    }

    public boolean isFourVertical(int col) {
        int count = 1;
        for(int i = grid.length - 1; i > 0; i--){
            if(grid[i][col].isValidAdjacent(grid[i-1][col])){
                count++;
                if(count == winningNum) {
                    winner = grid[i][col].getPlayer();
                    System.out.println("win at row " + (i + 1) + ", col " + (col + 1) + ".");
                    System.out.println("won by: vertical");
                    return true;
                } else if(i-(winningNum - 2) >= 0){
                    if(count == winningNum - 1 && !grid[i-2][col].getState() && nextFreeSlot(col) == i-2){
                        if(setToggle){
                            setToggle = false;
                            colAboutToWinVert = col;
//                            System.out.println("[by vertical] col about to win: " + colAboutToWinVert);
                        }
                    } else {
                        if(setToggle){
                            colAboutToWinVert = -1;
                        }
                    }
                } else {
                    if(setToggle){
                        colAboutToWinVert = -1;
                    }
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
            if(++col < grid[0].length && --row > -1 && grid[row+1][col-1].isValidAdjacent(grid[row][col])){
                count++;
                if (count == winningNum) {
                    winner = grid[row][col].getPlayer();
                    System.out.println("win at row " + (grid.length - row) + ", col " + (col + 1) + ".");
                    System.out.println("won by: right diagonal");
                    return true;
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    public void checkAllRightDiagonal() {
        for(int i = grid.length-1; i >= winningNum - 1; i--){
            for(int j = 0; j <= grid[0].length - winningNum; j++){
                if(isFourRightDiagonal(i, j))
                    return;
            }
        }
    }

    public boolean isFourLeftDiagonal(int row, int col){
        int count = 1;
        for(int k = 0; k < winningNum; k++){
            if(--col > -1 && --row > -1 && grid[row+1][col+1].isValidAdjacent(grid[row][col])){
                count++;
                if(count == winningNum) {
                    winner = grid[row][col].getPlayer();
                    System.out.println("win at row " + (grid.length - row) + ", col " + (col + 1) + ".");
                    System.out.println("won by: left diagonal");
//
                    return true;
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    public void checkAllLeftDiagonal() {
        for(int i = grid.length-1; i >= winningNum - 1; i--){
            for(int j = winningNum - 1; j <= grid[0].length - 1; j++){
                if(isFourLeftDiagonal(i, j))
                    return;
            }
        }
    }

    public int updAndGetWinner(){
        setToggle = true;
        checkAllHoz();
        checkAllVertical();
        checkAllRightDiagonal();
        checkAllLeftDiagonal();
        return winner;
    }

    public int getRandomFreeCol(){
        if(isFull())
            return -1;
        int col = (int)(Math.random() * grid[0].length) + 1;
        while(columnIsFull(col-1)){
            col = (int)(Math.random() * grid[0].length) + 1;
        }
        return col;
    }

    public int getComputerMove(){// how do i implement minimax :(
         int col = lastMoveColIndex;
         if(colAboutToWinHoz != -1){
             return colAboutToWinHoz + 1;
         } else if(colAboutToWinVert != -1){
             return colAboutToWinVert + 1;
         }
         if(col == 0){
             System.out.println("its equal 0. why???");
         }
         int range;
         if(col == 0){
             if(columnIsFull(col+1)){
                 return getRandomFreeCol();
             } else {
                 if(Math.random() > 0.5)
                     return col + 2;
                 else
                     return getRandomFreeCol();
             }
         } else if(col == grid[0].length - 1){
            if(columnIsFull(col-1)){
                return getRandomFreeCol();
            } else {
                if(Math.random() > 0.5)
                    return col;
                else
                    return getRandomFreeCol();
            }
         }
         if(col > 0 && col < grid[0].length - 1) { // if col has one col to the left & right
             if(columnIsFull(col) && columnIsFull(col-1) && columnIsFull(col+1)){
                 return getRandomFreeCol();
             } else {
                 range = (int)(Math.random() * 3) - 1;
                 while(columnIsFull(col+range)){
                     range = (int)(Math.random() * 3) - 1;
                 }
                 col = col+range;
             }
         }
         return col + 1;
    }
}
