package piechart;

import cloudutils.CloudParser;
import org.javatuples.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import todo.TodoList;

import javax.swing.*;
import java.util.List;

public class ChartUI extends JFrame {

    public ChartUI(String title) {
        super(title);

        JFreeChart chart = ChartFactory.createPieChart3D(
                title,                  // chart title
                getPieData(),                // data
                true,                   // include legend
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();

        setVisible(true);
    }

    private PieDataset getPieData() {
        String rawData;
        CloudParser parser = new CloudParser();
        TodoList list;
        List<Pair<String, Integer>> pairs;
        try {
            //parse data from cloud
            rawData = DataFetcher.getRawJSONfromAPI();
            list=parser.parseJsonTodoItem(rawData);
            //analyze data
            pairs= DataAnalysis.analyzeData(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Couldn't get data!");
            return new DefaultPieDataset();
        }
        return UIUtils.convertPairsToPieDataset(pairs);
    }



}