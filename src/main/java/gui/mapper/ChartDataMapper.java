package gui.mapper;

import jdk.jfr.Category;
import models.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

public class ChartDataMapper {
     public static PieDataset createStudentsByBdateDataset(List<Student> students) {
         Map<String, List<Student>> studentsByMonth = new HashMap<>();

         for (Student student : students) {
             String bdate = student.getBdate();
             if (bdate.equals("null") || bdate.isEmpty()) {
                 continue;
             }
             String month = student.getBdate().split("\\.")[1];

             if (!studentsByMonth.containsKey(month)) {
                 studentsByMonth.put(month, new ArrayList<>());
             }
             studentsByMonth.get(month).add(student);
         }

         Map<String, Integer> studentsCountByMontn = new HashMap<>();

         for (String key: studentsByMonth.keySet()) {
                 switch (key) {
                     case "1":
                         studentsCountByMontn.put(convertCP1251toUTF8("Январь"), studentsByMonth.get(key).size());
                         break;
                     case "2":
                         studentsCountByMontn.put(convertCP1251toUTF8("Февраль"), studentsByMonth.get(key).size());
                         break;
                     case "3":
                         studentsCountByMontn.put(convertCP1251toUTF8("Март"), studentsByMonth.get(key).size());
                         break;
                     case "4":
                         studentsCountByMontn.put(convertCP1251toUTF8("Апрель"), studentsByMonth.get(key).size());
                         break;
                     case "5":
                         studentsCountByMontn.put(convertCP1251toUTF8("Май"), studentsByMonth.get(key).size());
                         break;
                     case "6":
                         studentsCountByMontn.put(convertCP1251toUTF8("июнь"), studentsByMonth.get(key).size());
                         break;
                     case "7":
                         studentsCountByMontn.put(convertCP1251toUTF8("июль"), studentsByMonth.get(key).size());
                         break;
                     case "8":
                         studentsCountByMontn.put(convertCP1251toUTF8("Август"), studentsByMonth.get(key).size());
                         break;
                     case "9":
                         studentsCountByMontn.put(convertCP1251toUTF8("Сентябрь"), studentsByMonth.get(key).size());
                         break;
                     case "10":
                         studentsCountByMontn.put(convertCP1251toUTF8("Октябрь"), studentsByMonth.get(key).size());
                         break;
                     case "11":
                         studentsCountByMontn.put(convertCP1251toUTF8("Ноябрь"), studentsByMonth.get(key).size());
                         break;
                     case "12":
                         studentsCountByMontn.put(convertCP1251toUTF8("Декабрь"), studentsByMonth.get(key).size());
                         break;
                 }
             }

             DefaultPieDataset dataset = new DefaultPieDataset();

             studentsCountByMontn.forEach(dataset::setValue);

             return dataset;
     }

     public static DefaultCategoryDataset createStudentsByCityDataset(List<Student> students) {
         var studentsCountByCites = students.stream()
                 .collect(
                         Collectors.groupingBy(Student::getCity,
                                                HashMap::new,
                                                Collectors.counting())
                 );


         DefaultCategoryDataset dataset = new DefaultCategoryDataset();

         for (String city: studentsCountByCites.keySet()) {
             var studentsCountByCity = studentsCountByCites.get(city);
             if (!city.equals("null") && studentsCountByCity > 1) {
                 dataset.setValue(studentsCountByCity, "Количество студентов", convertCP1251toUTF8(city));
             }
         }

         return dataset;
     }

