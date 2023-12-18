package db.tables;

import db.DBConnection;
import db.TableOperations;
import models.Section;
import models.StudentPerformance;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    public List<Section> getSectionsFromFromDb() throws SQLException {
        Statement statement = DBConnection.getStatement();
        String query = "SELECT * FROM Section;";
        var queryRes = statement.executeQuery(query);
        List<Section> sections = new ArrayList<>();

        while (queryRes.next()){
            int sectionID = Integer.parseInt(queryRes.getString("sectionID"));
            String name = convertUtf8ToCp1251(queryRes.getString("name"));
            int maxActivityScore = Integer.parseInt(queryRes.getString("maxActivityScore"));
            int maxExerciseScore = Integer.parseInt(queryRes.getString("maxExerciseScore"));
            int maxPracticeScore = Integer.parseInt(queryRes.getString("maxPracticeScore"));
            int maxSeminarScore = Integer.parseInt(queryRes.getString("maxSeminarScore"));

            Section currentSection = new Section(sectionID, name, maxExerciseScore, maxPracticeScore, maxSeminarScore, maxActivityScore);
            sections.add(currentSection);
        }

        return sections;
    }
}
