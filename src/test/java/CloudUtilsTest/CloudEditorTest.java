package CloudUtilsTest;

import CloudUtils.CloudEditor;
import CloudUtils.CloudGetter;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.TodoItem;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
 /*
public class CloudEditorTest
{
    CloudEditor cloudEditor;
    CloudGetter cloudGetter;

    TodoItem item0;
    TodoItem item1;
    TodoItem item2;

    Boolean empty;


        Important note: running CloudEditorTest will result in any
        TodoItems that were on the cloud prior to be removed from
        https://todoserver222.herokuapp.com/team4/todos


    @BeforeEach
    void setup() throws IOException
    {
        cloudEditor = new CloudEditor();
        cloudGetter = new CloudGetter();

        item0 = new TodoItem("test task 0", "just for testing 0",
                "2020-5-18-12-30");
        item1 = new TodoItem("test task 1", "just for testing 2",
                "2020-4-18-12-30");
        item2 = new TodoItem("test task 2", "just for testing 2",
                "2020-3-18-12-30");
    }



    @Test
    void addTodoItem() throws IOException {
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

        empty = cloudEditor.clearCloud();
        assertTrue(empty);
    }



    @Test
    void deleteExistingTodoItem() throws IOException
    {
        empty = cloudEditor.clearCloud();
        assertTrue(empty);

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
    void updateExistingTodoItem() throws IOException
    {
        var resultingID = cloudEditor.addTodoItem(item0);

        //maybe reduce parameters
        var updated = cloudEditor.updateTodoItem(item0, "TEST TASK 0", "JUST FOR TESTING 0", true,
                "2020-04-05-12-30");

        assertTrue(updated);

        var expected = "{\n" +
                "  \"title\": \"test task 2\",\n" +
                "  \"owner\": \"team4\",\n" +
                "  \"id\": " + resultingID + "\n" +
                "}";
        var actual = cloudGetter.getTodoItemJsonString(resultingID);
        assertEquals(expected, actual);
    }

    @Test
    void updateNotExistingTodoItem() throws IOException
    {

        var updated = cloudEditor.updateTodoItem(item1, "hello1", "hellohello", true,
                "2020-04-05-12-30");
        assertFalse(updated);
    }

    @Test
    void clearCloud() throws IOException
    {
        cloudEditor.addTodoItem(item0);
        cloudEditor.addTodoItem(item1);
        cloudEditor.addTodoItem(item2);

        var empty = cloudEditor.clearCloud();
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
    void getAllTeam4TodoItems() throws IOException
    {
        empty = cloudEditor.clearCloud();
        assertTrue(empty);

        cloudEditor.addTodoItem(item0);
        cloudEditor.addTodoItem(item1);
        cloudEditor.addTodoItem(item2);
        var actual = cloudEditor.getAllTeam4TodoItems().size();
        assertEquals(3, actual);

        empty = cloudEditor.clearCloud();
        assertTrue(empty);
    }
}
*/
