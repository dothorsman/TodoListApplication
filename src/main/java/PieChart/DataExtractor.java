package PieChart;

import org.javatuples.Pair;
import todo.TodoItem;
import todo.TodoList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataExtractor {

    public static List<Pair<String, Integer>> analyzedata(TodoList todolist) {
        List<Pair<String, Integer>> list = new ArrayList<>();
        List<TodoItem> Itemlist = todolist.getItemsInTodoList();
        int AllNumbers=Itemlist.size();
        int CompletedNumbers=0;
        int pendingNumbers=0;
        int overdueNumber=0;
        LocalDateTime now=LocalDateTime.now();
        for (TodoItem item: Itemlist){
            if (item.checkIfCompleted()){
                CompletedNumbers+=1;
            }else if (!item.checkIfCompleted()){
                if (now.isAfter(item.getDeadlineTime())){
                    pendingNumbers+=1;
                }else {
                    overdueNumber+=1;
                }
            }
        }
        list.add(new Pair<>("All", AllNumbers));
        list.add(new Pair<>("Completed", CompletedNumbers));
        list.add(new Pair<>("Pending", pendingNumbers));
        list.add(new Pair<>("Overdue", overdueNumber));
        return list;
    }
}