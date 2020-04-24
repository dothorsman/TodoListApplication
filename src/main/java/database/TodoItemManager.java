package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import exceptions.ItemException;
import exceptions.ItemIdExistsException;
import exceptions.ItemIdNotExistsException;
import todo.TodoItem;

import java.io.IOException;
import java.sql.SQLException;
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

}