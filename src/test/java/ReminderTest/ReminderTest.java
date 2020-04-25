package ReminderTest;


import org.junit.jupiter.api.Test;
import todo.Reminder;
import todo.TodoItem;
import todo.TodoList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReminderTest {

    @Test
    void getOverDueItems() {
        TodoList list = new TodoList();
        List<TodoItem> itemslist = new ArrayList<>();
        TodoItem overdueItem1 = new TodoItem("Assignment1","homework1","2019-04-15T12:00",1,"false","null");
        TodoItem overdueItem2 = new TodoItem("Assignment2","homework2","2019-04-15T12:00",1,"false","null");
        TodoItem normalItem = new TodoItem("Assignment3","homework3","2025-04-15T12:00",1,"false","null");
        itemslist.add(overdueItem1);
        itemslist.add(overdueItem2);
        itemslist.add(normalItem);
        list.setItemsInTodoList(itemslist);
        Reminder reminder = new Reminder();
        List<TodoItem> overduelist =reminder.getOverDueItems(list);

        assertEquals(2,overduelist.size());
        assertEquals(overdueItem1, overduelist.get(0));
        assertEquals(overdueItem2, overduelist.get(1));
    }

    @Test
    void getDueWithin24HoursItems(){
        TodoList list = new TodoList();
        List<TodoItem> itemslist = new ArrayList<>();
        TodoItem overdueItem1 = new TodoItem("Assignment1","homework1","2019-04-15T12:00",1,"false","null");
        TodoItem overdueItem2 = new TodoItem("Assignment2","homework2","2019-04-15T12:00",1,"false","null");
        TodoItem normalItem = new TodoItem("Assignment3","homework3","2025-04-15T12:00",1,"false","null");
        itemslist.add(overdueItem1);
        itemslist.add(overdueItem2);
        itemslist.add(normalItem);
        list.setItemsInTodoList(itemslist);
        Reminder reminder = new Reminder();
        List<TodoItem> dueWithin24Hours = reminder.getDueWithin24HoursItems(list);

        assertEquals(0,dueWithin24Hours.size());
    }

    @Test
    void getReminderInformation() {
        TodoList list = new TodoList();
        List<TodoItem> itemslist = new ArrayList<>();
        TodoItem overdueItem1 = new TodoItem("Assignment1","homework1","2019-04-15T12:00",1,"false","null");
        TodoItem overdueItem2 = new TodoItem("Assignment2","homework2","2019-04-15T12:00",1,"false","null");
        TodoItem normalItem = new TodoItem("Assignment3","homework3","2025-04-15T12:00",1,"false","null");
        itemslist.add(overdueItem1);
        itemslist.add(overdueItem2);
        itemslist.add(normalItem);
        list.setItemsInTodoList(itemslist);
        Reminder reminder = new Reminder();
        List<TodoItem> overduelist =reminder.getOverDueItems(list);
        List<TodoItem> dueWithin24Hours = reminder.getDueWithin24HoursItems(list);
        String Information =reminder.getReminderInformation(overduelist,dueWithin24Hours);
        String actualInformation = "Here are expired to-do items:"
         +"\r\n" + "Title: " + overdueItem1.getTitle() + "\r\n" + "DeadlineTime: " + overdueItem1.getDeadlineTime() + "\r\n"
                +"\r\n" + "Title: " + overdueItem2.getTitle() + "\r\n" + "DeadlineTime: " + overdueItem2.getDeadlineTime() + "\r\n"+
                "\r\n"+ "There are currently no to-do items that will expire within 24 hours";
        assertEquals(actualInformation, Information);
    }


    }



