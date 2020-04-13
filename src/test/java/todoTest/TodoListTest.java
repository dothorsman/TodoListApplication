package todoTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.TodoItem;
import todo.TodoList;

import java.io.IOException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest
{
    TodoList TodoList0;

    @BeforeEach
    void setup() { TodoList0 = new TodoList(); }

    @Test
    void UneditedListNamesShouldHaveConsecutiveDigits()
    {
        String TodoList0Digit = TodoList0.getNameOfList().substring(TodoList0.getNameOfList().length() - 1, TodoList0.getNameOfList().length());

        TodoList TodoList1 = new TodoList();
        String TodoList1Digit = TodoList1.getNameOfList().substring(TodoList1.getNameOfList().length() - 1, TodoList1.getNameOfList().length());

        TodoList TodoList2 = new TodoList();
        String TodoList2Digit = TodoList2.getNameOfList().substring(TodoList2.getNameOfList().length() - 1, TodoList2.getNameOfList().length());

        assertEquals(Integer.valueOf(TodoList0Digit), Integer.valueOf(TodoList1Digit) - 1);
        assertEquals(Integer.valueOf(TodoList1Digit), Integer.valueOf(TodoList2Digit) - 1);
    }

    @Test
    void setTodoListName()
    {
        String newNameForTodoList = "Things to do on monday";
        TodoList0.setNameOfList(newNameForTodoList);
        assertEquals(newNameForTodoList, TodoList0.getNameOfList());
    }

    @Test
    void addItemToTodoList()
    {
        //fix this after TodoItems is done
        TodoItem firstItem = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        TodoItem secondItem = new TodoItem("Assignment2", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        TodoItem thirdItem = new TodoItem("Assignment13", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);

        TodoList0.addItemToTodoList(firstItem);
        TodoList0.addItemToTodoList(secondItem);
        TodoList0.addItemToTodoList(thirdItem);

        assertEquals(firstItem, TodoList0.getItemFromTodoList(firstItem));
        assertEquals(secondItem, TodoList0.getItemFromTodoList(secondItem));
        assertEquals(thirdItem, TodoList0.getItemFromTodoList(thirdItem));
    }

    @Test
    void deleteItemToTodoList()
    {
        //fix this after TodoItems is done
        TodoItem firstItem = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        TodoItem secondItem = new TodoItem("Assignment2", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        TodoItem thirdItem = new TodoItem("Assignment13", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);

        TodoList0.addItemToTodoList(firstItem);
        TodoList0.addItemToTodoList(secondItem);
        TodoList0.deleteItem(1);


        assertEquals(1, TodoList0.getItemsInTodoList().size());
    }

    @Test
    void snoozeItemDeadlineTime()
    {
        TodoItem firstItem = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,12,00);
        TodoList0.addItemToTodoList(firstItem);
        TodoList0.snoozeItemDeadlineTime(1,60);
        assertEquals(13,firstItem.getDeadlineTime().get(Calendar.HOUR_OF_DAY));
    }

}