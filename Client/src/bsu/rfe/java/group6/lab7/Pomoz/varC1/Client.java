package bsu.rfe.java.group6.lab7.Pomoz.varC1;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try(
                Socket socket = new Socket("192.168.100.115",8000);
                BufferedWriter writer =
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream()));
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));
        ){
            System.out.println("Connected to server");
            String request = "Borisov";
            System.out.println("Request: "+ request);
            String response = (int)(Math.random()*30-10)+"";
            System.out.println("Response: "+ response);
            writer.write(request);
            writer.newLine();
            writer.flush();

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}

