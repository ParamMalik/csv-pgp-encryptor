package com.trantor.app.mapper;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.trantor.app.model.BookModel;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CsvToByteArrayConverter {
    private static final String FILEPATH = "C:\\Users\\paramjeet.malik\\Desktop\\csv\\example.csv";

    public byte[] csvToByteArrayConverter() {

        // write to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream csvToByteStream = new DataOutputStream(byteArrayOutputStream);


        CsvMapper csvMapper = new CsvMapper();
        CsvSchema bookModelSchema = csvMapper.schemaFor(BookModel.class);


        ObjectReader objectReader = csvMapper.readerFor(BookModel.class).with(bookModelSchema);

        try (FileReader fileReader = new FileReader(FILEPATH)) {
            MappingIterator<BookModel> iterator = objectReader.readValues(fileReader);
            while (iterator.hasNext()) {
                csvToByteStream.writeUTF(iterator.next().toString());
            }
        } catch (Exception exception) {
            System.out.println("|| Unable to process the CSV file ||");
        }

        return byteArrayOutputStream.toByteArray();

    }
}
