package com.trantor.app.controller;

import com.trantor.app.encryptor.PgpEncryption;
import com.trantor.app.mapper.CsvToByteArrayConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class AppController {
    private final CsvToByteArrayConverter csvToPgpConverter;


    private final PgpEncryption pgpEncryption;

    @GetMapping
    public ResponseEntity<String> getFile() throws IOException {

	// Calling converter
        byte[] convertedCsv = csvToPgpConverter.csvToByteArrayConverter();

	// calling encryptor
        pgpEncryption.encrypt(convertedCsv);


        return new ResponseEntity<>("Done", HttpStatus.ACCEPTED);
    }

}
