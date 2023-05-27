package com.niooii;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("\n" +
                "  ______                                                       __            ________                               \n" +
                " /      \\                                                     |  \\          |        \\                              \n" +
                "|  $$$$$$\\  ______   _______   _______    ______    _______  _| $$_         | $$$$$$$$  ______   __    __   ______  \n" +
                "| $$   \\$$ /      \\ |       \\ |       \\  /      \\  /       \\|   $$ \\        | $$__     /      \\ |  \\  |  \\ /      \\ \n" +
                "| $$      |  $$$$$$\\| $$$$$$$\\| $$$$$$$\\|  $$$$$$\\|  $$$$$$$ \\$$$$$$        | $$  \\   |  $$$$$$\\| $$  | $$|  $$$$$$\\\n" +
                "| $$   __ | $$  | $$| $$  | $$| $$  | $$| $$    $$| $$        | $$ __       | $$$$$   | $$  | $$| $$  | $$| $$   \\$$\n" +
                "| $$__/  \\| $$__/ $$| $$  | $$| $$  | $$| $$$$$$$$| $$_____   | $$|  \\      | $$      | $$__/ $$| $$__/ $$| $$      \n" +
                " \\$$    $$ \\$$    $$| $$  | $$| $$  | $$ \\$$     \\ \\$$     \\   \\$$  $$      | $$       \\$$    $$ \\$$    $$| $$      \n" +
                "  \\$$$$$$   \\$$$$$$  \\$$   \\$$ \\$$   \\$$  \\$$$$$$$  \\$$$$$$$    \\$$$$        \\$$        \\$$$$$$   \\$$$$$$  \\$$      \n");
        System.out.println("\n\n");
        printModes();

        GameType type;
        int rows = 6;
        int cols = 7;
        int winningNum = 4;
        Scanner sc= new Scanner(System.in);
        while(true){
            switch(sc.nextInt()){
                case 1:
                    new Game(GameType.LOCALMULTIPLAYER, rows, cols, winningNum);
                    break;
                case 2:
                    new Game(GameType.ONLINEMULTIPLAYER, 6, 7, 4);
                    break;
                case 3:
                    new Game(GameType.SINGLEPLAYER, rows, cols, winningNum);
                    break;
                case 4:
                    new Game(GameType.SIMULATION, rows, cols, winningNum);
                    break;
                case 5:
                    new Game(GameType.MULTISIMULATION, rows, cols, winningNum);
                    break;
                case 6:
                    System.out.print("How many rows? ");
                    int temprows = sc.nextInt();
                    while(temprows <= 1){
                        System.out.print("Must be above one: ");
                        temprows = sc.nextInt();
                    }
                    rows = temprows;
                    System.out.print("How many columns? ");
                    int tempcols = sc.nextInt();
                    while(tempcols <= 1){
                        System.out.print("Must be above one: ");
                        tempcols = sc.nextInt();
                    }
                    cols = tempcols;
                    System.out.print("How many in a row to win? ");
                    int tempwinningNum = sc.nextInt();
                    while(tempwinningNum <= 1){
                        System.out.print("Must be above one: ");
                        tempwinningNum = sc.nextInt();
                    }
                    winningNum = tempwinningNum;
                    break;
            }
            printModes();
        }
    }

    private static void printModes() {
        System.out.println("\n-------------------------------");
        System.out.println("           MAIN MENU");
        System.out.println("-------------------------------");
        System.out.println("1: Local Multi-player (2-player)");
        System.out.println("2: Online Multi-player (yes, this actually works)");
        System.out.println("3: Single-player (play against computer)");
        System.out.println("4: Simulate Game");
        System.out.println("5: Simulate Multiple");
        System.out.println("6: Edit game parameters (Offline modes only)");
        System.out.println("note: all simulations have completely random placements.");
    }
}