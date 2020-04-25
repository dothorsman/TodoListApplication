package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import exceptions.ItemException;
import exceptions.ItemIdExistsException;
import exceptions.ItemIdNotExistsException;
import todo.TodoItem;
import todo.TodoList;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoItemManager
{

    Dao<TodoItem, Integer> TodoItemDao;

    public TodoItemManager() {
        this("TodoItem.db");
    }

    public TodoItemManager(String dbName) {
        try {
            var connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + dbName);
            TableUtils.createTableIfNotExists(connectionSource, TodoItem.class);
            this.TodoItemDao = DaoManager.createDao(connectionSource, TodoItem.class);
        } catch (SQLException e) {
            throw new ItemException("Something related to connection happened!", e);
        }
    }

    public TodoItem addItem(TodoItem Item) {
        try {
            int id =Item.getId();
            if (TodoItemDao.idExists(id)){
                throw new ItemIdExistsException(id);
            }else {
                TodoItem newItem = TodoItemDao.createIfNotExists(Item);
                return newItem;
            }
        } catch (SQLException e) {
            throw new ItemException("Something happened when adding item",e);
        }
    }

    public List<TodoItem> getAllItems() {
        try {
            return TodoItemDao.queryForAll();
        } catch (SQLException e) {
            throw new ItemException("Something happened when getting all items", e);
        }
    }

    public TodoItem deleteItem(int id) {
        try {
            if(TodoItemDao.idExists(id)){
                TodoItem Item = TodoItemDao.queryForId(id);
                TodoItemDao.deleteById(id);
                return Item;
            }else{
                throw new ItemIdNotExistsException(id);
            }
        } catch (SQLException e) {
            throw new ItemException("Something happened when deleting item!", e);
        }
    }

    public TodoItem snoozeItem(int id, String newDate){
        try {
            if (TodoItemDao.idExists(id)) {
                TodoItem completeItem = TodoItemDao.queryForId(id);
                completeItem.setDeadlineTime(newDate);
                TodoItemDao.update(new TodoItem(completeItem.getTitle(), completeItem.getDescription(), newDate, id, String.valueOf(completeItem.checkIfCompleted()), "null"));
                return TodoItemDao.queryForId(id);
            } else {
                throw new ItemIdNotExistsException(id);
            }
        } catch (SQLException e) {
            throw new ItemException("Couldn't complete item.", e);
        }
    }

    public TodoItem completeItem(int id){
        try {
            if (TodoItemDao.idExists(id)) {
                TodoItem Item = TodoItemDao.queryForId(id);
                Item.completeItem();
                String deadline = Item.getDeadlineTime().toString();
                String completionTIme = Item.getCompletionTime().toString();
                TodoItemDao.update(new TodoItem(Item.getTitle(), Item.getDescription(), deadline, id, String.valueOf(Item.checkIfCompleted()), completionTIme));
                return TodoItemDao.queryForId(id);
            } else {
                throw new ItemIdNotExistsException(id);
            }
        } catch (SQLException e) {
            throw new ItemException("Couldn't complete item.", e);
        }
    }


    public void clear() throws SQLException {
        TodoItemDao.delete(TodoItemDao.queryForAll());
    }

    public void disposeResources() {
        try {
            TodoItemDao.getConnectionSource().close();
        } catch (IOException e) {
            throw new ItemException("Couldn't close the source!", e);
        }
    }

    public static void main(String[] args) throws SQLException {
        TodoItemManager database = new TodoItemManager();
        database.clear();
    }


}