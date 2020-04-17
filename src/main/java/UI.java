import CloudUtils.CloudEditor;
import CloudUtils.CloudGetter;
import DataBase.TodoItemManager;
import todo.TodoItem;
import todo.TodoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UI extends JFrame implements ActionListener {

    JTextArea pieChart;
    JTextArea todoItems;
    JButton sync;
    JTextField title;
    JTextField description;
    JTextField duedate;
    JTextField operateID;
    JButton add;
    JButton delete;
    JButton complete;
    JButton snooze;
    JLabel titleNote;
    JLabel DescriptionNote;
    JLabel DueDateNote;
    JLabel operateNote;


    TodoList list = new TodoList();
    CloudEditor cloud = new CloudEditor();
    TodoItemManager manager = new TodoItemManager("TodoItem.db");

    public UI(){
        super("Todo Application with Swing UI");

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);


        pieChart = new JTextArea("This is where the pie chart will be displayed");
        var pieChartConstraints = new GridBagConstraints(0, 0, 1,5 , 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0);
        panel.add(pieChart, pieChartConstraints);
        pieChart.setSize(400,900);

        todoItems = new JTextArea("This will show all current todo items");
        var recentConstraints = new GridBagConstraints(1, 0, 1,5 , 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0);
        panel.add(todoItems, recentConstraints);
        todoItems.setSize(400,900);

        sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        sync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel.add(sync, syncConstraints);
        sync.setSize(300,150);

        titleNote = new JLabel("Please enter a title:");
        var termConstraints = new GridBagConstraints(2, 0, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(titleNote, termConstraints);
        titleNote.setSize(150,150);

        title = new JTextField("");
        var titleConstraints = new GridBagConstraints(2, 1, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(title, titleConstraints);
        title.setSize(150,150);

        DescriptionNote = new JLabel("Please enter a description:");
        var DescriptionConstraints = new GridBagConstraints(2, 2, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(DescriptionNote, DescriptionConstraints);
        titleNote.setSize(150,150);

        description = new JTextField("");
        var descriptionConstraints = new GridBagConstraints(2, 3, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(description, descriptionConstraints);
        description.setSize(150,150);

        DueDateNote = new JLabel("Please enter a due date (yyyy-MM-dd-HH-mm):");
        var DueDateConstraints = new GridBagConstraints(2, 4, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(DueDateNote, DueDateConstraints);
        titleNote.setSize(150,150);

        duedate = new JTextField("");
        var dateConstraints = new GridBagConstraints(2, 5, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(duedate, dateConstraints);
        duedate.setSize(150,150);

        operateNote = new JLabel("Please enter the id of the item you want to operate:");
        var operateNoteConstraints = new GridBagConstraints(2, 6, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateNote, operateNoteConstraints);
        operateNote.setSize(150,150);

        operateID = new JTextField("");
        var ownerConstraints = new GridBagConstraints(2, 7, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateID, ownerConstraints);
        operateID.setSize(150,150);

        add = new JButton("Add");
        var addConstraints = new GridBagConstraints(2, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Clear data
                todoItems.setText("");
                try {
                    manager.clear();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                //Add new todoItem
                String addItemTitle = title.getText();
                String addItemDescription = description.getText();
                String addItemDueDate = duedate.getText();
                TodoItem addItem = new TodoItem(addItemTitle, addItemDescription, addItemDueDate);
                list.addItemToTodoList(addItem);
                //Add item to database
                manager.addItem(addItem);
                //Add item to cloud
                try {
                    cloud.addTodoItem(addItem);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //Display in UI
                todoItems.setText(list.AllItemInformation());
            }
        });

        delete = new JButton("Delete");
        var deleteConstraints = new GridBagConstraints(3, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get the id of the to-do item user wants to delete
                int deleteItemID = Integer.parseInt(operateID.getText());
                //delete item
                list.deleteItem(deleteItemID);
                //Display current todoitems
                todoItems.setText(list.AllItemInformation());
            }
        });

        complete = new JButton("Complete");
        var completeConstraints = new GridBagConstraints(4, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        complete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get the id of the to-do item user wants to complete
                int completedItemID = Integer.parseInt(operateID.getText());
                //complete item
                list.completedItem(completedItemID);
                //Display current todoitems
                todoItems.setText(list.AllItemInformation());
            }
        });

        snooze = new JButton("Snooze");
        var snoozeConstraints = new GridBagConstraints(2, 11, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        complete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int snoozedItemID = Integer.parseInt(operateID.getText());
                String newDate = duedate.getText();
                list.snoozeItemDeadlineTime(snoozedItemID,newDate);
                todoItems.setText(list.AllItemInformation());
            }
        });

        add.setSize(100,150);
        panel.add(add, addConstraints);
        delete.setSize(100,150);
        panel.add(delete, deleteConstraints);
        complete.setSize(100,150);
        panel.add(complete, completeConstraints);
        snooze.setSize(100,150);
        panel.add(snooze, snoozeConstraints);

        setPreferredSize(new Dimension(1200, 600));
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
