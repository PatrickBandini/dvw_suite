package model;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RSA {

    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");
    private static final BigInteger FOUR = new BigInteger("4");

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    public static String encrypt(String plainText, String publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        PublicKey pk = convertStringToPublicKey(publicKey);
        encryptCipher.init(Cipher.ENCRYPT_MODE, pk);
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));
        return new String(Base64.getEncoder().encodeToString(cipherText));
    }

    public static String decrypt(String cipherText, String privateKey) throws Exception {
        PrivateKey pk = convertStringToPrivateKey(privateKey);
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        Cipher decriptCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        decriptCipher.init(Cipher.DECRYPT_MODE, pk);
        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }

    private static PublicKey convertStringToPublicKey(String str) {
        try {
            byte[] publicBytes = Base64.getDecoder().decode(str);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static PrivateKey convertStringToPrivateKey(String str) {
        try {
            byte[] publicBytes = Base64.getDecoder().decode(str);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
