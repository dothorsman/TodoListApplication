import cloudutils.CloudEditor;
import cloudutils.CloudGetter;
import cloudutils.CloudParser;
import database.TodoItemManager;
import piechart.ChartUI;
import todo.TodoItem;
import todo.TodoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

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
    JLabel descriptionNote;
    JLabel dueDateNote;
    JLabel operateNote;

    TodoList list = new TodoList();
    CloudGetter cloudGetter = new CloudGetter();
    CloudEditor cloudEditor = new CloudEditor();
    CloudParser parser = new CloudParser();
    TodoItemManager manager = new TodoItemManager("TodoItem.db");

    public UI(){
        super("Todo Application");

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        //Area showing to-do items
        todoItems = new JTextArea("This will show all current todo items");
        var recentConstraints = new GridBagConstraints(1, 0, 1,5 , 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0);
        panel.add(todoItems, recentConstraints);
        todoItems.setSize(900,900);

        //Operation tips label
        titleNote = new JLabel("Please enter a title:");
        var termConstraints = new GridBagConstraints(2, 0, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(titleNote, termConstraints);
        titleNote.setSize(150,150);

        //Area to enter title
        title = new JTextField("");
        var titleConstraints = new GridBagConstraints(2, 1, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(title, titleConstraints);
        title.setSize(150,150);

        //Operation tips label
        descriptionNote = new JLabel("Please enter a description:");
        var descriptionNoteConstraints = new GridBagConstraints(2, 2, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(descriptionNote, descriptionNoteConstraints);
        titleNote.setSize(150,150);

        //Area to enter description
        description = new JTextField("");
        var descriptionConstraints = new GridBagConstraints(2, 3, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(description, descriptionConstraints);
        description.setSize(150,150);

        //Operation tips label
        dueDateNote = new JLabel("Please enter a due date (Example:2020-04-15T12:00):");
        var dueDateConstraints = new GridBagConstraints(2, 4, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(dueDateNote, dueDateConstraints);
        titleNote.setSize(150,150);

        ////Area to enter due date
        duedate = new JTextField("");
        var dateConstraints = new GridBagConstraints(2, 5, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(duedate, dateConstraints);
        duedate.setSize(150,150);

        //Operation tips label
        operateNote = new JLabel("Please enter the id of the item you want to operate:");
        var operateNoteConstraints = new GridBagConstraints(2, 6, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateNote, operateNoteConstraints);
        operateNote.setSize(150,150);

        //Area to enter operate item ID
        operateID = new JTextField("");
        var ownerConstraints = new GridBagConstraints(2, 7, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateID, ownerConstraints);
        operateID.setSize(150,150);

        //Add button
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
                //Add new to-do item
                String addItemTitle = title.getText();
                String addItemDescription = description.getText();
                String addItemDueDate = duedate.getText();
                TodoItem addItem = new TodoItem(addItemTitle, addItemDescription, addItemDueDate);
                list.addItemToTodoList(addItem);
                //Add item to database
                manager.addItem(addItem);
                //Add item to cloud
                try {
                    cloudEditor.addTodoItem(addItem);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //Display in UI
                todoItems.setText(list.AllItemInformation());
            }
        });

        //Delete button
        delete = new JButton("Delete");
        var deleteConstraints = new GridBagConstraints(3, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get the id of the to-do item user wants to delete
                int deleteItemID = Integer.parseInt(operateID.getText());
                //Delete item from todolist
                list.deleteItem(deleteItemID);
                //Delete item from cloud
                try {
                    cloudEditor.deleteTodoItem(deleteItemID);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //Display current to-do items
                todoItems.setText(list.AllItemInformation());
            }
        });

        //Sync Button
        sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        sync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Sync data from cloud to local
                try {
                    String JsonString = cloudGetter.getTodoItemJsonString();
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
                //Get the id of the to-do item user wants to snooze
                int snoozedItemID = Integer.parseInt(operateID.getText());
                //Get the date of the to-do item user wants to snooze
                String newDate = duedate.getText();
                //Snooze item
                list.snoozeItemDueDate(snoozedItemID, newDate);
                //Display current to-do items
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
                //Complete item
                list.completedItem(completedItemID);
                //Display current to-do items
                todoItems.setText(list.AllItemInformation());
            }
        });

        //PieChart button
        pieChart = new JButton("piechart");
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
