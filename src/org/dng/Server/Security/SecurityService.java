package org.dng.Server.Security;

import org.dng.Server.Service.DataReader;
import org.dng.Server.Service.DataSender;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.HashMap;

public class SecurityService {
    private static final int ALGORITHM_STRENGTH  = 65536;
    private static final int KEY_LENGTH  = 128;
    private static final String ALGORITHM_NAME = "PBKDF2WithHmacSHA1";

    private static HashMap<String, byte[]> userPasswordDB = new HashMap<>();
    static{
        try {
            userPasswordDB.put("user1", SecurityService.getHash("pass1"));
            userPasswordDB.put("user2", SecurityService.getHash("pass2"));
            userPasswordDB.put("user3", SecurityService.getHash("pass3"));
            userPasswordDB.put("user4", SecurityService.getHash("pass4"));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }


    public static byte[] getHash(String str) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
        byte[] salt = {56, 90, 77, 35, -48, -5, 114, -65, 16, -116, 52, -92, 36, 1, 120, -37};

        KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, ALGORITHM_STRENGTH, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM_NAME);

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return hash;
    }

    public static boolean authenticate(String login, String pass){
        if (!userPasswordDB.containsKey(login)){
            return false;
        }
        byte[] storedPassHash = userPasswordDB.get(login);

        try {
            byte[] passHash = getHash(pass);
            return Arrays.equals(passHash,storedPassHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String authenticateClient(BufferedWriter out, BufferedReader in){
        try {
            //writing to client
            DataSender.sendData(out, "input your login:");
            //waiting for date from client and read it after date coming
            String login = DataReader.readData(in).trim();

            //writing to client
            DataSender.sendData(out, "input your password:");
            //waiting for date from client and read it after date coming
            String pass = DataReader.readData(in).trim();

            if(authenticate(login, pass)){
                //writing to client
                DataSender.sendData(out, "welcome "+login);
                return login;
            }
            else{
                DataSender.sendData(out, "go home, "+login+"!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

