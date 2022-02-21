package Fertigungsstrasse;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static String getJson() {
        String ip = "10.0.207.13";
        int port = 42000;
        String json = "";
        try {
            Socket clientSocket = new Socket(ip, port);
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(input.readLine());

            try {
//                output.println(br.readLine());
                String[] split = input.readLine().split(";");
                json = "{" +
                        "\n  Auftrag {" +
                        "\n    \"auftragsnr\": \"" + split[0] + "\"" +
                        "\n    \"produkt\": \"" + split[1] + "\"" +
                        "\n    \"menge\": \"" + split[2] + "\"" +
                        "\n  }" +
                        "\n}";
            } catch (IndexOutOfBoundsException iob) {
                System.out.println("Kein Gültiger Datensatz angekommen!");
            }


            input.close();
            output.close();
//            br.close();
            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Verbindung konnte nicht hergestellt werden.");
        }
        return json;
    }

    public static void main(String[] args) {
        System.out.println(getJson());
    }

}
