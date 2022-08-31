package org.dng;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (
        ServerSocket serverSocket = new ServerSocket(8000);
        ){
            if(serverSocket != null){
                System.out.println("Сервер запущен!");
            }

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client accepted!");
                OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());

                writer.write("my string");
                writer.flush();
                System.out.println("answer was wrote");

                writer.close();
                clientSocket.close();
            }
        }

    }
}
