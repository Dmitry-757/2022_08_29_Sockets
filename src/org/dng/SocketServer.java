package org.dng;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    // метод работы сервера
    // вход: ip-адрес сервера, порт
//    public static void startServer(String ipStr, int port) {
    public static void main(String[] args) {
        // ресурсы
        //ServerSocket serverSocket;
        // блок работы
        int maxCountOfClients = 50;
//        try( ServerSocket serverSocket = new ServerSocket(1024, maxCountOfClients, InetAddress.getByName("0.0.0.0")) ) {
        try( ServerSocket serverSocket = new ServerSocket(8000);
                //begin wait for connection
                Socket remoteClient = serverSocket.accept();
                //System.out.println("Client accepted!");
                BufferedReader in = new BufferedReader(new InputStreamReader(remoteClient.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(remoteClient.getOutputStream()));
        ) {
            while (true) {
                String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                System.out.println(word);
                AppContext.getMyLogger("SocketServer").info(word);
                //answer to client
                out.write("you wrote : " + word + "\n");
                out.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //
        }
    }
}
