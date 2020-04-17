package CloudUtils;


import com.google.gson.*;
import exceptions.CloudParserException;
import todo.TodoItem;

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


    public static TodoItem parseJsonTodoItem(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        JsonElement rootelement = jsonParser.parse(jsonString);
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonArray rootObjects = rootElement.getAsJsonArray();

        List<TodoItem> todoItemList = new ArrayList<>();

        for (JsonElement rootObject : rootObjects) {
            var title = rootObject.getAsJsonObject().getAsJsonPrimitive("title").getAsString();
            var description = rootObject.getAsJsonObject().getAsJsonPrimitive("description").getAsString();
            var year = rootObject.getAsJsonObject().getAsJsonPrimitive("year").getAsInt();
            var month = rootObject.getAsJsonObject().getAsJsonObject().getAsJsonPrimitive("month").getAsInt();
            var date = rootObject.getAsJsonObject().getAsJsonObject().getAsJsonPrimitive("date").getAsInt();
            var hour = rootObject.getAsJsonObject().getAsJsonObject().getAsJsonPrimitive("hour").getAsInt();
            var minute = rootObject.getAsJsonObject().getAsJsonObject().getAsJsonPrimitive("minute").getAsInt();

            TodoItem todoItem = new TodoItem(title, description, year, month, date, hour, minute);
            todoItemList.add(todoItem);

            return todoItem;
        }
        return (TodoItem) todoItemList;

    }

}
