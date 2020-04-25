package CloudUtilsTest;


import cloudutils.CloudEditor;
import cloudutils.CloudGetter;
import database.TodoItemManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.TodoItem;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CloudGetterTest
{
    CloudGetter cloudGetter;
    CloudEditor cloudEditor;
    TodoItemManager database = new TodoItemManager();


    TodoItem item1 = new TodoItem("test task 1",
            "first test task, This should not be deleted.", "2020-05-18T12:30",1,"false", "null");
    TodoItem item2 = new TodoItem("test task 2",
            "second test task, This should not be deleted.", "2020-04-18T01:30",2, "false", "null");
    TodoItem item3 = new TodoItem("test task 3",
            "third test task, This should not be deleted.", "2021-04-18T15:30",3,"false" , "null");

    @BeforeEach
    void setup() throws IOException, SQLException {


        cloudGetter = new CloudGetter();
        cloudEditor = new CloudEditor();
        database.clear();
        cloudEditor.clearCloud();

        cloudEditor.addTodoItem(item1);
        cloudEditor.addTodoItem(item2);
        cloudEditor.addTodoItem(item3);

        database.addItem(item1);
        database.addItem(item2);
        database.addItem(item3);
    }


    @Test
    void getTodoItemJsonString() throws IOException
    {
        var result = cloudGetter.getTodoItemJsonString();
        assertEquals("[\n" +
                        "  {\n" +
                        "    \"title\": \"" + item1.getTitle() + "\",\n" +
                        "    \"owner\": \"" + item1.getOwner() + "\",\n" +
                        "    \"description\": \"" + item1.getDescription() + "\",\n" +
                        "    \"creation time\": \"" + item1.getCreationTime() + "\",\n" +
                        "    \"deadline time\": \""  + item1.getDeadlineTime() + "\",\n" +
                        "    \"completion time\": \"" + item1.getCompletionTime() + "\",\n" +
                        "    \"status\": \"" + item1.checkIfCompleted() + "\",\n" +
                        "    \"id\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"" + item2.getTitle() + "\",\n" +
                        "    \"owner\": \"" + item2.getOwner() + "\",\n" +
                        "    \"description\": \"" + item2.getDescription() + "\",\n" +
                        "    \"creation time\": \"" + item2.getCreationTime() + "\",\n" +
                        "    \"deadline time\": \""  + item2.getDeadlineTime() + "\",\n" +
                        "    \"completion time\": \"" + item2.getCompletionTime() + "\",\n" +
                        "    \"status\": \"" + item2.checkIfCompleted() + "\",\n" +
                        "    \"id\": 2\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"" + item3.getTitle() + "\",\n" +
                        "    \"owner\": \"" + item3.getOwner() + "\",\n" +
                        "    \"description\": \"" + item3.getDescription() + "\",\n" +
                        "    \"creation time\": \"" + item3.getCreationTime() + "\",\n" +
                        "    \"deadline time\": \""  + item3.getDeadlineTime() + "\",\n" +
                        "    \"completion time\": \"" + item3.getCompletionTime() + "\",\n" +
                        "    \"status\": \"" + item3.checkIfCompleted() + "\",\n" +
                        "    \"id\": 3\n" +
                        "  }\n" +
                        "]"
                , result);
    }

    @Test
    void getTodoItemJsonStringWithExistingId() throws IOException
    {
        var result = cloudGetter.getTodoItemJsonString(1);
        assertEquals("{\n" +
                        "  \"title\": \"" + item1.getTitle() + "\",\n" +
                        "  \"owner\": \"" + item1.getOwner() + "\",\n" +
                        "  \"description\": \"" + item1.getDescription() + "\",\n" +
                        "  \"creation time\": \"" + item1.getCreationTime() + "\",\n" +
                        "  \"deadline time\": \""  + item1.getDeadlineTime() + "\",\n" +
                        "  \"completion time\": \"" + item1.getCompletionTime() + "\",\n" +
                        "  \"status\": \"" + item1.checkIfCompleted() + "\",\n" +
                        "  \"id\": 1\n" +
                        "}"
                , result);
    }

    @Test
    void getTodoItemJsonStringWithNotExistingId() throws IOException
    {
        var result = cloudGetter.getTodoItemJsonString(100000000);
        assertEquals(null, result);
    }

    @AfterEach
    void reset() throws IOException
    {
        cloudEditor.clearCloud();
        cloudEditor.addTodoItem(item1);
        cloudEditor.addTodoItem(item2);
        cloudEditor.addTodoItem(item3);
    }

}



