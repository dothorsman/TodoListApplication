package todo;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reminder
{
    private HttpRequestFactory requestFactory;
    private String baseURL = "https://todoserver-team4.herokuapp.com/todos/";

    HashMap<String, String> mapOfDeadlines;
    HashMap<String, String> mapOfOverdueDeadlines;

    String ReminderInformation = "";

    public Reminder()
    {
        requestFactory = new NetHttpTransport().createRequestFactory();
        mapOfDeadlines = new HashMap<String, String>();
        mapOfOverdueDeadlines = new HashMap<String, String>();
    }

    //M <title, deadline> --> sets mapOfDeadlines to current deadlines
    public void updateDeadlines() throws IOException
    {
        HttpRequest getRequest = requestFactory.buildGetRequest( new GenericUrl(baseURL));
        String rawResponse = getRequest.execute().parseAsString();

        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(rawResponse);
        JsonArray rootObjects = rootElement.getAsJsonArray();
        mapOfDeadlines.clear(); //so that there arent leftover deadlines from the previous 5 seconds
        for (JsonElement rootObject : rootObjects)
        {
            var title = rootObject.getAsJsonObject().getAsJsonPrimitive("title").getAsString();
            var deadline = rootObject.getAsJsonObject().getAsJsonPrimitive("deadline time").getAsString();

            mapOfDeadlines.put(title, deadline);
        }
    }


    //returns a map of overdue deadlines
    public void updateOverdueDeadlines(HashMap<String, String> mapOfDeadlines)
    {
        mapOfOverdueDeadlines.clear(); //so that there arent leftover deadlines from the previous 5 seconds



        for (Map.Entry<String, String> pair: mapOfDeadlines.entrySet())
        {
            LocalDateTime date = LocalDateTime.parse(pair.getValue(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            //check if value is overdue (must change pair.getValue() to a DateTime object or somethin else)
            if(date.isBefore(LocalDateTime.now()))
            {
                mapOfOverdueDeadlines.put(pair.getKey(), pair.getValue());
            }
        }
    }


    public HashMap<String, String> getDeadlines()
    {
        return(mapOfDeadlines);
    }

    public HashMap<String, String> getOverdueDeadlines()
    {
        return(mapOfOverdueDeadlines);
    }



    public List<TodoItem> getOverDueItems(TodoList list){
        List<TodoItem> Itemlist = list.getItemsInTodoList();
        List<TodoItem> OverdueItems = new ArrayList<>();
        for (TodoItem Item : Itemlist){
            if (Item.getDeadlineTime().isBefore(LocalDateTime.now())){
                if (!Item.checkIfCompleted()) {
                    OverdueItems.add(Item);
                }
            }
        }
        return OverdueItems;
    }

    public List<TodoItem> getDueWithin24HoursItems(TodoList list){
        List<TodoItem> Itemlist = list.getItemsInTodoList();
        List<TodoItem> DueWithin24HoursItems = new ArrayList<>();
        for (TodoItem Item : Itemlist){
            if (Item.getDeadlineTime().isAfter(LocalDateTime.now())){
                Duration duration = Duration.between(LocalDateTime.now(), Item.getDeadlineTime());
                long Hours = duration.toHours();
                if (Hours < 24){
                    DueWithin24HoursItems.add(Item);
                }
            }
        }
        return DueWithin24HoursItems;
    }

    public String getRemindeInformation(List<TodoItem> Overdue, List<TodoItem> DueWithin24Hours) {
        ReminderInformation = "";
        //Overdue Items
        if (Overdue.size() == 0) {
            ReminderInformation += "There are currently no to-do items that have expired";
        } else {
            ReminderInformation += "Here are expired to-do items:";
            for (TodoItem Item : Overdue) {
                ReminderInformation += "\r\n" + "Title: " + Item.getTitle() + "\r\n" + "DeadlineTime: " + Item.getDeadlineTime() + "\r\n";
            }
        }
        //Due within 24 hours items
        if (DueWithin24Hours.size() == 0){
            ReminderInformation += "\r\n"+ "There are currently no to-do items that will expire within 24 hours";
        }else {
            ReminderInformation += "\r\n"+ "Here are the to-do items that will expire in 24 hours:";
            for (TodoItem Item : DueWithin24Hours){
                ReminderInformation += "\r\n" + "Title: " + Item.getTitle() + "\r\n" + "DeadlineTime: " + Item.getDeadlineTime() + "\r\n";
            }
        }
        return ReminderInformation;
    }

}
