package CloudUtils;


import com.google.gson.*;
import exceptions.CloudParserException;
import todo.TodoItem;
import todo.TodoList;

import java.util.ArrayList;
import java.util.List;


public class CloudParser
{
    //parse method creates a TodoList
    public static TodoItem parseJsonTodo(String jsonString) throws CloudParserException.ParameterIsNotJsonStringException
    {
        if(!(jsonString.charAt(0) == '{'))
        {
            throw new CloudParserException.ParameterIsNotJsonStringException();
        }

        Gson gson = new Gson();
        TodoItem todoObject = gson.fromJson(jsonString, TodoItem.class);
        return todoObject;
    }

    public TodoList parseJsonTodoItem(String jsonString)
    {
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonArray rootObjects = rootElement.getAsJsonArray();
        TodoList todoList = new TodoList();


        TodoItem todoItem = new TodoItem(null,null,null);

        for (JsonElement rootObject : rootObjects) {
            var title = rootObject.getAsJsonObject().getAsJsonPrimitive("title").getAsString();
            var description = rootObject.getAsJsonObject().getAsJsonPrimitive("description").getAsString();
            var year = rootObject.getAsJsonObject().getAsJsonPrimitive("year").getAsInt();
            var month = rootObject.getAsJsonObject().getAsJsonPrimitive("month").getAsInt();
            var date = rootObject.getAsJsonObject().getAsJsonPrimitive("date").getAsInt();
            var hour = rootObject.getAsJsonObject().getAsJsonPrimitive("hour").getAsInt();
            var minute = rootObject.getAsJsonObject().getAsJsonPrimitive("minute").getAsInt();

            todoItem.setTitle(title);
            todoItem.setDescription(description);
            todoList.addItemToTodoList(todoItem);
        }
        return todoList;
    }


}
