package com.trantor.app.encryptor;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

@Component
public class PgpEncryption {


    private final PrivateKey privateKey;

    private final PublicKey publicKey;

    private static final String PADDING = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    private static final String SAVE_ENCRYPTED_FILE_PATH = "D:\\Encrypted Files\\encrypted.pgp";

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

            // encoding using encode method
            String encodedMessage = encode(encryptedBytes);


            // calling decrypt method
            decrypt(encodedMessage);


            // writing file in the local folder
            writeEncryptedFileAsPgp(encodedMessage);

        } catch (Exception e) {
            System.out.println("|| Error Occurred During Encryption ||");
        }
    }


        private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }




    public void decrypt(String encryptedMessage) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        byte[] encryptedBytes = decode(encryptedMessage);

        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);

        // Printing the decoded mssage
        System.out.println("DECRYPTED MESSAGE : ");

        ByteArrayInputStream bais = new ByteArrayInputStream(decryptedMessage);
        DataInputStream in = new DataInputStream(bais);
        while (in.available() > 0) {
            String element = in.readUTF();
            System.out.println(element);
        }
    }


    // Writing encrypted file to  local folder
    public void writeEncryptedFileAsPgp(String encryptedMessage) {

        try (FileWriter fileWriter = new FileWriter(SAVE_ENCRYPTED_FILE_PATH)) {
            fileWriter.write(encryptedMessage);

        } catch (Exception exception) {
            System.out.println("|| Error occurred while writing the file ||");
        }
    }

}
