package cloudutils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import todo.TodoItem;
import todo.TodoList;

import java.io.IOException;
import java.util.*;

public class CloudEditor
{
    private HttpRequestFactory requestFactory;
    private String baseURL = "https://todoserver-team4.herokuapp.com/todos/";


    public CloudEditor()
    {
        requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public int addTodoItem(TodoItem item) throws IOException
    {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("title", item.getTitle());
        data.put("owner", item.getOwner());
        data.put("description", item.getDescription());
        data.put("creation time", item.getCreationTime());
        data.put("deadline time", item.getDeadlineTime());
        data.put("completion time", item.getCompletionTime());
        data.put("status", item.checkIfCompleted());

        HttpContent content = new UrlEncodedContent(data);
        HttpRequest postRequest = requestFactory.buildPostRequest(
                new GenericUrl(baseURL), content);
        String rawResponse = postRequest.execute().parseAsString();

        int indexOfID = rawResponse.indexOf("\"id\"");
        String IDWithEnding = rawResponse.substring(indexOfID + 6);
        String IDWithoutEnding = IDWithEnding.substring(0, IDWithEnding.length() - 2);

        return(Integer.valueOf(IDWithoutEnding));
    }


    public boolean deleteTodoItem(int id) throws IOException
    {
        HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                new GenericUrl(baseURL + id));
        try
        {
            String rawResponse = deleteRequest.execute().parseAsString();
        }
        catch (HttpResponseException hre)
        {
            return false;
        }
        return true;
    }

    public boolean updateTodoItem(TodoItem originalItem, String newTitle, String newDescription,
                               boolean newStatus, String newDeadline) throws IOException
    {
        Map<String, Object> data = new LinkedHashMap<>();

        originalItem.updateItem(newTitle, newDescription, newStatus, newDeadline);

        data.put("title", originalItem.getTitle());
        data.put("owner", originalItem.getOwner());
        data.put("description", originalItem.getDescription());
        data.put("creation time", originalItem.getCreationTime());
        data.put("deadline time", originalItem.getDeadlineTime());
        data.put("completion time", originalItem.getCompletionTime());
        data.put("status", originalItem.checkIfCompleted());

        //originalItem.setIdToNextAvailable(); //will need to change with the id system update
        data.put("id", originalItem.getId());

        HttpContent content = new UrlEncodedContent(data);

        HttpRequest putRequest = requestFactory.buildPutRequest(
                new GenericUrl(baseURL + originalItem.getId()), content); //will need to be update with the new id system
        try
        {
            String rawResponse = putRequest.execute().parseAsString();

        }
        catch(HttpResponseException hre)
        {
            return(false);
        }

        return(true);
    }

    public boolean clearCloud() throws IOException
    {
        JsonParser jsonParser = new JsonParser();
        CloudGetter getter = new CloudGetter();

        if(getter.getTodoItemJsonString().equals("[]"))
        {
            return(true);
        }

        JsonElement rootElement = jsonParser.parse(getter.getTodoItemJsonString());
        JsonArray rootObjects = rootElement.getAsJsonArray();
        for (JsonElement rootObject : rootObjects){
            var number = rootObject.getAsJsonObject().getAsJsonPrimitive("id").getAsInt();
            deleteTodoItem(number);
        }

        if(getter.getTodoItemJsonString().equals("[]"))
        {
            return(true);
        }

        return(false);
    }
}
