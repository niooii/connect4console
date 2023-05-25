package com.niooii.onlinestuff;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class OnlinePlayer {
    public class ChallengeWaiter extends Thread {
        public void start(){
            System.out.println("running mainloop??");
            try {
                mainLoop();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        void mainLoop() throws IOException, InterruptedException {
            while(!getStringResponse().equals("thisstringwillneverbesent,ever")){
                sleep(200);
                System.out.println("interesting");
            }
        }

        public ChallengeWaiter(){
            start();
        }
    }
    int ID;
    Socket sock=new Socket("localhost",8811);
    DataInputStream din=new DataInputStream(sock.getInputStream());
    DataOutputStream dout=new DataOutputStream(sock.getOutputStream());
    boolean isIdle = true;
    Scanner sc = new Scanner(System.in);
    public OnlinePlayer() throws IOException {
        System.out.print("Enter your display name: ");
        sendString(sc.nextLine());
        System.out.println(getStringResponse());
        ID = Integer.parseInt(getStringResponse());
        System.out.println("Your unique ID: " + ID);
//        ChallengeWaiter waiter = new ChallengeWaiter();
        waitingLoop();
    }

    public void waitingLoop() throws IOException {
        while(isIdle){
            System.out.print("[IDLE] ");
            sendWaitingData(sc.nextLine());
            String idk = "a";
            idk = getStringResponse();
            System.out.println(idk);
        }
    }

    public void mainLoop() throws IOException {
        while(true){
            System.out.print("enter a column to move in: ");
            sendData(sc.nextLine());
            System.out.println(getStringResponse());
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
