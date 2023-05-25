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
    class ChallengeWaiter extends Thread {
        @Override
        public void run(){
            try {
                mainLoop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //recieve responses from server
        void mainLoop() throws Exception {
            String str;
            while(true){
                if((str = getStringResponse()) != null){
                    if(str.startsWith("SETNOTIDLE")){
                        isIdle = false;
                    } else if(str.startsWith("SETOPPONENT")){
                        opponent = str.substring(str.indexOf("|") + 1);
                    } else if(str.startsWith("MOV")){
                        HandleMoveAndUpdate(parsePlayer(str), parseColumn(str));
                    } else {
                        System.out.println(str);
                    }
                }
            }
        }
    }
    int ID;
    Socket sock=new Socket("localhost",8811);
    DataInputStream din=new DataInputStream(sock.getInputStream());
    DataOutputStream dout=new DataOutputStream(sock.getOutputStream());
    boolean isIdle = true;
    Scanner sc = new Scanner(System.in);
    ChallengeWaiter waiter = new ChallengeWaiter();
    String opponent;
    public OnlinePlayer() throws IOException, InterruptedException {
        while(!sock.isConnected()){
            sleep(400);
            System.out.println("trying to connect...");
        }
        System.out.print("Enter your display name: ");
        sendString(sc.nextLine());
        System.out.println(getStringResponse());
        ID = Integer.parseInt(getStringResponse());
        System.out.println("Your unique ID: " + ID);
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
                    System.out.println("started game with " + opponent);
                    break;
                }
                if (str.toLowerCase().startsWith("challenge")) {
                    //break from loop early so shit doesnt SEND
                    if (!str.contains(" ")) {
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
                        return;
                    }
                    int id = Integer.parseInt(str.substring(str.indexOf(" ") + 1));
                    sendString("ACCEPT|" + id);
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
        while(true){
            System.out.print("enter a column to move in: ");
            sendData(sc.nextLine());
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
        this.destroy();
    }
}
