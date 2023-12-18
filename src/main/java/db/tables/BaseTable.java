package db.tables;

import db.DBConnection;

import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.sql.*;


// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public class BaseTable implements Closeable {
    // JDBC-соединение для работы с таблицей
    Connection connection;
    String tableName;

    // Для реальной таблицы передадим в конструктор её имя
    BaseTable(String tableName) throws SQLException {
        this.tableName = tableName;
        this.connection = DBConnection.getConnection(); // Установим соединение с СУБД для дальнейшей работы
    }

    // Закрытие
    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия SQL соединения!");
        }
    }

    // Выполнить SQL команду без параметров в СУБД, по завершению выдать сообщение в консоль
    void executeSqlStatement(String sql, String description) throws SQLException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        statement.execute(sql); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
        if (description != null)
            System.out.println(description);
    };

    void executeSqlStatement(String sql) throws SQLException {
        executeSqlStatement(sql, null);
    };


    // Активизация соединения с СУБД, если оно не активно.
    void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DBConnection.getConnection();
        }
    }

    public static String convertCP1251toUTF8(String cp1251String) {
        if (cp1251String == null) {
            return null;
        }
        try {
            byte[] bytes = cp1251String.getBytes("Cp1251");
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertUtf8ToCp1251(String utf8String) {
        try {
            byte[] utf8Bytes = utf8String.getBytes("UTF-8");
            return new String(utf8Bytes, "CP1251");
        } catch (UnsupportedEncodingException e) {
            // обработка исключения
            e.printStackTrace();
            return null;
        }
    }
}
