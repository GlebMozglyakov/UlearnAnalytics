package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentPerformance {
    private final String ulearnId;
    private final int maxActivityScore;
    private final int maxExerciseScore;
    private final int maxPracticeScore;
    private final int maxSeminarScore;
    private int totalExerciseScore;
    private int totalPracticeScore;
    private int totalSeminarScore;
    private final List<Section> sections;

    public String getUlearnId() {
        return ulearnId;
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
        return sections;
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

    public StudentPerformance(String ulearnId, int maxActivityScore, int maxExerciseScore, int maxPracticeScore, int maxSeminarScore, List<Section> sections) {
        this.ulearnId = ulearnId;
        this.maxActivityScore = maxActivityScore;
        this.maxExerciseScore = maxExerciseScore;
        this.maxPracticeScore = maxPracticeScore;
        this.maxSeminarScore = maxSeminarScore;
        this.sections = sections;
    }
}