package gui.drawer;

import models.Student;
import models.StudentPerformance;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import gui.mapper.ChartDataMapper;
import org.jfree.data.category.CategoryDataset;

public class BarExerciseChartDrawer extends JFrame {
    public BarExerciseChartDrawer(List<StudentPerformance> studentPerformances) {
        setContentPane(createPracticeEachSection(studentPerformances));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(600, 300);
    }

    public static JPanel createPracticeEachSection(List<StudentPerformance> studentPerformances) {
        JFreeChart chart = createBarChart(ChartDataMapper.createAllExerciseScoreInEachSection(studentPerformances));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        return new ChartPanel(chart);
    }

    private static JFreeChart createBarChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                convertCP1251toUTF8("Средний балл ученика за упражнения в каждом разделе"),
                convertCP1251toUTF8("Раздел"),
                convertCP1251toUTF8("Баллы"),
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                false,
                false);

        chart.setBackgroundPaint(Color.white);

        return chart;
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
