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
        try {
            ServerSocket sv = new ServerSocket(42000);
            Socket connection = sv.accept();

            boolean stop = false;
            String message = "";

            String msg = "A01.1;A;2";


            PrintWriter output = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            output.println("Server started!");
            int counter = 0;
            while (!stop) {
                //output.println(br.readLine());
                message = input.readLine();

                //eingabe wird verarbeitet
                if (message.contains("exit")) {
                    stop = true;
                    output.println("Stop Server");
                } else if (message.contains("bereit")) {
                    output.println(msg);
                } else if (message.contains("fehler")) {
                    if (counter < 3) {
                        counter++;
                        output.println(msg);
                    } else {
                        counter = 0;
                    }
                } else {
                    output.println("Bitte Status senden!");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void send() {

    }
}
