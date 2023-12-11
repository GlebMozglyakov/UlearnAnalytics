package csvParser;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class Reader {
    private final String fileName;

    public Reader(String fileName) {
        File file = new File(fileName);

        if (file.exists()) {
            this.fileName = fileName;
        } else {
            throw new IllegalArgumentException();
        }
    }
    public CSVReader getReader() throws IOException {
        FileReader reader = new FileReader(fileName, Charset.forName("windows-1251"));
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        return new CSVReaderBuilder(reader).withCSVParser(parser).build();
    }
}
