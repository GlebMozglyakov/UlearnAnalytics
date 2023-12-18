package db.tables;

import db.DBConnection;
import db.TableOperations;
import db.tables.BaseTable;
import models.Student;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentTable extends BaseTable implements TableOperations {
    public StudentTable() throws SQLException {
        super("Student");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS Student(" +
                "ulearnID varchar(140) PRIMARY KEY," +
                "name varchar(50) NOT NULL," +
                "surname varchar(70) NOT NULL," +
                "email varchar(90) NOT NULL,"+
                "studentGroup varchar(100) NOT NULL," +
                "bdate varchar(20)," +
                "city varchar(50))", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    public void addStudents(List<Student> students) throws SQLException {
        int count = 0;
        Statement statement = DBConnection.getStatement();

        for (Student student: students) {
            var query = String.format(
                    "INSERT INTO Student (UlearnID, name, surname, email, studentGroup, bdate, city) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    student.getUlearnId(),
                    convertCP1251toUTF8(student.getName()),
                    convertCP1251toUTF8(student.getSurname()),
                    student.getEmail(),
                    convertCP1251toUTF8(student.getGroup()),
                    student.getBdate(),
                    convertCP1251toUTF8(student.getCity()));
            statement.addBatch(query);
            count++;
            if (count > 100) {
                statement.executeBatch();
                count = 0;
            }
        }
        statement.executeBatch();
    }

    public List<Student> getStudentsFromDb() throws SQLException {
        Statement statement = DBConnection.getStatement();
        String query = "SELECT * FROM Student;";
        var queryRes = statement.executeQuery(query);
        List<Student> students = new ArrayList<Student>();

        while (queryRes.next()){
            String ulearnID = queryRes.getString("UlearnID");
            String name = convertUtf8ToCp1251(queryRes.getString("name"));
            String surname = convertUtf8ToCp1251(queryRes.getString("surname"));
            String email = queryRes.getString("email");
            String group = convertUtf8ToCp1251(queryRes.getString("studentGroup"));
            String bdate = queryRes.getString("bdate");
            String city = convertUtf8ToCp1251(queryRes.getString("city"));

            students.add(new Student(ulearnID, name, surname, email, group, bdate, city));
        }
        return students;
    }
}
