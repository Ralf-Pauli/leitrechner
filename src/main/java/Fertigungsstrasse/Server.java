package Fertigungsstrasse;

import javax.management.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private List<String> orders;

    public static void main(String[] args) {
        Server.openConnection();
    }
    public static void openConnection() {
        try{
            ServerSocket sv = new ServerSocket(42000);
            Socket connection = sv.accept();

            boolean stop = false;
            String message = "";

            PrintWriter output = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            output.println("Server started!");

            while (!stop){
                output.println(br.readLine());
                message = input.readLine();
                if (message.contains("abgeschlossen")){

                }

                //eingabe wird verarbeitet
                if (message.contains("exit"))
                {
                    stop = true;
                    output.println("Bye");
                }
                else if (message.contains("ping"))
                {
                    output.println("Pong!");
                }
                else
                {
                    output.println("You have to write ping!");
                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public String getString(){
        Query q =
        return "";
    };

    public void send(){

    }
}
