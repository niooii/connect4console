package com.niooii;

import java.util.Scanner;

import com.niooii.onlinestuff.OnlinePlayer;

import static com.niooii.Grid.playerOneChar;
import static com.niooii.Grid.playerZeroChar;

public class Game {
    final int rows;
    final int cols;
    Scanner sc = new Scanner(System.in);
    // PLAYERS: 0, 1
    Grid maingrid;
    public Game(GameType type, int rows, int cols, int winningNum) throws Exception {
        this.rows = rows;
        this.cols = cols;
        maingrid = new Grid(rows, cols, winningNum);
        switch(type){
            case LOCALMULTIPLAYER:
                LMMainLoop();
                break;
            case SIMULATION:
                System.out.println("simulation finished with status " + simulateGame(true, new Grid(rows, cols, winningNum), false, false));
                break;
            case MULTISIMULATION:
                int wins = 0;
                int ties = 0;
                System.out.println("How many games?");
                int games = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter 1 if you'd like to exclude output for tied games. ");
                boolean bool;
                bool = sc.nextLine().equals("1");
                for(int i = 0; i < games; i++){
                    int res = simulateGame(false, new Grid(rows, cols, winningNum), bool, true);
                    if(res == 0)
                        wins++;
                    else
                        ties++;
                }
                System.out.println("\nSimulation of " + games + " game(s) has completed.");
                System.out.println("Wins: " + wins);
                System.out.println("Ties: " + ties);
                break;
            case ONLINEMULTIPLAYER:
                OMMainLoop();
                break;
            case SINGLEPLAYER:
                againstComputerMainLoop();
                break;
        }
    }

    int LMMainLoop() {
        maingrid.printGrid();
        System.out.println();
        int player = 0;
        int winner = -1;
        while(true){
            if((winner = maingrid.updAndGetWinner()) != -1){
                System.out.println("Player " + winner + " wins!");
                return 0;
            }
            System.out.print("Player " + player + " || Enter a column: ");
            while(!maingrid.placeThingy(player, sc.nextInt())){
                System.out.print("Please enter a valid column: ");
            }
            System.out.println();
            maingrid.printGrid();
            System.out.println();
            if(player == 0)
                player = 1;
            else
                player = 0;
        }
    }

    int againstComputerMainLoop(){
        maingrid.printGrid();
        System.out.println();
        int player = 0;
        int winner;
        while(true){
            if((winner = maingrid.updAndGetWinner()) != -1){
                if(winner == 0){
                    maingrid.printGrid();
                    System.out.println("You win!");
                } else {
                    maingrid.printGrid();
                    System.out.println("You lose :(");
                }
                return 0;
            }
            if(player == 1){
                int cmove = maingrid.getComputerMove();
                System.out.println("Computer goes: " + cmove);
                while(!maingrid.placeThingy(player, cmove)){
                    cmove = maingrid.getComputerMove();
                }
                System.out.println();
                maingrid.printGrid();
                System.out.println();
            } else {
                System.out.print("Enter a column: ");
                while(!maingrid.placeThingy(player, sc.nextInt())){
                    System.out.print("Please enter a valid column: ");
                }
            }
            if(player == 0)
                player = 1;
            else
                player = 0;
        }
    }

    int OMMainLoop() throws Exception {
        new OnlinePlayer();
        return 0;
    }

    // return val of 0 = someone wins, 1 = tie;
    int simulateGame(boolean printOutput, Grid grid, boolean onlyPrintWon, boolean multipleMode) throws Exception {
        if(printOutput){
            grid.printGrid();
            System.out.println();
        }
        int player = 0;
        int winner = -1;
        while(true){
//            System.out.println(winner);
            if(winner != -1){
                if(multipleMode)
                    grid.printGrid();
                char temp;
                if(winner == 0){
                    temp = playerZeroChar;
                } else
                    temp = playerOneChar;
                System.out.println("Player " + winner + " (" + temp + ") wins!\n");
                return 0;
            }
            if(grid.isFull()){
                if(!onlyPrintWon){
                    grid.printGrid();
                    System.out.println("It's a tie...\n");
                }
                return 1;
            }
            int col = grid.getRandomFreeCol();
            grid.placeThingy(player, col);
            char playerChar;
            if(player == 0)
                playerChar = playerZeroChar;
            else
                playerChar = playerOneChar;
            if(printOutput)
                System.out.println("Player " + player + " (" + playerChar + "), column " + col + "\n");
            if(printOutput){
                grid.printGrid();
                System.out.println();
            }
            if(player == 0)
                player = 1;
            else
                player = 0;
            winner = grid.updAndGetWinner();
        }
    }
}
