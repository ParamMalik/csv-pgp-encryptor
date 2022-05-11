package com.trantor.app.controller;

import com.trantor.app.encryptor.PgpEncryption;
import com.trantor.app.mapper.CsvToByteArrayConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class AppController {
    private final CsvToByteArrayConverter csvToPgpConverter;


    private final PgpEncryption pgpEncryption;

    @GetMapping
    public ResponseEntity<String> getFile(){
        byte[] convertedCsv = csvToPgpConverter.csvToByteArrayConverter();
        pgpEncryption.encrypt(convertedCsv);


        return new ResponseEntity<>("Done", HttpStatus.ACCEPTED);
    }

}
