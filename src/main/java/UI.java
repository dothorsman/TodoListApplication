import cloudutils.CloudEditor;
import cloudutils.CloudGetter;
import cloudutils.CloudParser;
import database.TodoItemManager;
import piechart.ChartUI;
import todo.Reminder;
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
    JTextField setID;
    JButton add;
    JButton delete;
    JButton complete;
    JButton snooze;
    JButton update;
    JButton sync;
    JButton pieChart;
    JButton reminder;
    JLabel titleNote;
    JLabel descriptionNote;
    JLabel dueDateNote;
    JLabel operateNote;
    JLabel setIDNote;

    TodoList list = new TodoList();
    CloudGetter cloudGetter = new CloudGetter();
    CloudEditor cloudEditor = new CloudEditor();
    CloudParser parser = new CloudParser();
    TodoItemManager manager = new TodoItemManager("TodoItem.db");
    Reminder reminders = new Reminder();

    public UI(){
        super("Todo Application");

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        //Area showing to-do items
        todoItems = new JTextArea("This will show all current todo items");
        var recentConstraints = new GridBagConstraints(0, 0, 2,10 , 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0);
        panel.add(todoItems, recentConstraints);
        todoItems.setSize(1200,900);

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

        //Area to enter due date
        duedate = new JTextField("");
        var dateConstraints = new GridBagConstraints(2, 5, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(duedate, dateConstraints);
        duedate.setSize(150,150);

        //Set id tips label
        setIDNote = new JLabel("Please enter an id (follow the order of existing ids) :");
        var setIDNoteConstraints = new GridBagConstraints(2, 6, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(setIDNote, setIDNoteConstraints);
        setIDNote.setSize(150,150);

        //Area to enter id
        setID = new JTextField("");
        var setIDConstraints = new GridBagConstraints(2, 7, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(setID, setIDConstraints);
        setID.setSize(150,150);

        //Operation tips label
        operateNote = new JLabel("Please enter the id of the item you want to operate:");
        var operateNoteConstraints = new GridBagConstraints(2, 8, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateNote, operateNoteConstraints);
        operateNote.setSize(150,150);

        //Area to enter operate item ID
        operateID = new JTextField("");
        var ownerConstraints = new GridBagConstraints(2, 9, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateID, ownerConstraints);
        operateID.setSize(150,150);

        //Add button
        add = new JButton("Add");
        var addConstraints = new GridBagConstraints(2, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                }
                //Clear display information
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
                int addItemID = Integer.parseInt(setID.getText());
                if (list.checkForDuplicateID(addItemID)){
                    JOptionPane.showMessageDialog(null,"Duplicate ID!");
                    todoItems.setText(list.AllItemInformation());
                }else {
                    TodoItem addItem = new TodoItem(addItemTitle, addItemDescription, addItemDueDate, addItemID);
                    list.addItemToTodoList(addItem);
                    //Add item to database
                    manager.addItem(addItem);
                    //Add item to cloud
                    try {
                        cloudEditor.addTodoItem(addItem);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Successfully added the to-do item!");
                    //Display in UI
                    todoItems.setText(list.AllItemInformation());
                }
            }
        });
        add.setSize(100,150);
        panel.add(add, addConstraints);

        //Delete button
        delete = new JButton("Delete");
        var deleteConstraints = new GridBagConstraints(3, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                }
                //Get the id of the to-do item user wants to delete
                int deleteItemID = Integer.parseInt(operateID.getText());
                //Delete item from local
                list.deleteItem(deleteItemID);
                //Delete item from cloud
                try {
                    cloudEditor.deleteTodoItem(deleteItemID);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"Successfully deleted!");
                //Display current to-do items
                todoItems.setText(list.AllItemInformation());
            }
        });
        delete.setSize(100,150);
        panel.add(delete, deleteConstraints);

        //Update button
        update = new JButton("Update");
        var updateConstraints = new GridBagConstraints(3, 11, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get the id of the to-do item user wants to update
                int updateItemID = Integer.parseInt(operateID.getText());
                //Delete item from local
                list.deleteItem(updateItemID);
                //Delete item from cloud
                try {
                    cloudEditor.deleteTodoItem(updateItemID);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //Add new to-do item
                String addItemTitle = title.getText();
                String addItemDescription = description.getText();
                String addItemDueDate = duedate.getText();
                int addItemid = Integer.parseInt(setID.getText());
                TodoItem addItem = new TodoItem(addItemTitle, addItemDescription, addItemDueDate, addItemid);
                list.addItemToTodoList(addItem);
                //Add item to database
                manager.addItem(addItem);
                //Add item to cloud
                try {
                    cloudEditor.addTodoItem(addItem);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"Successfully updated!");
                //Display in UI
                todoItems.setText(list.AllItemInformation());
            }
        });
        update.setSize(100,150);
        panel.add(update, updateConstraints);

        //Reminder button
        reminder = new JButton("Reminder");
        var reminderConstraints = new GridBagConstraints(1, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        reminder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reminderInformation = "";
                list.setOverDueItems(reminders.getOverDueItems(list));
                list.setDueWithin24HoursItems(reminders.getDueWithin24HoursItems(list));
                reminderInformation = reminders.getRemindeInformation(list.getOverDueItems(),list.getDueWithin24HoursItems());
                JOptionPane.showMessageDialog(null,reminderInformation);
            }
        });
        panel.add(reminder, reminderConstraints);
        reminder.setSize(150,150);

        //Sync button
        sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 11, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        sync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                    //Sync data from database (Network connection failed)
                    list.setItemsInTodoList(manager.getAllItems());
                }else {
                    //Sync data from cloud to local
                    try {
                        String JsonString = cloudGetter.getTodoItemJsonString();
                        list = parser.parseJsonTodoItem(JsonString);
                        manager.clear();
                    } catch (IOException | SQLException ioException) {
                        ioException.printStackTrace();
                    }
                    //Sync data from cloud to database
                    for (TodoItem item : list.getItemsInTodoList()) {
                        manager.addItem(item);
                    }
                }
                //Display current items
                JOptionPane.showMessageDialog(null,"Successfully synchronized!");
                todoItems.setText(list.AllItemInformation());
            }
        });
        panel.add(sync, syncConstraints);
        sync.setSize(150,150);

        //Snooze button
        snooze = new JButton("Snooze");
        var snoozeConstraints = new GridBagConstraints(2, 11, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                }
                //Get the id of the to-do item user wants to snooze
                int snoozedItemID = Integer.parseInt(operateID.getText());
                //Get the date of the to-do item user wants to snooze
                String newDate = duedate.getText();
                //Snooze item
                list.snoozeItemDueDate(snoozedItemID, newDate);
                //Display current to-do items
                JOptionPane.showMessageDialog(null,"Successfully snoozed!");
                todoItems.setText(list.AllItemInformation());
            }
        });
        snooze.setSize(100,150);
        panel.add(snooze, snoozeConstraints);


        //Complete button
        complete = new JButton("Complete");
        var completeConstraints = new GridBagConstraints(4, 10, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        complete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                }
                //Get the id of the to-do item user wants to complete
                int completedItemID = Integer.parseInt(operateID.getText());
                //Complete item
                list.completedItem(completedItemID);
                //Display current to-do items
                JOptionPane.showMessageDialog(null,"Successfully completed!");
                todoItems.setText(list.AllItemInformation());
            }
        });
        complete.setSize(100,150);
        panel.add(complete, completeConstraints);

        //PieChart button
        pieChart = new JButton("piechart");
        var pieChartConstraints = new GridBagConstraints(4, 11, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        pieChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                }else {
                    ChartUI pieChart=new ChartUI("Todo Item PieChart");
                }
            }
        });
        pieChart.setSize(100,150);
        panel.add(pieChart, pieChartConstraints);

        setPreferredSize(new Dimension(1200, 900));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }


    public static void main(String[] args)
    {
        new UI();
    }
    @Override
    public void actionPerformed(ActionEvent e) { }
}
