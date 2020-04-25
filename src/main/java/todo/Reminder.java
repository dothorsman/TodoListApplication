package todo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reminder {

    String ReminderInformation;

    public Reminder() {
        ReminderInformation = "";
    }

    public List<TodoItem> getOverDueItems(TodoList list){
        List<TodoItem> Itemlist = list.getItemsInTodoList();
        List<TodoItem> OverdueItems = new ArrayList<>();
        //Find out overdue items
        for (TodoItem Item : Itemlist){
            if (Item.getDeadlineTime().isBefore(LocalDateTime.now())){
                if (!Item.checkIfCompleted()) {
                    OverdueItems.add(Item);
                }
            }
        }
        return OverdueItems;
    }

    public List<TodoItem> getDueWithin24HoursItems(TodoList list) {
        List<TodoItem> Itemlist = list.getItemsInTodoList();
        List<TodoItem> DueWithin24HoursItems = new ArrayList<>();
        //Find out due within 24 hours items
        for (TodoItem Item : Itemlist) {
            if (Item.getDeadlineTime().isAfter(LocalDateTime.now())) {
                if (!Item.checkIfCompleted()) {
                    Duration duration = Duration.between(LocalDateTime.now(), Item.getDeadlineTime());
                    long Hours = duration.toHours();
                    if (Hours < 24) {
                        DueWithin24HoursItems.add(Item);
                    }
                }
            }
        }
        return DueWithin24HoursItems;
    }

    public String getReminderInformation(List<TodoItem> Overdue, List<TodoItem> DueWithin24Hours) {
        ReminderInformation = "";
        //Overdue Items
        if (Overdue.size() == 0) {
            ReminderInformation += "There are currently no to-do items that have expired";
        } else {
            ReminderInformation += "Here are expired to-do items:";
            for (TodoItem Item : Overdue) {
                ReminderInformation += "\r\n" + "Title: " + Item.getTitle() + "\r\n" + "DeadlineTime: " + Item.getDeadlineTime() + "\r\n";
            }
        }
        //Due within 24 hours items
        if (DueWithin24Hours.size() == 0){
            ReminderInformation += "\r\n"+ "There are currently no to-do items that will expire within 24 hours";
        }else {
            ReminderInformation += "\r\n"+ "Here are the to-do items that will expire in 24 hours:";
            for (TodoItem Item : DueWithin24Hours){
                ReminderInformation += "\r\n" + "Title: " + Item.getTitle() + "\r\n" + "DeadlineTime: " + Item.getDeadlineTime() + "\r\n";
            }
        }
        return ReminderInformation;
    }

}
