package Fertigungsstrasse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static String status = "bereit";
    private static String json = "";

    public static void sendMessage(String message, String auftrag) {
        if (message.equals("bereit")) {
            status = message;
        } else {
            status = "fehler";
        }
    }

    public static String getJson() throws IOException {
        String ip = "10.0.207.13";
        int port = 42000;
        try {
            Socket clientSocket = new Socket(ip, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(input.readLine());
            boolean abbruch = false;

            while (!abbruch) {
                try {
                    output.println(status);
                    String[] split = input.readLine().split(";");
                    json = "{" +
                            "\n  Auftrag {" +
                            "\n    \"auftragsnr\": \"" + split[0] + "\"," +
                            "\n    \"produkt\": \"" + split[1] + "\"," +
                            "\n    \"menge\": \"" + split[2] + "\"," +
                            "\n  }" +
                            "\n}";
                } catch (IndexOutOfBoundsException iob) {
                    System.out.println("Kein CSV!");
                }
            }

            input.close();
            output.close();
            br.close();
            clientSocket.close();

        } catch (IOException e) {
            throw new IOException("Kein Gültiger Datensatz angekommen!!");
        }
        return json;
    }

    public static void main(String[] args) throws IOException {
        String ip = "10.0.207.12";
        int port = 43000;
        try {
            Socket clientSocket = new Socket(ip, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(input.readLine());
            boolean abbruch = false;
            output.println(status);

            while (!abbruch) {
                try {
                    String[] split = input.readLine().split(";");
                    json = "{" +
                            "\n  Auftrag {" +
                            "\n    \"auftragsnr\": \"" + split[0] + "\"," +
                            "\n    \"produkt\": \"" + split[1] + "\"," +
                            "\n    \"menge\": \"" + split[2] + "\"," +
                            "\n  }" +
                            "\n}";
                    System.out.println(json);
//                    StarteHardware(json);
                    while (status.equals("verarbeitung")) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IndexOutOfBoundsException iob) {
                    System.out.println("Kein CSV!");
                }
            }
            input.close();
            output.close();
            br.close();
            clientSocket.close();

        } catch (IOException e) {
            throw new IOException("Kein Gültiger Datensatz angekommen!!");
        }
    }
}
