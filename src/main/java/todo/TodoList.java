package todo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TodoList
{
    private String nameOfTodoList = ""; //final?
    private static int nextNameDigit = 0;
    private List<TodoItem> itemsInTodoList;

    public TodoList()
    {
        nameOfTodoList = "List " + getNextNameDigit();
        itemsInTodoList = new ArrayList<>();
    }

    public String getNameOfTodoList() {
        return nameOfTodoList;
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

    public String getNameOfList()
    {
        return(nameOfTodoList);
    }

    public void setNameOfList(String name)
    {
        nameOfTodoList = name;
    }

    public TodoItem getItemFromTodoList(TodoItem item)
    {
        TodoItem resultingItem = null;
        for (TodoItem todoItem : itemsInTodoList) {
            if (todoItem == item) {
                resultingItem = item;
            }
        }
        return(resultingItem);
    }

    //public TodoItem getAllItemsFromTodoList() {

    //}

    public void addItemToTodoList(TodoItem item)
    {
        this.itemsInTodoList.add(item);
        //put CloudSender.add(item) or CloudGetter.add(item) here
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
                todoItem.changeToCompleted();
            }
        }
    }

    public void printAllItemInformation(){
        for (TodoItem Item: itemsInTodoList){
                System.out.println("Title: " + Item.getTitle());
                System.out.println("Content: " + Item.getContent());
                System.out.println("Owner: " + Item.getOwner());
                if (Item.checkIfCompleted()){
                    System.out.println("Status: Finished");
                }else {
                    System.out.println("Status: unfinished");
                }
                System.out.println("Id: " + Item.getId());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                System.out.println("CreationTime: " + df.format(Item.getCreationTime().getTime()));
                System.out.println("DeadlineTime: " + df.format(Item.getDeadlineTime().getTime()));
                if (Item.checkIfCompleted()){
                    System.out.println("CompletionTime: "+ df.format(Item.getCompletionTime().getTime()));
                }
                System.out.println();
            }
        }

    public void printItemDeadlineTime(int id){
        for (TodoItem Item: itemsInTodoList){
            if (Item.getId() == id){
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                System.out.println(df.format(Item.getDeadlineTime().getTime()));
            }
        }
    }

    public void snoozeItemDeadlineTime(int id, int extendTime){
        for (TodoItem Item: itemsInTodoList){
            if (Item.getId() == id){
                Item.getDeadlineTime().add(Calendar.MINUTE, extendTime);
            }
        }
    }


}
