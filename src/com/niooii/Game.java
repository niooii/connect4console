package com.niooii;
import com.niooii.*;

import java.util.Scanner;
import com.niooii.GameType;
import com.niooii.onlinestuff.OnlinePlayer;

public class Game {
    final int rows = 6;
    final int cols = 7;
    Scanner sc = new Scanner(System.in);
    // PLAYERS: 0, 1
    Grid grid = new Grid(rows, cols);
    public Game(GameType type) throws Exception {
        switch(type){
            case LOCALMULTIPLAYER:
                LMMainLoop();
                break;
            case SIMULATION:
                simulateGame(true);
                break;
            case MULTISIMULATION:
                System.out.println("How many games?");
                simulationCounts(sc.nextInt());
                break;
            case ONLINEMULTIPLAYER:
                OMMainLoop();
                break;
        }
    }

    int LMMainLoop() throws Exception {
        grid.printGrid();
        System.out.println();
        int player = 0;
        int winner = -1;
        while(true){
            if((winner = grid.updAndGetWinner()) != -1){
                System.out.println("Player " + winner + " wins!");
                return 0;
            }
            System.out.print("Player " + player + " || Enter a column: ");
            while(!grid.placeThingy(player, sc.nextInt())){
                System.out.print("Enter an empty column: ");
            }
            System.out.println();
            grid.printGrid();
            System.out.println();
            if(player == 0)
                player = 1;
            else
                player = 0;
        }
    }

    int OMMainLoop() throws Exception {
        OnlinePlayer op = new OnlinePlayer();
        grid.printGrid();
        System.out.println();
        int player = 0;
        int winner = -1;
        while(true){
            if((winner = grid.updAndGetWinner()) != -1){
                System.out.println("Player " + winner + " wins!");
                return 0;
            }
            System.out.print("Player " + player + " || Enter a column: ");
            while(!grid.placeThingy(player, sc.nextInt())){
                System.out.print("Enter an empty column: ");
            }
            System.out.println();
            grid.printGrid();
            System.out.println();
        }
    }

    // return val of 0 = someone wins, 1 = tie;
    int simulateGame(boolean printOutput) throws Exception {
        if(printOutput){
            grid.printGrid();
            System.out.println();
        }
        int player = 0;
        int winner = -1;
        while(true){
            if((winner = grid.updAndGetWinner()) != -1){
                if(printOutput){
                    System.out.println("Player " + winner + " wins!");
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
                    System.out.println("new column: " + col + "\n");
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
