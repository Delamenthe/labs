package bsu.rfe.java.group6.lab7.Pomoz.varC1;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try(Phone phone = new Phone("192.168.100.115",8000))
                {
            System.out.println("Connected to server");

            String request = "Borisov";
            System.out.println("Request: "+ request);
            phone.writeLine(request);
            String response = phone.readLine();
            System.out.println("Response: "+ response);

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}

