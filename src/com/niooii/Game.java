package com.niooii;
import com.niooii.*;

import java.util.Scanner;

public class Game {
    Scanner sc = new Scanner(System.in);
    // PLAYERS: 0, 1
    Grid grid = new Grid(6, 7);
    public Game() throws Exception {
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
}
