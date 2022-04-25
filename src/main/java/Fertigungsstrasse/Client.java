package Fertigungsstrasse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static String status = "bereit";
    private static String sConsole = "";
    private static String ip = "10.0.207.12";
    private static int port = 43000;

    public static void setsConsole(String sConsole) {
        Client.sConsole = sConsole;
    }

    public static String convertJson(String[] splitted) {
        return "{" +
                "\n  Auftrag {" +
                "\n    \"auftragsnr\": \"" + splitted[0] + "\"," +
                "\n    \"produkt\": \"" + splitted[1] + "\"," +
                "\n    \"menge\": \"" + splitted[2] + "\"," +
                "\n  }" +
                "\n}";
    }

    public static void main(String[] args) throws IOException {
        try {
            Socket clientSocket = new Socket(ip, port);
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            consoleReaderT console = new consoleReaderT();
            console.start();
            while (true) {
                output.println(status);
                if (sConsole.equals("ende")) {
                    output.println("exit");
                    break;
                }
                try {
                    String[] splitted = input.readLine().split(";");

                    String json = convertJson(splitted);
                    starteHardware(json);
                    output.println(status);
                } catch (IndexOutOfBoundsException iob) {
                    System.out.println("Kein CSV!");
                }
            }
            input.close();
            output.close();
            clientSocket.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void starteHardware(String json) {
        // TODO: starteHardware startet Prouktion und setzt Status auf "verarbeitung"
        // Wenn erfolgreich abgeschlossen wird Status auf "bereit" gesetzt, bei Fehler auf "fehler"
        // YOUR CODE GOES HERE
    }
}

class consoleReaderT extends Thread {
    @Override
    public void run() {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                Client.setsConsole(console.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
