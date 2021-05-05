package bsu.rfe.java.group6.lab7.Pomoz.varC1;

import java.io.*;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args){

        try(ServerSocket server = new ServerSocket(8000))
        {
            System.out.println("Server started");
            while(true)
                try(Phone phone = new Phone(server))
                {
                    String request = phone.readLine();
                    System.out.println("Request: "+request);
                    String response = (int)(Math.random()*30-10)+"";
                    phone.writeLine(response);
                    System.out.println("Response: "+response);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
