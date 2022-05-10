package com.trantor.app.mapper;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.trantor.app.model.BookModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class CsvToPgpConverter {
    private static final String FILEPATH = "C:\\Users\\paramjeet.malik\\Desktop\\csv\\example.csv";

    @Bean
    public void coverter() throws IOException {

        ArrayList<BookModel> bookList = new ArrayList<>();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema bookModelSchema = csvMapper.schemaFor(BookModel.class);


        ObjectReader objectReader = csvMapper.readerFor(BookModel.class).with(bookModelSchema);

        try (FileReader fileReader = new FileReader(FILEPATH);) {
            MappingIterator<BookModel> iterator = objectReader.readValues(fileReader);
            while (iterator.hasNext()) {
                bookList.add(iterator.next());
            }
        }

        // write to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        for (BookModel element : bookList) {
            out.writeUTF(element.toString());
        }
        byte[] bytes = baos.toByteArray();



        for (BookModel bookModel : bookList) {
            System.out.println(bookModel.toString());
//            System.out.print(bookModel.getTitle() + " " + bookModel.getAuthor() + " " + bookModel.getPrice());
            System.out.println();

        }


    }
}
