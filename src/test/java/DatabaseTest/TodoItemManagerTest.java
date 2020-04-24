package DatabaseTest;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import database.TodoItemManager;
import exceptions.ItemIdNotExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.TodoItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemManagerTest {

    TodoItemManager ItemManager;

    @BeforeEach
    void setupTodoItemManagerAndAddItems() throws SQLException, IOException {

        var testConnection = new JdbcConnectionSource("jdbc:sqlite:test.db");
        TableUtils.dropTable(testConnection, TodoItem.class, true);
        TableUtils.createTableIfNotExists(testConnection, TodoItem.class);
        Dao<TodoItem, Integer> TodoItemDao = DaoManager.createDao(testConnection, TodoItem.class);
        TodoItemDao.create(new TodoItem("Assignment1", "homework1" , "2020-04-15T12:00",1));
        TodoItemDao.create(new TodoItem("Assignment2", "homework2" , "2020-04-16T12:00",2));
        TodoItemDao.create(new TodoItem("Assignment3", "homework3" , "2020-04-17T12:00",3));
        testConnection.close();
        ItemManager = new TodoItemManager("test.db");
    }

    @Test
    void getAllQuotes() {
        var allActualItems = ItemManager.getAllItems();
        var expectedItem1 = new TodoItem("Assignment1", "homework1" , "2020-04-15T12:00",1);
        assertEquals(expectedItem1.getTitle(),allActualItems.get(0).getTitle());
        assertEquals(expectedItem1.getDescription(),allActualItems.get(0).getDescription());
        assertEquals(expectedItem1.getDeadlineTime(),allActualItems.get(0).getDeadlineTime());
        assertEquals(expectedItem1.checkIfCompleted(),allActualItems.get(0).checkIfCompleted());
    }

    @Test
    void addQuote_Success() {
        var newItem = new TodoItem("Assignment4", "homework4" , "2020-04-18T12:00",4);
        var resultingItem = ItemManager.addItem(newItem);
        assertEquals(newItem, resultingItem);
    }

    @Test
    void deleteItem_NotExistingId() {
        assertEquals(3, ItemManager.getAllItems().size());
        assertThrows(ItemIdNotExistsException.class, () -> {
            var resultingQuote = ItemManager.deleteItem(123);
        });
    }

    @Test
    void deleteQuote_Success() {
        assertEquals(3, ItemManager.getAllItems().size());
        var expectedQuote = new TodoItem("Assignment3", "homework3" , "2020-04-17T12:00",3);
        var resultingQuote = ItemManager.deleteItem(3);
        assertEquals(expectedQuote.getTitle(),resultingQuote.getTitle());
        assertEquals(expectedQuote.getDescription(),resultingQuote.getDescription());
        assertEquals(expectedQuote.getDeadlineTime(),resultingQuote.getDeadlineTime());
        assertEquals(expectedQuote.checkIfCompleted(),resultingQuote.checkIfCompleted());
        assertEquals(2, ItemManager.getAllItems().size());
    }

    @AfterEach
    void disposeDB() {
        ItemManager.disposeResources();
        File dbFile = new File(Paths.get(".").normalize().toAbsolutePath() + "\\test.db");
        dbFile.delete();
    }


}