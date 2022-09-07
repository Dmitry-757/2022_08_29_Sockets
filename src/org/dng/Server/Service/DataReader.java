package org.dng.Server.Service;

import org.dng.AppContext;

import java.io.BufferedReader;
import java.io.IOException;

public class DataReader {
    public static String readData(BufferedReader in) throws IOException {
//waiting for date from client and read it after date coming
        String clientMessage = in.readLine();
        System.out.println("Get massage from client = " + clientMessage);
        AppContext.getMyLogger("SocketServer").info("Get massage from client = " + clientMessage);

        return clientMessage;
    }
}
