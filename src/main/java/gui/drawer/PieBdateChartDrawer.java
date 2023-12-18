package gui.drawer;

import gui.mapper.ChartDataMapper;
import models.Student;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class PieBdateChartDrawer extends JFrame {
     public PieBdateChartDrawer(String title, List<Student> students) {
         super(convertCP1251toUTF8(title));
         setContentPane(createStudentsByBdatePanel(students));
         setExtendedState(JFrame.MAXIMIZED_BOTH);
         setSize(600, 300);
     }



     public static JPanel createStudentsByBdatePanel(List<Student> students) {
         PieDataset dataset = ChartDataMapper.createStudentsByBdateDataset(students);
         JFreeChart chart = createPieChart(dataset);
         chart.setPadding(new RectangleInsets(4, 8, 2, 2));

         return new ChartPanel(chart);
     }

     private static JFreeChart createPieChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                convertCP1251toUTF8("Количество студентов по месяцу рождения"),
                    dataset,
                false,
                true,
                false
        );

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
