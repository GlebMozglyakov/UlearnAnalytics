package db.tables;

import db.DBConnection;
import db.TableOperations;
import models.Section;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SectionTable extends BaseTable implements TableOperations {
    public SectionTable() throws SQLException {
        super("Section");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS Section(" +
                "sectionID integer PRIMARY KEY," +
                "name varchar(90) NOT NULL," +
                "maxActivityScore integer NOT NULL," +
                "maxExerciseScore integer NOT NULL," +
                "maxPracticeScore integer NOT NULL,"+
                "maxSeminarScore integer NOT NULL)", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    public void addSections(List<Section> sections) throws SQLException {
        Statement statement = DBConnection.getStatement();

        for (Section section: sections) {
            var query = String.format(
                    "INSERT INTO Section (sectionID, name, maxActivityScore, maxExerciseScore, maxPracticeScore, maxSeminarScore) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                    section.getId(),
                    convertCP1251toUTF8(section.getName()),
                    section.getMaxActivityScore(),
                    section.getMaxExerciseScore(),
                    section.getMaxPracticeScore(),
                    section.getMaxSeminarScore());
            statement.addBatch(query);
            statement.executeBatch();
        }
        statement.executeBatch();
    }
}
