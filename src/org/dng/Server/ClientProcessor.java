package org.dng.Server;


import org.dng.AppContext;
import org.dng.Server.Security.SecurityService;
import org.dng.Server.Service.DataReader;
import org.dng.Server.Service.DataSender;
import org.dng.Server.Service.RaveGeneratorI;

import java.io.*;
import java.net.Socket;

public class ClientProcessor implements Runnable{
    private final Socket clientSocket;
    private final RaveGeneratorI raveGenerator;
    private String clientName;

    public ClientProcessor(Socket clientSocket, RaveGeneratorI raveGenerator) {
        this.clientSocket = clientSocket;
        this.raveGenerator = raveGenerator;
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

                //try to authenticate client

                if (clientName == null) {
                    if ((clientName = SecurityService.authenticateClient(out, in)) == null) {
                        System.out.println("Client was not authenticated!");
                        AppContext.getMyLogger("SocketServer").warning("Client was not authenticated!");
                        break;
                    }
                    AppContext.getMyLogger("").info("User = " + clientName + " was authenticated.");
                }

                //waiting for date from client and read it after date coming
                String clientMessage = DataReader.readData(in);

                //writing answer to client
                DataSender.sendData(out, raveGenerator.getSentence());

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
