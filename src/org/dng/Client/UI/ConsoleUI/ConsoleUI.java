package org.dng.Client.UI.ConsoleUI;

import org.dng.Client.UI.MessagePrinterI;

public class ConsoleUI implements MessagePrinterI {
    @Override
    public void printMessage(String msg){
        System.out.println(msg);
    }
}
