package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.opencsv.exceptions.CsvValidationException;
import config.Config;
import db.tables.*;

public class DBConnection {
    private static final String dbDriver = "org.postgresql.Driver";
    private static final String dbURL = Config.dbUrl;
    private static final String dbUser = Config.dbUser;
    private static final String dbPassword = Config.dbPassword;

    // Таблицы СУБД
    static StudentTable studentTable;
    static StudentPerformanceTable studentPerformanceTable;
    static SectionTable sectionTable;
    static TaskTable taskTable;
    static StudentExercisesTable studentExercisesTable;
    static StudentPracticesTable studentPracticesTable;

    public DBConnection() throws SQLException, ClassNotFoundException, CsvValidationException, IOException {
        // Проверяем наличие JDBC драйвера для работы с БД
        Class.forName(dbDriver);

        studentTable = new StudentTable();
        studentPerformanceTable = new StudentPerformanceTable();
        sectionTable = new SectionTable();
        taskTable = new TaskTable();
        studentExercisesTable = new StudentExercisesTable();
        studentPracticesTable = new StudentPracticesTable();
    }

    // Получить новое соединение с БД
    public static Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword); //соединениесБД

        System.out.println("Соединение с СУБД выполнено.");

        return connection;
    }

    public static Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    public static StudentTable getStudentTable() {
        return studentTable;
    }

    public static StudentPerformanceTable getStudentPerformanceTable() {
        return studentPerformanceTable;
    }

    public static SectionTable getSectionTable() {
        return sectionTable;
    }

    public static TaskTable getTaskTable() {
        return taskTable;
    }

    public static StudentExercisesTable getStudentExercisesTable() {
        return studentExercisesTable;
    }

    public static StudentPracticesTable getStudentPracticesTable() {
        return studentPracticesTable;
    }

    // Создание всех таблиц и ключей между ними
    public void createTablesAndForeignKeys() throws SQLException, CsvValidationException, IOException {
        studentTable.createTable();
        studentPerformanceTable.createTable();
        sectionTable.createTable();
        taskTable.createTable();
        studentExercisesTable.createTable();
        studentPracticesTable.createTable();
        // Создание внешних ключей (связt` между таблицами)

    }
}