     public static DefaultCategoryDataset createAllPracticeScoreInEachSection(List<StudentPerformance> studentPerformances) {
         Map<String, Integer> allScoresInEachSection = new TreeMap<>();

         for (StudentPerformance studentPerformance: studentPerformances) {
             List<Section> currentStudentsSections = studentPerformance.getSections();
             for (Section section: currentStudentsSections) {
                 List<Task> currentStudentsTasks = section.getTasks();
                 for (Task task: currentStudentsTasks) {
                     if (task.getTaskType() == TaskType.Practice) {
                         if (!allScoresInEachSection.containsKey(task.getSectionName())) {
                             allScoresInEachSection.put(task.getSectionName(), task.getStudentScore());
                         } else {
                             int newScore = allScoresInEachSection.get(task.getSectionName()) + task.getStudentScore();
                             allScoresInEachSection.put(task.getSectionName(), newScore);
                         }
                     }
                 }
             }
         }

         // Создаем TreeMap и передаем в конструкторе HashMap для сортировки по значениям
         TreeMap<String, Integer> sortedMap = new TreeMap<>((o1, o2) -> {
             int result = allScoresInEachSection.get(o2).compareTo(allScoresInEachSection.get(o1));
             if (result == 0) {
                 // Если значения равны, то сортируем по ключу
                 return o2.compareTo(o1);
             }
             return result;
         });
         // Переносим все элементы из HashMap в TreeMap
         sortedMap.putAll(allScoresInEachSection);

         DefaultCategoryDataset dataset = new DefaultCategoryDataset();

         for (String sectionName: sortedMap.keySet()) {
             dataset.setValue(sortedMap.get(sectionName) / 1030, "Количество баллов за практики в каждой теме", convertCP1251toUTF8(sectionName));
         }

         return dataset;
     }

    public static DefaultCategoryDataset createAllExerciseScoreInEachSection(List<StudentPerformance> studentPerformances) {
        HashMap<String, Integer> allScoresInEachSection = new HashMap<String, Integer>();

        for (StudentPerformance studentPerformance: studentPerformances) {
            List<Section> currentStudentsSections = studentPerformance.getSections();
            for (Section section: currentStudentsSections) {
                List<Task> currentStudentsTasks = section.getTasks();
                for (Task task: currentStudentsTasks) {
                    if (task.getTaskType() == TaskType.Exercise) {
                        if (!allScoresInEachSection.containsKey(task.getSectionName())) {
                            allScoresInEachSection.put(task.getSectionName(), task.getStudentScore());
                        } else {
                            int newScore = allScoresInEachSection.get(task.getSectionName()) + task.getStudentScore();
                            allScoresInEachSection.put(task.getSectionName(), newScore);
                        }
                    }
                }
            }
        }

        // Создаем TreeMap и передаем в конструкторе HashMap для сортировки по значениям
        TreeMap<String, Integer> sortedMap = new TreeMap<>((o1, o2) -> {
            int result = allScoresInEachSection.get(o2).compareTo(allScoresInEachSection.get(o1));
            if (result == 0) {
                // Если значения равны, то сортируем по ключу
                return o2.compareTo(o1);
            }
            return result;
        });
        // Переносим все элементы из HashMap в TreeMap
        sortedMap.putAll(allScoresInEachSection);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String sectionName: sortedMap.keySet()) {
            dataset.setValue(sortedMap.get(sectionName) / 1030, "Количество баллов за упражнения в каждой теме", convertCP1251toUTF8(sectionName));
        }

        return dataset;
    }

    public static DefaultCategoryDataset createTopBestStudents(List<StudentPerformance> studentPerformances) {
        Map<String, Integer> bestStudents = new TreeMap<>();

        for (StudentPerformance studentPerformance: studentPerformances) {
            Integer maxCourseScore = studentPerformance.getMaxExerciseScore() +
                    studentPerformance.getMaxPracticeScore() +
                    studentPerformance.getMaxSeminarScore() +
                    studentPerformance.getMaxActivityScore();

            bestStudents.put(studentPerformance.getUlearnId(), maxCourseScore);
        }

        // Создаем TreeMap и передаем в конструкторе HashMap для сортировки по значениям
        TreeMap<String, Integer> sortedMap = new TreeMap<>((o1, o2) -> {
            int result = bestStudents.get(o2).compareTo(bestStudents.get(o1));
            if (result == 0) {
                // Если значения равны, то сортируем по ключу
                return o2.compareTo(o1);
            }
            return result;
        });
        // Переносим все элементы из HashMap в TreeMap
        sortedMap.putAll(bestStudents);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int count = 0;

        for (String ulearnId: sortedMap.keySet()) {
            count++;
            dataset.setValue(sortedMap.get(ulearnId), "Лучшие студенты курса", ulearnId);
            if (count > 10) {
                break;
            }
        }

        return dataset;
    }

    private static String convertCP1251toUTF8(String cp1251String) {
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
