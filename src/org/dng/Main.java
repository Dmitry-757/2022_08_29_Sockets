package org.dng;

//import org.dng.Server.Service.RaveGenerator;
import org.dng.Server.Security.SecurityService;
import org.dng.Server.ServerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
//import java.net.URL;


public class Main {


    public static void main(String[] args) {
        //start servers factory
        ServerFactory.createServers();


//        System.out.println(url.getPath());
//        System.out.println(RaveGenerator.getSentance());

//        try {
//            byte[] hash = SecurityService.getHash("password");
//            StringBuilder sb = new StringBuilder();
//            for (byte b:hash){
//                sb.append(String.format("%02X ",b));
//            }
//
//            System.out.println("Hash of 'password' is "+sb.toString());
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
    }
}
