package ProductionLine;

import Database.DatabaseManager;

import java.io.BufferedReader;
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
                System.out.println("Input: " + answer);
                if (answer == null) {
                    output.println("Bitte Status senden!");
                } else if (answer.contains("exit")) {
                    stop = true;
                } else if(answer.equals("bereit")){
                    orderMsg = getMessage();
                    System.out.println("Output: " + orderMsg);
                    output.println(orderMsg);
                } else if (answer.contains("bereit")) {
                    DatabaseManager.setStatus(answer.split(";")[0], "fertig");
                    orders.remove(0);
                    orderMsg = getMessage();
                    System.out.println("Output: " + orderMsg);
                    output.println(orderMsg);
                } else if (answer.contains("lauft")){
                    System.out.println("läuft");
                } else {
                    continue;
                }
                oldOrder = orders.get(0);

            }

        } catch (Exception e) {
            System.out.println("Das Programm wird beendet");
            System.exit(-1);
        }

    }

    public static String getMessage() {
        String result = "";
        try {
            result = orders.get(0);
        } catch (IndexOutOfBoundsException ie){
            System.out.println("Keine neuen Datensätze vorhanden");
        }
        return result;
    }
}
