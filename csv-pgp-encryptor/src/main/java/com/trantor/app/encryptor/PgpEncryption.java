package com.trantor.app.encryptor;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

@Service
public class PgpEncryption {


//    private PrivateKey privateKey;

    private final PublicKey publicKey;

    private static final String PADDING = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    public PgpEncryption() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2820);
        KeyPair keyPair = keyGenerator.generateKeyPair();

//        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();

    }


    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }


    public void encrypt(byte[] messageToBytes) {


        try {
            Cipher cipher = Cipher.getInstance(PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(messageToBytes);
            String encodedMessage = encode(encryptedBytes);
            writeEncryptedFileAsPgp(encodedMessage);
        } catch (Exception e) {
            System.out.println("|| Error Occurred During Encryption ||");
        }
    }

    public void writeEncryptedFileAsPgp(String encryptedMessage) {

        try (FileWriter fileWriter = new FileWriter("D:\\encryptedOne.pgp")) {
            fileWriter.write(encryptedMessage);
        } catch (Exception exception) {
            System.out.println("|| Error occurred while writing the file ||");
        }
    }

}
