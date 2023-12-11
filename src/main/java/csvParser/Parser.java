package csvParser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import models.Section;
import models.Student;
import models.Task;
import models.TaskType;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Parser {
    private HashMap<String, Integer> indexes = new HashMap<>();

    public List<Student> parseAndGetStudents() throws IOException, CsvValidationException {
        CSVReader reader = new Reader("basicprogramming.csv").getReader();
        reader.skip(3);

        ArrayList<Student> students = new ArrayList<>();

        String[] line;
        while ((line = reader.readNext()) != null) {
            Student student = parseStudent(line);
            if (student.getName() != null && student.getGroup() != null) {
                students.add(student);
            }
        }

        return students;
    }

    public HashMap<String, int[]> parseAndGetStudentPerformance() throws IOException, CsvValidationException {
        CSVReader reader = new Reader("basicprogramming.csv").getReader();
        reader.skip(3);
        HashMap<String, int[]> studentPerformance = new HashMap<>();

        String[] line;
        while ((line = reader.readNext()) != null) {
            String ulearnId = line[1];
            int maxActivityScore = Integer.parseInt(line[4]);
            int maxExerciseScore = Integer.parseInt(line[5]);
            int maxPracticeScore = Integer.parseInt(line[6]);
            int maxSeminarScore = Integer.parseInt(line[7]);

            studentPerformance.put(ulearnId, new int[] { maxActivityScore, maxExerciseScore, maxPracticeScore, maxSeminarScore});
        }

        return studentPerformance;
    }

    public List<Section> parseAndGetSections() throws IOException, CsvValidationException {
        CSVReader reader = new Reader("basicprogramming.csv").getReader();
        List<String> line = Arrays.stream(reader.readNext()).skip(10).toList();
        List<Section> sections = new ArrayList<>();

        int index = 1;

        for (String obj: line) {
            if (!obj.isEmpty()) {
                Section section = new Section(index, obj);
                index++;
                sections.add(section);
            }
        }

        parseTasks(sections);
        parseScoreExercise(sections);
        return sections;
    }

    private void parseTasks(List<Section> sections) throws IOException, CsvValidationException {
        CSVReader reader = new Reader("basicprogramming.csv").getReader();
        reader.skip(1);
        List<String> line = Arrays.stream(reader.readNext()).skip(10).toList();
        List<Task> tasks = new ArrayList<>();

        int taskId = 0;
        int index = 0;
        int sectionId = 0;
        String act = "Акт";
        String ex = "Упр";
        String sem = "Сем";
        String dz = "ДЗ";
        String startEx = "Упр: ";
        String statePr = "ДЗ: ";

        for (String obj: line) {
            if (obj.equals(act)) {
                sectionId++;
                indexes.put(sections.get(sectionId - 1).getName() + act, index);
            }
            else if (obj.equals(ex)) {
                indexes.put(sections.get(sectionId - 1).getName() + ex, index);
            }
            else if (obj.equals(sem)) {
                indexes.put(sections.get(sectionId - 1).getName() + sem, index);
            }
            else if (obj.equals(dz)) {
                indexes.put(sections.get(sectionId - 1).getName() + dz, index);
            }
            else if (obj.startsWith(startEx)) {
                taskId++;
                String taskName = obj;
                String sectionName = sections.get(sectionId - 1).getName();
                Task task = new Task(taskId, taskName, TaskType.Exercise, sectionId, sectionName);
                indexes.put(sections.get(sectionId - 1).getName() + taskName, index);
                sections.get(sectionId - 1).addTask(task);
            }
            else if (obj.startsWith(statePr)) {
                taskId++;
                String taskName = obj;
                String sectionName = sections.get(sectionId - 1).getName();
                Task task = new Task(taskId, taskName, TaskType.Practice, sectionId, sectionName);
                indexes.put(sections.get(sectionId - 1).getName() + taskName, index);
                sections.get(sectionId - 1).addTask(task);
            }
            index++;
        }
    }

    public List<Task> getAllTasks() throws CsvValidationException, IOException {
        List<Section> sections = parseAndGetSections();
        List<Task> tasks = new ArrayList<>();

        for (Section section: sections) {
            tasks.addAll(section.getTasks());
        }

        return tasks;
    }

    private void parseScoreExercise(List<Section> sections) throws IOException, CsvValidationException {
        CSVReader reader = new Reader("basicprogramming.csv").getReader();
        reader.skip(2);
        List<String> line = Arrays.stream(reader.readNext()).skip(10).toList();

        for (Section section: sections) {
            String actKey = section.getName() + "Акт";
            String exKey = section.getName() + "Упр";
            String prKey = section.getName() + "ДЗ";
            String semKey = section.getName() + "Сем";

            if (indexes.containsKey(exKey)) {
                section.setMaxExerciseScore(Integer.parseInt(line.get(indexes.get(exKey))));
            }
            if (indexes.containsKey(prKey)) {
                section.setMaxPracticeScore(Integer.parseInt(line.get(indexes.get(prKey))));
            }
            section.setMaxActivityScore(Integer.parseInt(line.get(indexes.get(actKey))));
            section.setMaxSeminarScore(Integer.parseInt(line.get(indexes.get(semKey))));

            for (Task task: section.getTasks()) {
                String key = section.getName() + task.getName();
                //keys.add(key);
                int neededIndex = indexes.get(key);
                task.setMaxScore(Integer.parseInt(line.get(neededIndex)));
            }
        }
    }

    private Student parseStudent(String[] line) {
        var studentData = line;
        var nameData = studentData[0].split("\\s+");

        Student student = new Student();
        if (nameData.length == 2) {
            student.setName(nameData[1]);
            student.setSurname(nameData[0]);
        }

        student.setUlearnId(studentData[1]);
        student.setEmail(studentData[2]);
        student.setGroup(studentData[3]);

        return student;
    }





    public HashMap<String, HashMap<Task, Integer>> parseUsersTasksScores() throws CsvValidationException, IOException {
        var colIndexes = getTasksColumns();
        return getUidScores(colIndexes);
    }

    private HashMap<String, HashMap<Task, Integer>> getUidScores(HashMap<Task, Integer> elemsIndexes) throws IOException, CsvValidationException {
        CSVReader reader = new Reader("basicprogramming.csv").getReader();
        reader.skip(3);
        var uidIndex = getUidColumn();
        var result = new HashMap<String, HashMap<Task, Integer>>();
        String[] row;
        while ((row = reader.readNext()) != null) {
            var uid = row[uidIndex];
            var scores = new HashMap<Task, Integer>();
            for (var elem: elemsIndexes.keySet()) {
                var column = elemsIndexes.get(elem);
                if (column == -1) {
                    System.out.println("stop!");
                }
                scores.put(elem, Integer.valueOf(row[column]));
            }
            result.put(uid, scores);
        }
        return result;
    }

    private HashMap<Task, Integer> getTasksColumns() throws CsvValidationException, IOException {
        List<Section> sections = parseAndGetSections();
        String[] headerRow = getHeaders();
        var indexes = new HashMap<Task, Integer>();
        for (Section section: sections) {
            List<Task> tasks = section.getTasks();
            for (var task : tasks) {
                String newStr = convertCP1251toUTF8(task.getName());
                indexes.put(task, ArrayUtils.indexOf(headerRow, newStr));
                indexes.put(task, ArrayUtils.indexOf(headerRow, convertCP1251toUTF8(task.getName())));
            }
        }
        return indexes;
    }

    private int getUidColumn() throws CsvValidationException, IOException {
        return ArrayUtils.indexOf(getHeaders(),"Ulearn id");
    }

    private String[] getHeaders() throws IOException, CsvValidationException {
        CSVReader reader = new Reader("basicprogramming.csv").getReader();
        reader.skip(1);
        String[] headers = reader.readNext();
        for (int i = 0; i < headers.length; i++) {
            headers[i] = convertCP1251toUTF8(headers[i]);
        }

        return headers;
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
}
