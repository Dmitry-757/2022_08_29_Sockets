package org.dng.Client.UI;

public class ConsoleUI implements MessagePrinterI {
    @Override
    public void printMessage(String msg){
        System.out.println(msg);
    }
}
