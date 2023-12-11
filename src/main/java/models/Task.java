package models;

import java.util.UUID;

public class Task {
    private final int id;
    private final String name;
    private int sectionId;
    private String sectionName;
    private final TaskType taskType;
    private int maxScore;;
    private int studentScore;

    public Task(int id, String name, TaskType taskType, int sectionId, String sectionName) {
        this.id = id;
        this.name = name;
        this.taskType = taskType;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
    }

    public Task(int id, String name, int sectionId, String sectionName, TaskType taskType, int maxScore, int studentScore) {
        this.id = id;
        this.name = name;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.taskType = taskType;
        this.maxScore = maxScore;
        this.studentScore = studentScore;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(int score) {
        if (score >= 0 && score <= maxScore) {
            studentScore = score;
        } else {
            throw new IllegalArgumentException("Балл должен быть больше 0 и меньше максимального");
        }
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}