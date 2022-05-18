package com.trantor.app.controller;

import com.trantor.app.encryptor.EncryptorB;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class AppController {

    private final EncryptorB encryptorB;

    // client public key
//    private static final String PUBLIC_KEY_FILE = "D:\\Development\\EncryptorB\\NASA FCU.asc";

    // test public key
    private static final String PUBLIC_KEY_FILE = "D:\\Development\\EncryptorB\\IITCorporation_1024.asc";

    // txt file
    //    private static final String INPUT_FILE_NAME = "D:\\Development\\EncryptorB\\textToEncrypt.txt";

    // csv file
    private static final String INPUT_FILE_NAME = "D:\\Development\\EncryptorB\\textFileToEncrypt.csv";
    private static final String OUTPUT_FILE_NAME = "D:\\Development\\EncryptorB\\EncryptedFile.bpg";

    @GetMapping("/encrypt")
    public ResponseEntity<String> getFile() throws Exception {


        encryptorB.encrypt(PUBLIC_KEY_FILE, INPUT_FILE_NAME, OUTPUT_FILE_NAME);

        System.out.println("File Encrypted successfully");


        return new ResponseEntity<>("Done", HttpStatus.ACCEPTED);
    }

}
