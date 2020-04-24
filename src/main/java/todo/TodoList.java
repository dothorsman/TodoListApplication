package todo;

import java.util.ArrayList;
import java.util.List;

public class TodoList
{
    private String nameOfTodoList = "";
    private static int nextNameDigit;
    private List<TodoItem> itemsInTodoList;
    private List<TodoItem> OverDueItems;
    private List<TodoItem> DueWithin24HoursItems;
    String Information="";

    public TodoList()
    {
        nameOfTodoList = "List " + getNextNameDigit();
        itemsInTodoList = new ArrayList<>();
        OverDueItems = new ArrayList<>();
    }

    public List<TodoItem> getItemsInTodoList() {
        return itemsInTodoList;
    }

    public static String getNextNameDigit()
    {
        String digit = Integer.toString(nextNameDigit);
        nextNameDigit++;
        return(digit);
    }

    public boolean checkForDuplicateID(int id){
        boolean status = false;
        for (TodoItem item : itemsInTodoList) {
            if (item.getId() == id) {
                status = true;
            }
        }
        return status;
    }

    public void setOverDueItems(List<TodoItem> list){
        this.OverDueItems = list;
    }

    public List<TodoItem> getOverDueItems(){
        return OverDueItems;
    }

    public List<TodoItem> getDueWithin24HoursItems() {
        return DueWithin24HoursItems;
    }

    public void setDueWithin24HoursItems(List<TodoItem> list) {
        DueWithin24HoursItems = list;
    }

    public String getNameOfList()
    {
        return(nameOfTodoList);
    }

    public void setNameOfList(String name)
    {
        nameOfTodoList = name;
    }

    public void addItemToTodoList(TodoItem item)
    {
        int addItemID = item.getId();
        if (!checkForDuplicateID(addItemID)){
            this.itemsInTodoList.add(item);
        }
    }

    public void deleteItem(int id)
    {
        TodoItem deletingItem = null;
        for (TodoItem todoItem : itemsInTodoList) {
            if (todoItem.getId() == id) {
                deletingItem = todoItem;
            }
        }
        itemsInTodoList.remove(deletingItem);
    }

    public void completedItem(int id){
        for (TodoItem todoItem : itemsInTodoList) {
            if (todoItem.getId() == id) {
                todoItem.completeItem();
            }
        }
    }

    public String AllItemInformation(){
        Information="";
        for (TodoItem item:itemsInTodoList){
            Information +="\r\n"+ "Title: "+ item.getTitle()+"  Description: "+ item.getDescription()+"  Status: "+item.checkIfCompleted()+"  ID: "+item.getId()+"\r\n"+"DeadlineTime: " + item.getDeadlineTime() +"\r\n"+"CreationTime: " + item.getCreationTime()+ "\r\n";
            if (item.checkIfCompleted()){
                Information += "CompletionTime: "+ item.getCompletionTime()+"\r\n";
            }
        }
        return Information;
    }

    public void snoozeItemDueDate(int id, String newDueDate){
        for (TodoItem item:itemsInTodoList){
            if (item.getId() == id){
                item.snoozeDeadlineTime(newDueDate);
            }
        }
    }

    public void setItemsInTodoList(List<TodoItem> allItems) {
        this.itemsInTodoList = allItems;
    }
}