package org.dng.Server;


import org.dng.AppContext;
import org.dng.Server.Service.GetSentenceI;
import org.dng.Server.Service.RaveGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerFactory {
    static final int MAX_THREADS = 5;
    static final int PORT_NUMBER = 8000;
    static final int maxCountOfClients = 3;
    static final String ipAddress = "127.0.0.1";
    static ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
    static final GetSentenceI getSentenceMethod = RaveGenerator::getSentence;
    //static RaveGenerateI raveGenerator;

    public static void createServers(){
        //create server on port PORT_NUMBER and console reader on the server
//        try(ServerSocket server = new ServerSocket(PORT_NUMBER);
        try(ServerSocket server = new ServerSocket(PORT_NUMBER, maxCountOfClients, InetAddress.getByName(ipAddress));
            BufferedReader keyboardBufReader = new BufferedReader(
                    new InputStreamReader(System.in));
            ) {
            System.out.println("Server socket and keyboardReader for commands in server part created");

            //if server socked is not closed
            while (!server.isClosed()){
                //let`s look at  commands from keyboard
                if (keyboardBufReader.ready()){
                    System.out.println("some commands is in console!");
                    String consoleCommand = keyboardBufReader.readLine();
                    if(consoleCommand.equalsIgnoreCase("exit") ||
                            consoleCommand.equalsIgnoreCase("quit")){
                        System.out.println("There is no beer more! \n" +
                                "Factory will be closed...\n" +
                                "Servers will not be started.\n");
                        AppContext.getMyLogger("SocketServer").info("Server-factory is shutting down");
                        server.close();
                        break;
                    }
                }

                System.out.println("start waiting connection to server socket...");
                //start waiting connection to server socket
                Socket client = server.accept();
                //after connecting server creates socket and now it need to pull it to new thread
//                GetSentenceI gs = RaveGenerator::getSentence;
                threadPool.execute(new ClientProcessor(client, getSentenceMethod));
                System.out.println("Connection accepted and pass to processing in multithreading part...");
                AppContext.getMyLogger("Socket server").info("Connection accepted from "+client.getInetAddress());
            }
            //lets end work of thread pool after all threads stop working
            threadPool.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
