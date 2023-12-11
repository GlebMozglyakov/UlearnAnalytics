package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentPerformance {
    private final UUID id;
    private final UUID studentId;
    private final int maxExerciseScore;
    private final int maxPracticeScore;
    private final int maxSeminarScore;
    private int totalExerciseScore;
    private int totalPracticeScore;
    private int totalSeminarScore;
    private final List<Section> sections;

    public StudentPerformance(UUID id,
                              UUID studentId,
                              int maxExerciseScore,
                              int maxPracticeScore,
                              int maxSeminarScore,
                              int totalExerciseScore,
                              int totalPracticeScore,
                              int totalSeminarScore) {
        this.id = id;
        this.studentId = studentId;
        this.maxExerciseScore = maxExerciseScore;
        this.maxPracticeScore = maxPracticeScore;
        this.maxSeminarScore = maxSeminarScore;
        this.totalExerciseScore = totalExerciseScore;
        this.totalPracticeScore = totalPracticeScore;
        this.totalSeminarScore = totalSeminarScore;
        sections = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public UUID getStudentId() {
        return studentId;
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

    public int getTotalExerciseScore() {
        return totalExerciseScore;
    }

    public int getTotalPracticeScore() {
        return totalPracticeScore;
    }

    public int getTotalSeminarScore() {
        return totalSeminarScore;
    }

    public void setTotalExerciseScore(int score) {
        if (score >= 0 && score <= maxExerciseScore) {
            totalExerciseScore = score;
        } else {
            throw new IllegalArgumentException("Баллы должны быть не меньше 0 и не больше максимального значения");
        }
    }

    public void setTotalPracticeScore(int score) {
        if (score >= 0 && score <= maxExerciseScore) {
            totalPracticeScore = score;
        } else {
            throw new IllegalArgumentException("Баллы должны быть не меньше 0 и не больше максимального значения");
        }
    }

    public void setTotalSeminarScore(int score) {
        if (score >= 0 && score <= maxExerciseScore) {
            totalSeminarScore = score;
        } else {
            throw new IllegalArgumentException("Баллы должны быть не меньше 0 и не больше максимального значения");
        }
    }

    public List<Section> getSections() {
        List<Section> copySections = List.copyOf(sections);
        return copySections;
    }

    public void addSections(List<Section> addedSections) {
        if (addedSections != null && !addedSections.isEmpty()) {
            sections.addAll(addedSections);
        } else {
            throw new IllegalArgumentException("Список должен быть проинициализирован и не должен быть пустым");
        }
    }

    public void addSection(Section addedSection) {
        if (addedSection != null) {
            sections.add(addedSection);
        } else {
            throw new IllegalArgumentException("Раздел должен быть проинициализирован");
        }
    }
}