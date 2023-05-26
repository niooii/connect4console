package com.niooii.onlinestuff;

import com.niooii.Grid;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class OnlinePlayer {
    final int rows = 6;
    final int cols = 7;
    Grid grid;
    String print = "";
    int playernum;
    class ResponseReciever extends Thread {
        @Override
        public void run(){
            try {
                recvLoop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //recieve responses from server
        void recvLoop() throws Exception {
            String str;
            while(true){
                if((str = getStringResponse()) != null){
                    if(str.startsWith("SETNOTIDLE")){
                        isIdle = false;
                    } else if(str.startsWith("SETOPPONENT")){
                        opponent = str.substring(str.indexOf("|") + 1);
                    } else if(str.startsWith("GAME_ABORTED")){
                        System.out.println("game has been aborted...\nPress [enter] to continue.");
                        grid = null;
                        opponent = null;
                        isIdle = true;
                    } else if(str.startsWith("MOV")){
                        HandleMoveAndUpdate(parsePlayer(str), parseColumn(str));
                        int winner = -1;
                        // i have no idea why this logic works, trial and error!
                        boolean stop = grid.isFull() || (winner = grid.updAndGetWinner()) != -1;
                        if(stop){
                            if(grid.isFull()){
                                sendString("FULL");
                            } else {
                                if(winner == playernum){
                                    sendString("WINNER|" + ID);
                                }
                            }
                            grid = null;
                            opponent = null;
                            isIdle = true;
                        }
                        char type;
                        /* why does this logic work?
                        WHO KNOWS? not me. and I don't particularly care. */
                        if(playernum == 1){
                            if(playernum == parsePlayer(str))
                                type = 'X';
                            else
                                type = 'O';
                        } else {
                            if(playernum == parsePlayer(str))
                                type = 'O';
                            else
                                type = 'X';
                        }
                        if(parsePlayer(str) == playernum)
                            print = opponent + "'s turn (" + type + "): ";
                        else
                            print = "your turn (" + type + "): ";
                        if(!stop){
                            System.out.println();
                            System.out.print(print);
                            System.out.println();
                        }
                    } else if(str.startsWith("SETIGID")){
                        playernum = Integer.parseInt(str.substring(str.indexOf("|") + 1));
                    } else {
                        System.out.println(str);
                    }
                }
            }
        }
    }
    int ID;
    Socket sock;
    DataInputStream din;
    DataOutputStream dout;
    boolean isIdle = true;
    Scanner sc = new Scanner(System.in);
    ResponseReciever waiter = new ResponseReciever();
    String opponent;
    public OnlinePlayer() throws IOException {
        try{
            sock=new Socket("localhost",8811);
            din=new DataInputStream(sock.getInputStream());
            dout=new DataOutputStream(sock.getOutputStream());
        } catch (Exception e){
            System.out.println("Could not connect to server: ");
            System.out.println(e);
            return;
        }
        System.out.println("\nconnected to server!");
        System.out.print("Enter your display name: ");
        sendString(sc.nextLine());
//        System.out.println(getStringResponse());
        ID = Integer.parseInt(getStringResponse());
        System.out.println();
        waiter.start();
        waitingLoop();
    }

    public int parseColumn(String res){
        return Integer.parseInt(res.substring(res.indexOf("|") + 1));
    }

    public int parsePlayer(String res){
        return Integer.parseInt(res.substring(res.indexOf(":") + 1, res.indexOf("|")));
    }

    public void HandleMoveAndUpdate(int player, int col) throws Exception {
        grid.placeThingy(player, col);
        grid.printGrid();
    }

    public void waitingLoop() throws IOException {
        while(true){
        while (isIdle) {
            String str = "";
            try {
                str = sc.nextLine();
                if (!isIdle) {
                    break;
                }
                if (str.toLowerCase().startsWith("challenge")) {
                    //break from loop early so shit doesnt SEND
                    if (!str.contains(" ") || str.substring(str.indexOf(" ") + 1).length() == 0) {
                        System.out.println("enter an id bruh");
                        return;
                    }
                    int id = Integer.parseInt(str.substring(str.indexOf(" ") + 1));
                    sendString("CHALLENGE|" + id);
                } else if (str.toLowerCase().startsWith("view")) {
                    sendString("VIEW");
                } else if (str.toLowerCase().startsWith("accept")) {
                    if (!str.contains(" ")) {
                        System.out.println("enter an id bruh");
                    } else {
                        int id = Integer.parseInt(str.substring(str.indexOf(" ") + 1));
                        sendString("ACCEPT|" + id);
                    }
                } else {
                    sendWaitingData(str);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        GameLoop();
    }
    }

    public void GameLoop() throws IOException {
        grid = new Grid(rows, cols);
        grid.printGrid();
        while(!isIdle){
            sendData(sc.nextLine());
//            if(grid.updAndGetWinner() != -1){
//                System.out.println("SOMEONE WON SOMEONE WON");
//            }
        }
    }

    public void sendData(String str) throws IOException {
        dout.writeUTF( ID + "|" + str);
        dout.flush();
    }

    public void sendWaitingData(String str) throws IOException {
        dout.writeUTF("WAITING|" + str);
        dout.flush();
    }

    public void sendString(String str) throws IOException {
        dout.writeUTF(str);
        dout.flush();
    }

    public String getStringResponse() throws IOException {
        return din.readUTF();
    }

    public int getMoveResponse() throws IOException {
        return Integer.parseInt(din.readUTF());
    }

    public String formatMoveData(int ID, int column){
        return ID + "|" + column;
    }

    public void destroy() throws IOException {
        dout.close();
        sock.close();
    }
}
