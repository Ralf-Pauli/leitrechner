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
                // Wenn keine offenen Aufträge in der Liste sind holl alle neuen offenen aus der DB
                if (orders.size() == 0) {
                    orders = DatabaseManager.getNewAuftrage(lastRead);
                }
                // Lese Nachricht vom Client
                answer = input.readLine();
                System.out.println("Input: " + answer);
                // Wenn jemand manuell "" sendet oder einfach Enter drückt
                if (answer == null) {
                    output.println("Bitte Status senden!");
                // Wenn der Client exit sendet wird unser Programm auch "beendet" bzw. er geht aus der while Schleife raus, aber danach passiert nichts mehr
                } else if (answer.contains("exit")) {
                    stop = true;
                // Dies sollte nur beim ersten mal senden vorkommen
                } else if(answer.equals("bereit")){
                    orderMsg = getMessage();
                    System.out.println("Output: " + orderMsg);
                    output.println(orderMsg);
                // Der Status wird in der DB aktuallisiert,
                // der fertige Auftrag aus der Liste entfernt,
                // der neue Auftrag wird aus der Liste geholt und an den Client gesendet.
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
