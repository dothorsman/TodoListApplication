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


}