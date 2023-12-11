package models;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private final int id;
    private final String name;
    private int maxExerciseScore;
    private int maxPracticeScore;
    private int maxSeminarScore;
    private int maxActivityScore;
    private final List<Task> tasks;

    public Section(int id, String name) {
        this.id = id;
        this.name = name;
        tasks = new ArrayList<>();
    }

    public Section(int id, String name, int maxExerciseScore, int maxPracticeScore, int maxSeminarScore, int maxActivityScore) {
        this.id = id;
        this.name = name;
        this.maxActivityScore = maxActivityScore;
        this.maxExerciseScore = maxExerciseScore;
        this.maxPracticeScore = maxPracticeScore;
        this.maxSeminarScore = maxSeminarScore;
        tasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxExerciseScore() {
        return maxExerciseScore;
    }

    public int getMaxPracticeScore() {
        return maxPracticeScore;
    }

    public int getMaxSeminarScore() {
        return maxSeminarScore;
    }

    public int getMaxActivityScore() {
        return maxActivityScore;
    }

    public List<Task> getTasks() {
        //List<Task> copyTasks = List.copyOf(tasks);
        //return copyTasks;
        return tasks;
    }

    public void setMaxExerciseScore(int maxExerciseScore) {
        this.maxExerciseScore = maxExerciseScore;
    }

    public void setMaxPracticeScore(int maxPracticeScore) {
        this.maxPracticeScore = maxPracticeScore;
    }

    public void setMaxSeminarScore(int maxSeminarScore) {
        this.maxSeminarScore = maxSeminarScore;
    }

    public void setMaxActivityScore(int maxActivityScore) {
        this.maxActivityScore = maxActivityScore;
    }

    public void addTasks(List<Task> addedTasks) {
        if (addedTasks != null && !addedTasks.isEmpty()) {
            tasks.addAll(addedTasks);
        } else {
            throw new IllegalArgumentException("Список не должен быть пустым");
        }
    }

    public void addTask(Task addedTask) {
        if (addedTask != null) {
            tasks.add(addedTask);
        } else {
            throw new IllegalArgumentException("Задача должна быть проинициализирована");
        }
    }
}