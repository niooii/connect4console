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
        Scanner sc= new Scanner(System.in);
        while(true){
            switch(sc.nextInt()){
                case 1:
                    new Game(GameType.LOCALMULTIPLAYER);
                    break;
                case 2:
                    new Game(GameType.ONLINEMULTIPLAYER);
                    break;
            }
            printModes();
        }
    }

    private static void printModes() {
        System.out.println("-------------------------------");
        System.out.println("           MAIN MENU");
        System.out.println("-------------------------------");
        System.out.println("1: Local Multi-player (2-player)");
        System.out.println("2: Online Multi-player (yes, this actually works)");
        System.out.println("3: Single-player (play against computer)");
        System.out.println("4: Simulate Game");
        System.out.println("5: Simulate Multiple");
        System.out.println("note: all simulations have completely random placements.");
    }


}