package org.dng;

import org.dng.Server.RaveGenerator;
//import java.net.URL;


public class Main {

    public static void main(String[] args) {
        //start servers factory
        //ServerFactory.createServers();

//        URL url = Main.class.getClassLoader().getResource("resources/text.txt");
//        System.out.println(url.getPath());
        System.out.println(RaveGenerator.getSentance());


    }
}
