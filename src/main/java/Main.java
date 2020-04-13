import todo.TodoItem;
import todo.TodoList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            TodoList list = new TodoList();
            while (true) {
                //Main interface, showing the basic operation of the software
                System.out.println("Welcome to the to-do list software！");
                System.out.println("If you want to add to-do items, please type 'add' ");
                System.out.println("If you want to snooze to-do items, please type 'snooze' ");
                System.out.println("If you want to mark completed to-do items, please type 'complete' ");
                System.out.println("If you want to delete to-do items, please type 'delete' ");
                System.out.println("If you want to view the current to-do list,, please type 'view' ");
                System.out.println("If you want to exit the software, please type 'quit' ");
                String input = br.readLine();
                //Exit the program
                if (input.equals("quit")){
                    break;
                }else if(input.equals("add")){
                    //Add to-do item
                    System.out.println("Please enter a title");
                    String title = br.readLine();
                    System.out.println("Please enter a owner");
                    String owner = br.readLine();
                    System.out.println("Please enter a content");
                    String content = br.readLine();
                    System.out.println("Please enter a year");
                    int year = Integer.parseInt(br.readLine());
                    System.out.println("Please enter a month");
                    int month = Integer.parseInt(br.readLine());
                    System.out.println("Please enter a date");
                    int date = Integer.parseInt(br.readLine());
                    System.out.println("Please enter a hour");
                    int hour = Integer.parseInt(br.readLine());
                    System.out.println("Please enter a minute");
                    int minute = Integer.parseInt(br.readLine());
                    TodoItem addItem = new TodoItem(title, owner, content, year, month, date, hour, minute);
                    list.addItemToTodoList(addItem);
                    System.out.println();
                }else if (input.equals("delete")){
                    //Delete to-do item
                    list.printAllItemInformation();
                    System.out.println("Please enter the id of the to-do item you want to delete: ");
                    int deleteId = Integer.parseInt(br.readLine());
                    list.deleteItem(deleteId);
                    System.out.println();
                }else if (input.equals("complete")){
                    //Mark to-do items as completed
                    list.printAllItemInformation();
                    System.out.println("Please enter the id of the to-do item you want to mark finished: ");
                    int completedId = Integer.parseInt(br.readLine());
                    list.completedItem(completedId);
                    System.out.println();
                }else if (input.equals("snooze")){
                    //Delayed deadline for to-do items
                    list.printAllItemInformation();
                    System.out.println("Please enter the id of the to-do item you want to snooze: ");
                    int snoozedId = Integer.parseInt(br.readLine());
                    System.out.println("The current deadline for this to-do is:");
                    list.printItemDeadlineTime(snoozedId);
                    System.out.println("Please enter how many minutes you want to extend: ");
                    int snoozeTime = Integer.parseInt(br.readLine());
                    list.snoozeItemDeadlineTime(snoozedId, snoozeTime);
                    System.out.println("The modification was successful. The current deadline for this to-do is: ");
                    list.printItemDeadlineTime(snoozedId);
                    System.out.println();
                }else if (input.equals("view")){
                    if (list.getItemsInTodoList().size()==0){
                        System.out.println("There are currently no to-do items");
                        System.out.println();
                    }else {
                    //View to-do items
                    list.printAllItemInformation();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
