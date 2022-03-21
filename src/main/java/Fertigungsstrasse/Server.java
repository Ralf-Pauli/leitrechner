package Fertigungsstrasse;

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
            ServerSocket sv = new ServerSocket(43000);
            Socket connection = sv.accept();

            boolean stop = false;
            String answer = "";
            String auftragMsg = "";

            PrintWriter output = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connected");
            output.println("Server started!");
            int counter = 0;
            while (!stop) {
                answer = input.readLine();

                //eingabe wird verarbeitet
                if (answer == null) {
                    output.println("Bitte Status senden!");
                } else if (answer.contains("exit")) {
                    stop = true;
                    output.println("Stop Server");
                } else if (answer.contains("bereit")) {
                    writeStatus();
                    auftragMsg = getMessage();
                    System.out.println("Message: " + auftragMsg);
                    output.println(auftragMsg);
                    output.println(console.readLine());
                } else if (answer.contains("fehler")) {
                    writeStatus();
                } else {
                    output.println("Nachricht fehlerhaft");
                }
            }
            console.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Socket clOpen(Socket conn, ServerSocket sv){
        try {
            conn = sv.accept();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }



    public static String getMessage(){
        return "A01.1;A;2";
    }

    public static void writeStatus(){
        System.out.println("Status: ");
    }

    public void send() {

    }
}
