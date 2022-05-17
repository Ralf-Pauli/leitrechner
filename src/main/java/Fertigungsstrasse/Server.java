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

    public static void main(String[] args) {
        Server.openConnection();
    }

    public static void openConnection() {
        try (ServerSocket sv = new ServerSocket(43000)) {
            Socket connection = sv.accept();

            boolean stop = false;
            String answer;
            String orderMsg;
            String oldOrder;

            PrintWriter output = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            System.out.println("Connected");
            LocalDate lastRead = LocalDate.of(2022, 4, 4);
            while (!stop) {
                if (orders.size() == 0) {
                    orders = DatabaseManager.getNewAuftrage(lastRead);
                }
                answer = input.readLine();
                System.out.println("Answer: " + answer);
                if (answer == null) {
                    output.println("Bitte Status senden!");
                } else if (answer.contains("exit")) {
                    stop = true;
                } else if (answer.contains("bereit")) {
                    orderMsg = getMessage();
                    System.out.println("Message: " + orderMsg);
                    output.println(orderMsg);
//                    DatabaseManager.setStatus(answer.split(";")[0], "fertig");
                } else if (answer.contains("lauft")){
                    System.out.println("läuft");
                } else {
                    continue;
                }
                oldOrder = orders.get(0);
                orders.remove(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getMessage() {
        return orders.get(0);
    }
}
