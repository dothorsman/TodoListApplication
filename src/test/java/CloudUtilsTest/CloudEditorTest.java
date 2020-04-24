package CloudUtilsTest;

import cloudutils.CloudEditor;
import cloudutils.CloudGetter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.TodoItem;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CloudEditorTest
{
    CloudEditor cloudEditor;
    CloudGetter cloudGetter;

    TodoItem item1 = new TodoItem("test task 1",
            "first test task, This should not be deleted.", "2020-05-18T12:30",1);
    TodoItem item2 = new TodoItem("test task 2",
            "second test task, This should not be deleted.", "2020-04-18T01:30",2);
    TodoItem item3 = new TodoItem("test task 3",
            "third test task, This should not be deleted.", "2021-04-18T15:30",3);

    TodoItem item0 = new TodoItem("TEST TASK 0", "ZERO TEST TASK",
            "2030-06-14T15:00",4);
    TodoItem nonexistingItem = new TodoItem("nope", "should not exist in cloud or database",
            "2050-06-14T15:00",5);

    boolean empty = false;


    @BeforeEach
    void setup() throws IOException
    {
        cloudEditor = new CloudEditor();
        cloudGetter = new CloudGetter();
    }

    @Test
    void deleteExistingTodoItem() throws IOException
    {
        var resultingID = cloudEditor.addTodoItem(item0);
        var deleteResult = cloudEditor.deleteTodoItem(resultingID);
        assertTrue(deleteResult);
    }

    @Test
    void deleteNotExistingTodoItem() throws IOException
    {
        var nonExistingIDdeleteResult = cloudEditor.deleteTodoItem(152434354);
        assertFalse(nonExistingIDdeleteResult);
    }

    @Test
    void addTodoItem() throws IOException
    {
        var resultingID = cloudEditor.addTodoItem(item0);
        var expected = "{\n" +
                "  \"title\": \"" + item0.getTitle() + "\",\n" +
                "  \"owner\": \"" + item0.getOwner() + "\",\n" +
                "  \"description\": \"" + item0.getDescription() + "\",\n" +
                "  \"creation time\": \"" + item0.getCreationTime() + "\",\n" +
                "  \"deadline time\": \""  + item0.getDeadlineTime() + "\",\n" +
                "  \"status\": \"" + item0.checkIfCompleted() + "\",\n" +
                "  \"id\": " + resultingID +
                "\n}";
        var actual = cloudGetter.getTodoItemJsonString(resultingID);

        assertEquals(expected, actual);
    }


    @Test
    void clearCloud() throws IOException
    {
        cloudEditor.addTodoItem(item1);
        cloudEditor.addTodoItem(item2);
        cloudEditor.addTodoItem(item3);

        empty = cloudEditor.clearCloud();
        assertTrue(empty);
    }


    @Test
    void clearingAnEmptyCloud() throws IOException
    {
        empty = cloudEditor.clearCloud();
        assertTrue(empty);

        empty = cloudEditor.clearCloud();
        assertTrue(empty);
    }



    @Test
    void updateExistingTodoItemIncomplete() throws IOException
    {
        //changed the item 0 at the top to match the expected for the title and description but trying to update it does not work
        var resultingID = cloudEditor.addTodoItem(item0);

        var expected = "{\n" +
                "  \"title\": \"TEST TASK 0\",\n" +
                "  \"owner\": \"team4\",\n" +
                "  \"description\": \"ZERO TEST TASK\",\n" +
                "  \"creation time\": \"" + item0.getCreationTime() + "\",\n" +
                "  \"deadline time\": \""  + item0.getDeadlineTime() + "\",\n" +
                "  \"status\": \"" + item0.checkIfCompleted() + "\",\n" +
                "  \"id\": " + resultingID + "\n" +
                "}";
        var actual = cloudGetter.getTodoItemJsonString(resultingID);

        var updated = cloudEditor.updateTodoItem(item0, "test task 0",
                "zero test task 1", false, "2033-06-14T15:00");

        assertEquals(expected, actual);
        assertTrue(updated);
    }



    @Test
    void updateNotExistingTodoItem() throws IOException
    {
        var updated = cloudEditor.updateTodoItem(nonexistingItem, "hello1", "hellohello", true,
                "2020-04-05T12:30");
        assertFalse(updated);
    }


    //@Test
    //void updateExistingTodoItemComplete() throws IOException {

    //}

    
    @AfterEach
    void add3TestItemsBackIn() throws IOException
    {
        cloudEditor.clearCloud();
        cloudEditor.addTodoItem(item1);
        cloudEditor.addTodoItem(item2);
        cloudEditor.addTodoItem(item3);
    }
}


