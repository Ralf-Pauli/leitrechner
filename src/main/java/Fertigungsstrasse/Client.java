package Fertigungsstrasse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    public static String status = "bereit";
    private static String json = "";

    /*public static String getJson() throws IOException {
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
    }*/
    
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
//        ReadStatus thread = new ReadStatus();
//        thread.start();
        System.out.println("Does the train come in?");
        String ip = "10.0.207.12";
        int port = 43000;
        try {
            Socket clientSocket = new Socket(ip, port);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(input.readLine());
            boolean abbruch = false;
            output.println(status);

            while (!abbruch) {
                try {
                    String[] splitted = input.readLine().split(";");
                    json = convertJson(splitted);
                    System.out.println(json);
                    starteHardware(json);
                    while (status.equals("verarbeitung")) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (status.equals("fehler")){
                        writeStatus(json, "fehler");
                    }
                } catch (IndexOutOfBoundsException iob) {
                    System.out.println("Kein CSV!");
                }
                System.out.println(input.readLine());
            }
            input.close();
            output.close();
            console.close();
            clientSocket.close();

        } catch (IOException e) {
            throw new IOException("Kein Gültiger Datensatz angekommen!!");
        }
    }

    private static void starteHardware(String json){
        System.out.println("Tut tut da zug ist da");
        status = "verarbeitung";


    }



    public static void writeStatus(String auftrag, String status){
        System.out.println("Write Status - " + status + " for Auftrag: " + auftrag);
    }
}
 class ReadStatus extends Thread{
    @Override
    public void run() {
        Timer timer = new Timer();

        while (true){
            System.out.println(Client.status);
            try {
                ReadStatus.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
