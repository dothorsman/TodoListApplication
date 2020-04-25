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
    JTextField setStatus;
    JButton add;
    JButton delete;
    JButton complete;
    JButton snooze;
    JButton sync;
    JButton pieChart;
    JButton reminder;
    JLabel statusNote;
    JLabel titleNote;
    JLabel descriptionNote;
    JLabel dueDateNote;
    JLabel operateNote;
    JLabel setIDNote;
    JScrollPane scrollPane;

    TodoList list = new TodoList();
    TodoList cloudData = new TodoList();
    CloudGetter cloudGetter = new CloudGetter();
    CloudEditor cloudEditor = new CloudEditor();
    CloudParser parser = new CloudParser();
    TodoItemManager databaseManager = new TodoItemManager("TodoItem.db");
    Reminder reminders = new Reminder();

    public UI(){
        super("Todo Application");

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        //Area showing to-do items
        todoItems = new JTextArea("This will show all current todo items, please click the sync button to import data first");
        var recentConstraints = new GridBagConstraints(0, 0, 2,13 , 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0);
        panel.add(todoItems, recentConstraints);
        todoItems.setSize(1200,1200);

        //ScrollPane
        scrollPane = new JScrollPane(todoItems);
        scrollPane.setPreferredSize(new Dimension(400,400));
        panel.add(scrollPane, recentConstraints);

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
        var descriptionNoteConstraints = new GridBagConstraints(2, 8, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(descriptionNote, descriptionNoteConstraints);
        titleNote.setSize(150,150);

        //Area to enter description
        description = new JTextField("");
        var descriptionConstraints = new GridBagConstraints(2, 9, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
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

        //set status tip label
        statusNote = new JLabel("Please enter the status, if not completed, please enter 'false' :");
        var statusNoteConstraints = new GridBagConstraints(2, 2, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(statusNote, statusNoteConstraints);
        statusNote.setSize(150,150);

        //Area to enter status
        setStatus = new JTextField("");
        var setStatusConstraints = new GridBagConstraints(2, 3, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(setStatus, setStatusConstraints);
        setStatus.setSize(150,150);

        //Operation tips label
        operateNote = new JLabel("Please enter the id of the item you want to operate:");
        var operateNoteConstraints = new GridBagConstraints(2, 10, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateNote, operateNoteConstraints);
        operateNote.setSize(150,150);

        //Area to enter operate item ID
        operateID = new JTextField("");
        var ownerConstraints = new GridBagConstraints(2, 11, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(operateID, ownerConstraints);
        operateID.setSize(150,150);

        //Add button
        add = new JButton("Add");
        var addConstraints = new GridBagConstraints(2, 12, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                }
                //Clear display information
                todoItems.setText("");
                //Add new to-do item
                String addItemTitle = title.getText();
                String addItemDescription = description.getText();
                String addItemDueDate = duedate.getText();
                String addItemStatus = setStatus.getText();
                int addItemID = Integer.parseInt(setID.getText());
                if (list.checkForDuplicateID(addItemID)){
                    JOptionPane.showMessageDialog(null,"Duplicate ID!");
                    todoItems.setText(list.AllItemInformation());
                }else {
                    TodoItem addItem = new TodoItem(addItemTitle, addItemDescription, addItemDueDate, addItemID, addItemStatus, "null");
                    list.addItemToTodoList(addItem);
                    //Add item to database
                    databaseManager.addItem(addItem);
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
        var deleteConstraints = new GridBagConstraints(3, 12, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
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
                //Delete item from database
                databaseManager.deleteItem(deleteItemID);
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

        //Reminder button
        reminder = new JButton("Reminder");
        var reminderConstraints = new GridBagConstraints(3, 13, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        reminder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reminderInformation = "";
                list.setOverDueItems(reminders.getOverDueItems(list));
                list.setDueWithin24HoursItems(reminders.getDueWithin24HoursItems(list));
                reminderInformation = reminders.getReminderInformation(list.getOverDueItems(),list.getDueWithin24HoursItems());
                JOptionPane.showMessageDialog(null,reminderInformation);
            }
        });
        panel.add(reminder, reminderConstraints);
        reminder.setSize(150,150);

        //Sync button
        sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 13, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        sync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                    //Sync data from database (Network connection failed)
                    list.setItemsInTodoList(databaseManager.getAllItems());
                }else {
                    //Sync data from cloud to local
                    try {
                        list.synchronousData(databaseManager, cloudEditor);
                        list.setItemsInTodoList(databaseManager.getAllItems());
                        String JsonString = cloudGetter.getTodoItemJsonString();
                        cloudData = parser.parseJsonTodoItem(JsonString);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
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
        var snoozeConstraints = new GridBagConstraints(2, 13, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                }
                //Get the id of the to-do item user wants to snooze
                int snoozedItemID = Integer.parseInt(operateID.getText());
                TodoItem snoozedItem = list.findItemByID(snoozedItemID);
                //Get the date of the to-do item user wants to snooze
                String newDate = duedate.getText();
                //Snooze item
                list.snoozeItemDueDate(snoozedItemID, newDate);
                databaseManager.snoozeItem(snoozedItemID, newDate);
                try {
                    cloudEditor.snoozeItem(snoozedItem);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //Display current to-do items
                JOptionPane.showMessageDialog(null,"Successfully snoozed!");
                todoItems.setText(list.AllItemInformation());
            }
        });
        snooze.setSize(100,150);
        panel.add(snooze, snoozeConstraints);


        //Complete button
        complete = new JButton("Complete");
        var completeConstraints = new GridBagConstraints(4, 12, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        complete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check network connection
                if (!cloudGetter.checkURL()) {
                    JOptionPane.showMessageDialog(null,"The network is not currently connected");
                }
                //Get the id of the to-do item user wants to complete
                int completedItemID = Integer.parseInt(operateID.getText());
                TodoItem completedItem = list.findItemByID(completedItemID);
                //Complete item
                list.completedItem(completedItemID);
                databaseManager.completeItem(completedItemID);
                try {
                    cloudEditor.completedItem(completedItem);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //Display current to-do items
                JOptionPane.showMessageDialog(null,"Successfully completed!");
                todoItems.setText(list.AllItemInformation());
            }
        });
        complete.setSize(100,150);
        panel.add(complete, completeConstraints);

        //PieChart button
        pieChart = new JButton("PieChart");
        var pieChartConstraints = new GridBagConstraints(4, 13, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
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

        setPreferredSize(new Dimension(1200, 600));
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
