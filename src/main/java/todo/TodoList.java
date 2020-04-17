package todo;

import java.util.ArrayList;
import java.util.List;

public class TodoList
{
    private String nameOfTodoList = "";
    private static int nextNameDigit;
    private List<TodoItem> itemsInTodoList;
    String Information="";

    public TodoList()
    {
        nameOfTodoList = "List " + getNextNameDigit();
        itemsInTodoList = new ArrayList<>();
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

    public void addItemToTodoList(TodoItem item)
    {
        this.itemsInTodoList.add(item);
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

    public void printAllItemInformation(){
        for (TodoItem Item: itemsInTodoList){
            System.out.println("Title: " + Item.getTitle());
            System.out.println("Description: " + Item.getDescription());
            if (Item.checkIfCompleted()){
                System.out.println("Status: Finished");
            }else {
                System.out.println("Status: unfinished");
            }
            System.out.println("Id: " + Item.getId());
            System.out.println("CreationTime: " + Item.getCreationTime());
            System.out.println("DeadlineTime: " + Item.getDeadlineTime());
            if (Item.checkIfCompleted()){
                System.out.println("CompletionTime: "+ Item.getCompletionTime());
            }
            System.out.println();
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


}