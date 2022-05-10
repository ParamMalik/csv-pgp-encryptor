package com.trantor.app.controller;

import com.trantor.app.mapper.CsvToPgpConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class AppController {
    private final CsvToPgpConverter csvToPgpConverter;

    @GetMapping
    public ResponseEntity<String> getFile(@PathVariable String fileName) throws IOException {
        csvToPgpConverter.coverter();
        return new ResponseEntity<>("Done", HttpStatus.ACCEPTED);
    }

}
