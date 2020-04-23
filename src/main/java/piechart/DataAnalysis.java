package piechart;

import org.javatuples.Pair;
import todo.TodoItem;
import todo.TodoList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataAnalysis {

    public static List<Pair<String, Integer>> analyzeData(TodoList todolist) {
        List<Pair<String, Integer>> list = new ArrayList<>();
        List<TodoItem> Itemlist = todolist.getItemsInTodoList();
        int completedNumbers=0;
        int pendingNumbers=0;
        int overdueNumbers=0;
        //Analyze the status of to-do items
        for (TodoItem item: Itemlist){
            if (item.checkIfCompleted()){
                completedNumbers+=1;
            }else if (!item.checkIfCompleted()){
                if (item.getDeadlineTime().isBefore(LocalDateTime.now())){
                    overdueNumbers+=1;
                }else {
                    pendingNumbers+=1;
                }
            }
        }
        list.add(new Pair<>("Completed", completedNumbers));
        list.add(new Pair<>("Pending", pendingNumbers));
        list.add(new Pair<>("Overdue", overdueNumbers));
        return list;
    }
}