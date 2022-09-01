package org.dng;

import java.io.*;
import java.net.Socket;

public class SocketClient {


    public static void main(String[] args) {
        try (
                Socket clientSocket = new Socket("localhost", 8000); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//                // читать соообщения с сервера
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                // писать туда же
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        ) {
            while (true) {
                System.out.println("print what something to pass to server:");
                String ourMessage = reader.readLine(); // ждём пока клиент что-нибудь
                // не напишет в консоль
                out.write(ourMessage + "\n"); // отправляем сообщение на сервер
                out.flush();
                //Thread.sleep(2000);
                String serverMessage = in.readLine(); // ждём, что скажет сервер
                System.out.println(serverMessage); // получив - выводим на экран

                if (serverMessage.equalsIgnoreCase("exit") ||
                        serverMessage.equalsIgnoreCase("quit") ||
                        ourMessage.equalsIgnoreCase("exit") ||
                        ourMessage.equalsIgnoreCase("quit")) {
                    System.out.println("let`s end communication ;)");
                    break;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
