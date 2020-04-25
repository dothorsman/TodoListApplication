package cloudutils;


import com.google.gson.*;
import exceptions.CloudParserException;
import todo.TodoItem;
import todo.TodoList;


public class CloudParser
{

    public TodoList parseJsonTodoItem(String jsonString)
    {
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonArray rootObjects = rootElement.getAsJsonArray();
        TodoList todoList = new TodoList();
        for (JsonElement rootObject : rootObjects) {
            var title = rootObject.getAsJsonObject().getAsJsonPrimitive("title").getAsString();
            var description = rootObject.getAsJsonObject().getAsJsonPrimitive("description").getAsString();
            var duedate = rootObject.getAsJsonObject().getAsJsonPrimitive("deadline time").getAsString();
            var creationtime = rootObject.getAsJsonObject().getAsJsonPrimitive("creation time").getAsString();
            var completiontime = rootObject.getAsJsonObject().getAsJsonPrimitive("completion time").getAsString();
            var id = rootObject.getAsJsonObject().getAsJsonPrimitive("id").getAsInt();
            var status = rootObject.getAsJsonObject().getAsJsonPrimitive("status").getAsBoolean();
            String status_string = String.valueOf(status);
            if (completiontime.isEmpty()){
                TodoItem todoItem = new TodoItem(title,description,duedate, id, status_string, "null");
                todoItem.setCreationTimeBy(creationtime);
                todoItem.setStatus(status);
                todoList.addItemToTodoList(todoItem);
            }else {
                TodoItem todoItem = new TodoItem(title,description,duedate, id, status_string, completiontime);
                todoItem.setCreationTimeBy(creationtime);
                todoItem.setStatus(status);
                todoList.addItemToTodoList(todoItem);
            }
        }
        return todoList;
    }


}
