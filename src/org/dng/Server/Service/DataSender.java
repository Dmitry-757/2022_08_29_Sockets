package org.dng.Server.Service;

import org.dng.AppContext;

import java.io.BufferedWriter;
import java.io.IOException;

public class DataSender {
    public static void sendData(BufferedWriter out, GetSentenceI getSentenceMethod) throws IOException {
        out.write(getSentenceMethod.getSentence()+'\n');
        out.flush();
        System.out.println("answer was passed");
        AppContext.getMyLogger("SocketServer").info("answer was passed");

    }
}
