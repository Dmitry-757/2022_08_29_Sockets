package org.dng.Server;


import org.dng.AppContext;
import org.dng.Server.Service.DataReader;
import org.dng.Server.Service.DataSender;
import org.dng.Server.Service.GetSentenceI;

import java.io.*;
import java.net.Socket;

public class ClientProcessor implements Runnable{
    private static Socket clientSocket;
    private static GetSentenceI getSentenceMethod;

    public ClientProcessor(Socket clientSocket, GetSentenceI methodGetSentenceRef) {
        ClientProcessor.clientSocket = clientSocket;
        ClientProcessor.getSentenceMethod = methodGetSentenceRef;
    }

    @Override
    public void run() {
//        RaveGenerateI rg = RaveGenerator::getSentence;
//        GetSentenceI gs = RaveGenerator::getSentence;
        System.out.println("Thread "+Thread.currentThread().getName()+" started");
        try (
                //ServerSocket serverSocket = new ServerSocket(8000);

                // ***** !!!!! *****
                //for write in socket
                // канал записи в сокет следует инициализировать раньше канал чтения
                // для избежания блокировки выполнения программы на ожидании заголовка в сокете !!!
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                //for read from socket
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        ) {

            while (!clientSocket.isClosed()) {
                //waiting for date from client and read it after date coming
//                String clientMessage = in.readLine();
//                System.out.println("Get massage from client = " + clientMessage);
//                AppContext.getMyLogger("SocketServer").info("Get massage from client = " + clientMessage);
                String clientMessage = DataReader.readData(in);

//                out.write(getSentenceMethod.getSentence()+'\n');
//                out.flush();
//                System.out.println("answer was passed");
//                AppContext.getMyLogger("SocketServer").info("answer was passed");

                DataSender.sendData(out, getSentenceMethod);

                if (clientMessage.equalsIgnoreCase("exit") || clientMessage.equalsIgnoreCase("quit")) {
                    System.out.println("Client was decided become RIP ;)");
                    AppContext.getMyLogger("SocketServer").info("Client was decided to disconnect");
                    break;
                }
            }

//            in.close();
//            out.close();
//            clientSocket.close();
            System.out.println("Closing connection to client on side of server \n" +
                    "but some other threads are still working...\n" +
                    "termination of work will be on a next connection.");
            AppContext.getMyLogger("SocketServer").info("Closing connection to client on server side");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
