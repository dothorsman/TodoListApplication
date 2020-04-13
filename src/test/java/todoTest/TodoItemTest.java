package todoTest;

import org.junit.jupiter.api.Test;
import todo.TodoItem;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest {

    @Test
    void getTitle() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        assertEquals("Assignment1", Item.getTitle());
    }

    @Test
    void setTitle() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        Item.setTitle("Assignment2");
        assertEquals("Assignment2", Item.getTitle());
    }

    @Test
    void getOwner() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        assertEquals("TeamFour", Item.getOwner());
    }

    @Test
    void setOwner() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        Item.setOwner("TeamZero");
        assertEquals("TeamZero", Item.getOwner());
    }

    @Test
    void getContent() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        assertEquals("Remember to complete Assignment1 by next week", Item.getContent());
    }

    @Test
    void setContent() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        Item.setContent("Remember to complete Assignment2 by next week");
        assertEquals("Remember to complete Assignment2 by next week", Item.getContent());
    }

    @Test
    void getId() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        assertEquals(4, Item.getId());

    }

    @Test
    void checkIfCompleted() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        assertEquals(false, Item.checkIfCompleted());
    }

    @Test
    void changeToCompleted() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        Item.changeToCompleted();
        assertEquals(true, Item.checkIfCompleted());
    }

    @Test
    void changeToIncomplete() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        Item.changeToIncomplete();
        assertFalse(Item.checkIfCompleted());
    }

    @Test
    void setCompletionTime() {
        TodoItem Item = new TodoItem("Assignment1", "TeamFour",
                "Remember to complete Assignment1 by next week", 2020, 4,8,11,59);
        Calendar cal = Calendar.getInstance();
        Item.setCompletionTime();
        assertEquals(cal,Item.getCompletionTime());
    }


}