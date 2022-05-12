package com.trantor.app.encryptor;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.FileWriter;
import java.security.*;
import java.util.Base64;

@Component
public class PgpEncryption {


    private final PrivateKey privateKey;

    private final PublicKey publicKey;

    private static final String PADDING = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    public PgpEncryption() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(4096);
        KeyPair keyPair = keyGenerator.generateKeyPair();

        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();

    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public void encrypt(byte[] messageInBytes) {


        try {
            Cipher cipher = Cipher.getInstance(PADDING);

            // encrypting with public key
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedBytes = cipher.doFinal(messageInBytes);

            // encoding
            String encodedMessage = encode(encryptedBytes);

            // writing file in the local folder
            writeEncryptedFileAsPgp(encodedMessage);

        } catch (Exception e) {
            System.out.println("|| Error Occurred During Encryption ||");
        }
    }


    public void writeEncryptedFileAsPgp(String encryptedMessage) {

        try (FileWriter fileWriter = new FileWriter("D:\\Encrypted Files\\encrypted.pgp")) {
            fileWriter.write(encryptedMessage);

        } catch (Exception exception) {
            System.out.println("|| Error occurred while writing the file ||");
        }
    }

}
