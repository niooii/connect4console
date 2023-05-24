package com.niooii;
import com.niooii.*;

import java.util.Scanner;

public class Game {
    final int rows = 6;
    final int cols = 7;
    Scanner sc = new Scanner(System.in);
    // PLAYERS: 0, 1
    Grid grid = new Grid(rows, cols);
    public Game() throws Exception {
//          simulationCounts(100);
        grid.printGrid();
        System.out.println();
        int player = 0;
        while(true){
            if(grid.checkAllHoz() || grid.checkAllVertical() || grid.checkAllRightDiagonal()){
                System.out.println("someone won idk");
                System.exit(0);
            }
            System.out.print("Enter a column: ");
            while(!grid.placeThingy(player, sc.nextInt())){
                System.out.print("Enter a valid column: ");
            }
            grid.printGrid();
            System.out.println();
            if(player == 0)
                player = 1;
            else
                player = 0;
        }
    }

    // return val of 0 = someone wins, 1 = tie;
    int simulateGame(boolean printOutput) throws Exception {
        if(printOutput){
            grid.printGrid();
            System.out.println();
        }
        int player = 0;
        while(true){
            if(grid.checkAllRightDiagonal()){
                if(printOutput){
                    System.out.println("someone won idk");
                }
                return 0;
            }
            if(grid.isFull()){
                if(printOutput){
                    System.out.println("tied");
                }
                return 1;
            }
            int col = (int)(Math.random() * cols) + 1;
            if(printOutput)
                System.out.println("Player " + player + ", column " + col + "\n");
            while(!grid.placeThingy(player, col)){
                col = (int)(Math.random() * cols) + 1;
                if(printOutput){
                    System.out.println("regenerating column...");
                    System.out.println("new column: " + col);
                }
            }
            if(printOutput){
                grid.printGrid();
                System.out.println();
            }
            if(player == 0)
                player = 1;
            else
                player = 0;
        }
    }

    void simulationCounts(int gameCount) throws Exception {
        int tieCount = 0;
        int winCount = 0;
        for(int i = 0; i < gameCount; i++){
            int result = simulateGame(false);
            if(result == 0){
                winCount++;
            } else
                tieCount++;
        }
        System.out.println("TIES: " + tieCount);
        System.out.println("WINS " + winCount);
    }
}
