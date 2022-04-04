package Fertigungsstrasse;

import Database.DatabaseManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<String> orders = new ArrayList<>();

    public static void setOrders(List<String> orders) {
        Server.orders = orders;
    }

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

//            DatabaseThread databaseThread = new DatabaseThread();
//            databaseThread.start();
//            databaseThread.setDate(LocalDate.of(2022, 4, 4));

            System.out.println("Connected");
            output.println("Server started!");
            LocalDate lastRead = LocalDate.of(2022, 4, 4);
            int counter = 0;
            while (!stop) {
                if (orders.size() == 0) {
                    orders = DatabaseManager.getNewAuftrage(lastRead);
                }

                answer = input.readLine();
                if (answer == null) {
                    output.println("Bitte Status senden!");
                } else if (answer.contains("exit")) {
                    stop = true;
                    output.println("Stop Server");
                } else if (answer.contains("bereit")) {
                    auftragMsg = getMessage();
                    System.out.println("Message: " + auftragMsg);
                    output.println(auftragMsg);
                    if (counter == 1) {
                        orders.remove(0);
                        counter = 0;
                    }
                } else if (answer.contains("fehler")) {

                } else {
                    output.println("Nachricht fehlerhaft");
                }
                counter++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Socket clOpen(Socket conn, ServerSocket sv) {
        try {
            conn = sv.accept();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public static String getMessage() {
        return orders.get(0);
    }
}

//class DatabaseThread extends Thread {
//    private LocalDate date;
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    @Override
//    public void run() {
//        Server.setOrders(DatabaseManager.getNewAuftrage(date));
//    }
//}
