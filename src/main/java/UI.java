import CloudUtils.CloudEditor;
import CloudUtils.CloudGetter;
import CloudUtils.CloudParser;
import DataBase.TodoItemManager;
import PieChart.ChartUI;
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

    JTextArea todoItems;
    JTextField title;
    JTextField description;
    JTextField duedate;
    JTextField operateID;
    JButton add;
    JButton delete;
    JButton complete;
    JButton snooze;
    JButton sync;
    JButton pieChart;
    JLabel titleNote;
    JLabel DescriptionNote;
    JLabel DueDateNote;
    JLabel operateNote;

    TodoList list = new TodoList();
    CloudEditor cloud = new CloudEditor();
    CloudParser parser = new CloudParser();
    TodoItemManager manager = new TodoItemManager("TodoItem.db");

    public UI(){
        super("Todo Application");

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        //Area showing todoitems
        todoItems = new JTextArea("This will show all current todo items");
        var recentConstraints = new GridBagConstraints(1, 0, 1,5 , 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0);
        panel.add(todoItems, recentConstraints);
        todoItems.setSize(900,900);

        //Operation tips Label
        titleNote = new JLabel("Please enter a title:");
        var termConstraints = new GridBagConstraints(2, 0, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(titleNote, termConstraints);
        titleNote.setSize(150,150);

        //Area to enter title
        title = new JTextField("");
        var titleConstraints = new GridBagConstraints(2, 1, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(title, titleConstraints);
        title.setSize(150,150);

        //Operation tips Label
        DescriptionNote = new JLabel("Please enter a description:");
        var DescriptionConstraints = new GridBagConstraints(2, 2, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(DescriptionNote, DescriptionConstraints);
        titleNote.setSize(150,150);

        //Area to enter description
        description = new JTextField("");
        var descriptionConstraints = new GridBagConstraints(2, 3, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(description, descriptionConstraints);
        description.setSize(150,150);

        //Operation tips Label
        DueDateNote = new JLabel("Please enter a due date (Example:2020-04-15T12:00):");
        var DueDateConstraints = new GridBagConstraints(2, 4, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(DueDateNote, DueDateConstraints);
        titleNote.setSize(150,150);

        ////Area to enter due date
        duedate = new JTextField("");
        var dateConstraints = new GridBagConstraints(2, 5, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(duedate, dateConstraints);
        duedate.setSize(150,150);

        //Operation tips Label
        operateNote = new JLabel("Please enter the id of the item you want to operate:");
        var operateNoteConstraints = new GridBagConstraints(2, 6, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateNote, operateNoteConstraints);
        operateNote.setSize(150,150);

        //Area to enter operate item ID
        operateID = new JTextField("");
        var ownerConstraints = new GridBagConstraints(2, 7, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateID, ownerConstraints);
        operateID.setSize(150,150);

        //Add buttton
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

        //delete button
        delete = new JButton("Delete");
        var deleteConstraints = new GridBagConstraints(3, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get the id of the to-do item user wants to delete
                int deleteItemID = Integer.parseInt(operateID.getText());
                //delete item from todolist
                list.deleteItem(deleteItemID);
                //delete item from cloud
                try {
                    cloud.deleteTodoItem(deleteItemID);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //Display current todoitems
                todoItems.setText(list.AllItemInformation());
            }
        });

        //Sync Button
        sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        sync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String JsonString = cloud.getAllTeam4TodoItems();
                    list = parser.parseJsonTodoItem(JsonString);
                    manager.clear();
                } catch (IOException | SQLException ioException) {
                    ioException.printStackTrace();
                }
                //Sync data from cloud to database
                for (TodoItem item:list.getItemsInTodoList()){
                    manager.addItem(item);
                }
                //Display current items
                todoItems.setText(list.AllItemInformation());
            }
        });
        panel.add(sync, syncConstraints);
        sync.setSize(300,150);

        //Snooze Button
        snooze = new JButton("Snooze");
        var snoozeConstraints = new GridBagConstraints(2, 11, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int snoozedItemID = Integer.parseInt(operateID.getText());
                String newDate = duedate.getText();
                list.snoozeItemDueDate(snoozedItemID, newDate);
                todoItems.setText(list.AllItemInformation());
            }
        });

        //Complete button
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

        //PieChart button
        pieChart = new JButton("PieChart");
        var pieChartConstraints = new GridBagConstraints(4, 11, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        pieChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChartUI pieChart=new ChartUI("Todo Item PieChart");
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
        pieChart.setSize(100,150);
        panel.add(pieChart, pieChartConstraints);

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
