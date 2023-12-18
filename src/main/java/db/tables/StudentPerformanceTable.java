package db.tables;

import db.DBConnection;
import db.TableOperations;
import db.tables.BaseTable;
import models.Section;
import models.Student;
import models.StudentPerformance;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentPerformanceTable extends BaseTable implements TableOperations {
    public StudentPerformanceTable() throws SQLException {
        super("StudentPerformance");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS StudentPerformance(" +
                "ulearnID VARCHAR PRIMARY KEY," +
                "activityScores integer NOT NULL," +
                "exerciseScores integer NOT NULL," +
                "practiceScores integer NOT NULL," +
                "seminarScores  integer NOT NULL)", "Создана таблица " + tableName);
    }

    public void addStudentPerformance(HashMap<String, int[]> studentPerformance) throws SQLException {
        int count = 0;
        Statement statement = DBConnection.getStatement();

        for (String ulearnId: studentPerformance.keySet()) {
            var query = String.format(
                    "INSERT INTO StudentPerformance (UlearnID, activityScores, exerciseScores, practiceScores, seminarScores) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    ulearnId,
                    studentPerformance.get(ulearnId)[0],
                    studentPerformance.get(ulearnId)[1],
                    studentPerformance.get(ulearnId)[2],
                    studentPerformance.get(ulearnId)[3]);
            statement.addBatch(query);
            count++;
            if (count > 10) {
                statement.executeBatch();
                count = 0;
            }
        }
        statement.executeBatch();
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    public List<StudentPerformance> getStudentPerformanceFromDb(List<Section> allSections) throws SQLException {
        Statement statement = DBConnection.getStatement();
        String query = "SELECT * FROM StudentPerformance;";
        var queryRes = statement.executeQuery(query);

        List<StudentPerformance> studentsPerformances = new ArrayList<StudentPerformance>();

        while (queryRes.next()){
            String ulearnID = queryRes.getString("UlearnID");
            int activityScores = Integer.parseInt(queryRes.getString("activityScores"));
            int exerciseScores = Integer.parseInt(queryRes.getString("exerciseScores"));
            int practiceScores = Integer.parseInt(queryRes.getString("practiceScores"));
            int seminarScores = Integer.parseInt(queryRes.getString("seminarScores"));

            StudentPerformance currentStudentPerformance = new StudentPerformance(ulearnID, activityScores, exerciseScores, practiceScores, seminarScores, allSections);
            studentsPerformances.add(currentStudentPerformance);
        }

        return studentsPerformances;
    }
}
