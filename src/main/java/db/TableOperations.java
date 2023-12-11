package db;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;

// Операции с таблицами
public interface TableOperations {
    void createTable() throws SQLException, CsvValidationException, IOException; // создание таблицы
    void createForeignKeys() throws SQLException; // создание связей между таблицами
}
