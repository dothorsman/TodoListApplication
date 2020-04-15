import CloudUtils.CloudGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UI extends JFrame implements ActionListener {

    JTextArea pieChart;

    JTextArea todoItems;
    JButton sync;

    JTextField title;
    JTextField description;
    JTextField time;
    JTextField date;
    JTextField owner;
    JButton add;
    JButton delete;
    JButton revise;

    public UI(){
        super("Todo Application with Swing UI");

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        //left 3rd of ui
        pieChart = new JTextArea("This is where the pie chart will be displayed");
        var pieChartConstraints = new GridBagConstraints(0, 0, 1,5 , 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0);
        panel.add(pieChart, pieChartConstraints);
        pieChart.setSize(300,900);

        //middle 3rd of ui
        todoItems = new JTextArea("This will show all current todo items");
        var recentConstraints = new GridBagConstraints(1, 0, 1,5 , 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0);
        panel.add(todoItems, recentConstraints);
        todoItems.setSize(300,750);

        sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        sync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel.add(sync, syncConstraints);
        sync.setSize(300,150);

        //right third of ui
        title = new JTextField("Title");
        var titleConstraints = new GridBagConstraints(2, 0, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(title, titleConstraints);
        title.setSize(300,150);

        description = new JTextField("Description");
        var descriptionConstraints = new GridBagConstraints(2, 1, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(description, descriptionConstraints);
        description.setSize(300,150);

        date = new JTextField("Date");
        var dateConstraints = new GridBagConstraints(2, 2, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(date, dateConstraints);
        date.setSize(300,150);

        time = new JTextField("Time");
        var timeConstraints = new GridBagConstraints(2, 3, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(time, timeConstraints);
        time.setSize(300,150);

        owner = new JTextField("Owner");
        var ownerConstraints = new GridBagConstraints(2, 4, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(owner, ownerConstraints);
        owner.setSize(300,150);

        add = new JButton("Add");
        var addConstraints = new GridBagConstraints(2, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        delete = new JButton("Delete");
        var deleteConstraints = new GridBagConstraints(3, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        revise = new JButton("Update");
        var reviseConstraints = new GridBagConstraints(4, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        revise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        add.setSize(100,150);
        panel.add(add, addConstraints);
        delete.setSize(100,150);
        panel.add(delete, deleteConstraints);
        revise.setSize(100,150);
        panel.add(revise, reviseConstraints);

        setPreferredSize(new Dimension(900, 900));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new UI();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
