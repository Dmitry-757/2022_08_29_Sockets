package org.dng.Server.Security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class SecurityService {
    private static final int ALGORITHM_STRENGTH  = 65536;
    private static final int KEY_LENGTH  = 128;
    private static final String ALGORITHM_NAME = "PBKDF2WithHmacSHA1";


    public static byte[] getHash(String str) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, ALGORITHM_STRENGTH, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM_NAME);

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return hash;
    }
}
