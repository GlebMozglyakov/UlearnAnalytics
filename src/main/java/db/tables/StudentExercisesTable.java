package db.tables;

import com.opencsv.exceptions.CsvValidationException;
import csvParser.Parser;
import db.DBConnection;
import db.TableOperations;
import db.tables.BaseTable;
import models.Student;
import models.Task;
import models.TaskType;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class StudentExercisesTable extends BaseTable implements TableOperations {
    private Parser parser = new Parser();

    public StudentExercisesTable() throws SQLException, CsvValidationException, IOException {
        super("StudentExercises");
    }

    @Override
    public void createTable() throws SQLException, CsvValidationException, IOException {
        List<Task> tasks = parser.getAllTasks();
        var ids = tasks.stream().filter(task -> task.getTaskType() == TaskType.Exercise).map(Task::getId).collect(Collectors.toList());

        var builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS StudentExercises(UlearnID VARCHAR PRIMARY KEY");
        for (var id : ids) {
            builder.append(String.format(",%s %s", "id" + id, "smallint NOT NULL"));
        }
        builder.append(")");
        String sqlQuery = builder.toString();

        super.executeSqlStatement(sqlQuery, "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    public void addStudentScores() throws CsvValidationException, IOException, SQLException {
        var studentScores = parser.parseUsersTasksScores();
        var paramsBuilder = new StringBuilder();
        var valuesBuilder = new StringBuilder();
        int count = 0;
        Statement statement = DBConnection.getStatement();

        for (var uid : studentScores.keySet()) {
            paramsBuilder.setLength(0);
            valuesBuilder.setLength(0);
            var scores = studentScores.get(uid);

            for (var elem : scores.keySet()) {
                if (elem.getTaskType() != TaskType.Exercise) {
                    continue;
                }

                var score = scores.get(elem);
                paramsBuilder.append(String.format("id%s, ", elem.getId()));
                valuesBuilder.append(String.format("%d, ", score));
            }

            var query = String.format("INSERT INTO StudentExercises (UlearnID, %s) VALUES ('%s', %s);",
                    paramsBuilder.substring(0, paramsBuilder.length() - 2),
                    uid,
                    valuesBuilder.substring(0, valuesBuilder.length() - 2));

            statement.addBatch(query);
            count++;

            if (count > 100) {
                statement.executeBatch();
                count = 0;
            }
        }
        statement.executeBatch();
    }
}
