package CloudUtilsTest;

import CloudUtils.CloudEditor;
import CloudUtils.CloudGetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CloudEditorTest
{
    CloudEditor cloudEditor;
    CloudGetter cloudGetter;

    @BeforeEach
    void setup()
    {
        cloudEditor = new CloudEditor();
        cloudGetter = new CloudGetter();
    }

    @Test
    void addTodoItem() throws IOException
    {
        var resultingID = cloudEditor.addTodoItem("fake task");
        var expected = "{\n" +
                "  \"title\": \"fake task\",\n" +
                "  \"owner\": \"team4\",\n" +
                "  \"id\": " + resultingID + "\n" +
                "}";
        var actual = cloudGetter.getTodoItemJsonString(resultingID);

        assertEquals(expected, actual);
    }

    @Test
    void deleteExistingTodoItem() throws IOException {
        var resultingID = cloudEditor.addTodoItem("hello task");
        var deleteResult = cloudEditor.deleteTodoItem(resultingID);
        assertTrue(deleteResult);
    }

    @Test
    void deleteNotExistingTodoItem() throws IOException {
        var nonExistingIDdeleteResult = cloudEditor.deleteTodoItem(152434354);
        assertFalse(nonExistingIDdeleteResult);
    }


    @Test
    void updateExistingTodoItem() throws IOException {
        var resultingID = cloudEditor.addTodoItem("test task 1");
        var updated = cloudEditor.updateTodoItem(resultingID, "test task 2");

        assertTrue(updated);

        var expected = "{\n" +
                "  \"id\": " + resultingID + ",\n" +
                "  \"title\": \"test task 2\",\n" +
                "  \"owner\": \"team4\"\n" +
                "}";
        var actual = cloudGetter.getTodoItemJsonString(resultingID);
        assertEquals(expected, actual);
    }

    @Test
    void updateNotExistingTodoItem() throws IOException
    {
        var updated = cloudEditor.updateTodoItem(214323, "Hello1");
        assertFalse(updated);
    }
}
