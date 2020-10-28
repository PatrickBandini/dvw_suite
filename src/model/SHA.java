package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

    public static String SHA1(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] result = md.digest(message.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
