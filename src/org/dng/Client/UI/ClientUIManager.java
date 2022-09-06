package org.dng.Client.UI;


public class ClientUIManager {

    public static MessagePrinterI getMsgPrinter(TypeUI typeUI){
        switch (typeUI){
            case CONSOLE -> {
                return new ConsoleUI();
            }
            case DESKTOP -> {
                System.out.println("may be later ;)");
            }
            default -> throw new IllegalStateException("Unexpected value: " + typeUI);
        }
        return null;
    }

}

