package TodoTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.TodoItem;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest
{
    TodoItem item;

    @BeforeEach
    void setup()
    {
        item = new TodoItem("Assignment1", "Remember to complete Assignment1 by next week",
                "2020-04-15T12:00",1);
    }

    @Test
    void getTitle()
    {
        assertEquals("Assignment1", item.getTitle());
    }

    @Test
    void setTitle()
    {
        item.setTitle("Assignment2");
        assertEquals("Assignment2", item.getTitle());
    }

    @Test
    void getOwner()
    {
        assertEquals("team4", item.getOwner());
    }

    @Test
    void getDescription()
    {
        assertEquals("Remember to complete Assignment1 by next week", item.getDescription());
    }

    @Test
    void setDescription()
    {
        item.setDescription("Remember to complete Assignment2 by next week");
        assertEquals("Remember to complete Assignment2 by next week", item.getDescription());
    }

    @Test
    void checkIfCompleted()
    {
        assertEquals(false, item.checkIfCompleted());
    }

    @Test
    void completeItem()
    {
        item.completeItem();
        assertEquals(true, item.checkIfCompleted());
    }

    @Test
    void changeToIncomplete()
    {
        item.changeToIncomplete();
        assertFalse(item.checkIfCompleted());
    }


    //add update method test?



}