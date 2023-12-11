import com.opencsv.exceptions.CsvValidationException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import csvParser.Parser;
import db.DBConnection;
import models.Section;
import models.Student;
import models.Task;
import vkAPI.VkParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException, SQLException, CsvValidationException, ClientException, ApiException, URISyntaxException {
        Parser parser = new Parser();
        VkParser vk = new VkParser();

        List<Student> students = parser.parseAndGetStudents();
        //vk.addVkDataToStudents(students);
        HashMap<String, int[]> studentPerformance = parser.parseAndGetStudentPerformance();
        List<Section> sections = parser.parseAndGetSections();

        var tasks = parser.parseUsersTasksScores();
        System.out.println(tasks);

        try{
            DBConnection dbConnection = new DBConnection();
            dbConnection.createTablesAndForeignKeys();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC драйвер для СУБД не найден");
        }

        //DBConnection.getStudentTable().addStudents(students);
        //DBConnection.getStudentPerformanceTable().addStudentPerformance(studentPerformance);
        //DBConnection.getSectionTable().addSections(sections);
        //DBConnection.getTaskTable().addTasks(sections);
        //DBConnection.getStudentExercisesTable().addStudentScores();
        DBConnection.getStudentPracticesTable().addStudentScores();
    }
}