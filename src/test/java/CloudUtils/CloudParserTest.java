package CloudUtils;

import exceptions.CloudParserException;
import org.junit.jupiter.api.Test;
import todo.TodoItem;
import todo.TodoList;

import static org.junit.jupiter.api.Assertions.*;

class CloudParserTest {

    @Test
    void parseJsonTodoItem() throws CloudParserException {
        TodoItem item0;
        item0 = new TodoItem("test task 0", "just for testing 0",
                2020, 5, 18, 12, 30);
        var resultingList = CloudParser.parseJsonTodoItem(item0);
        assertEquals("test task 0", resultingList.getTitle());
        assertEquals("just for testing 0", resultingList.getDescription());
        assertEquals(2020, resultingList.getDeadlineYear());
        assertEquals(5,resultingList.getDeadlineMonth());
        assertEquals(18,resultingList.getDeadlineDate());
        assertEquals(12,resultingList.getDeadlineHour());
        assertEquals(30,resultingList.getDeadlineMinute());
        }
    }
