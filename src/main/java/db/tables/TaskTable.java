package db.tables;

import db.DBConnection;
import db.TableOperations;
import models.Section;
import models.Task;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TaskTable extends BaseTable implements TableOperations {
    public TaskTable() throws SQLException {
        super("Task");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS Task(" +
                "taskID integer PRIMARY KEY," +
                "name varchar(90) NOT NULL," +
                "sectionID integer NOT NULL," +
                "taskTypeID integer NOT NULL," +
                "maxTaskScore integer NOT NULL)", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    public void addTasks(List<Section> sections) throws SQLException {
        Statement statement = DBConnection.getStatement();

        for (Section section: sections) {
            List<Task> tasks = section.getTasks();
            for (Task task: tasks) {
                var query = String.format(
                        "INSERT INTO Task (taskID, name, sectionID, taskTypeID, maxTaskScore) VALUES ('%s', '%s', '%s', '%s', '%s')",
                        task.getId(),
                        convertCP1251toUTF8(task.getName()).substring(4).trim(),
                        section.getId(),
                        task.getTaskType().ordinal() +1,
                        task.getMaxScore());
                statement.addBatch(query);
                statement.executeBatch();
            }
        }
        statement.executeBatch();
    }
}
