package CloudUtils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import todo.TodoItem;
import todo.TodoList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.*;

public class CloudEditor
{
    private HttpRequestFactory requestFactory;
    private String baseURL = "https://todoserver222.herokuapp.com/";
    private String todosURL = baseURL + "todos/";       // https://todoserver222.herokuapp.com/team4/todos
    private String team4URL = baseURL + "team4/todos/";

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
                new GenericUrl("https://todoserver222.herokuapp.com/team4/todos"), content);
        String rawResponse = postRequest.execute().parseAsString();

        int indexOfID = rawResponse.indexOf("\"id\"");
        String IDWithEnding = rawResponse.substring(indexOfID + 6);
        String IDWithoutEnding = IDWithEnding.substring(0, IDWithEnding.length() - 2);

        return(Integer.valueOf(IDWithoutEnding));
    }


    public boolean deleteTodoItem(int id) throws IOException
    {
        HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                new GenericUrl(todosURL + id));
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


    //maybe update with DateTime stuff instead of a billion parameters
    public boolean updateTodoItem(TodoItem originalItem, String title, String description,
                                  boolean status, String duedate) throws IOException
    {
        Map<String, Object> data = new LinkedHashMap<>();

        originalItem.setTitle(title);
        data.put("title", originalItem.getTitle());

        //change we need different owners
        //originalItem.setOwner("");
        data.put("owner", originalItem.getOwner());

        originalItem.setDescription(description);
        data.put("description", originalItem.getDescription());

        originalItem.setCreationTime();
        data.put("creation time", originalItem.getCreationTime());

        /*
        originalItem.setDeadlineTime(duedate);
        data.put("deadline time", originalItem.getDeadlineTime());
         */

        //Completion time only updates to now if it is completed
        if(status == false)
        {
            originalItem.changeToIncomplete();
        }
        else
        {
            originalItem.completeItem();
        }
        data.put("completion time", originalItem.getCompletionTime());
        data.put("status", originalItem.checkIfCompleted());


        originalItem.setIdToNextAvailable();
        data.put("id", originalItem.getId());

        HttpContent content = new UrlEncodedContent(data);
        HttpRequest putRequest = requestFactory.buildPutRequest(
                new GenericUrl(todosURL + originalItem.getId()), content);
        try
        {
            String rawResponse = putRequest.execute().parseAsString();
        }
        catch (HttpResponseException hre)
        {
            return false;
        }

        return true;
    }

    public String getAllTeam4TodoItems() throws IOException
    {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl(team4URL));
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
    }

    /*
    public boolean clearCloud() throws IOException
    {
        String allItems = this.getAllTeam4TodoItems();
        List<TodoItem> =
        int indexOfID = -1;
        String IDWithEnding = null;
        int indexOfEnding = -1;
        String IDWithoutEnding = null;

        if(allItems.get(0).equals("[]"))
        {
            return(true);
        }

        for (int i = 0; i < allItems.size(); i++)
        {
            indexOfID = allItems.get(i).indexOf("\"id\"");
            IDWithEnding = allItems.get(i).substring(indexOfID + 6);
            indexOfEnding = IDWithEnding.indexOf(" ");
            IDWithoutEnding = IDWithEnding.substring(0, indexOfEnding-1);

            deleteTodoItem(Integer.valueOf(IDWithoutEnding));
        }

        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl(team4URL));
        String rawResponse = getRequest.execute().parseAsString();

        if(rawResponse.equals("[]"))
        {
            return(true);
        }
        else
        {
            return(false);
        }
    }
    */

    public static void main(String[] args) throws IOException {

        //Completed
        TodoItem a = new TodoItem("CS222", "Assignment1","2020-04-01T12:00");
        a.completeItem();

        //Pending
        TodoItem b = new TodoItem("CS222","Assignment2","2020-05-01T12:00");

        //Overdue
        TodoItem c = new TodoItem("CS222", "Assignment3", "2020-04-01T12:00");

        CloudEditor cloud = new CloudEditor();
        cloud.addTodoItem(a);
        cloud.addTodoItem(b);
        cloud.addTodoItem(c);

    }


}
