package todo;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reminder
{
    private HttpRequestFactory requestFactory;
    private String baseURL = "https://todoserver-team4.herokuapp.com/todos/";

    HashMap<String, String> mapOfDeadlines;
    HashMap<String, String> mapOfOverdueDeadlines;

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



    //another method?





    public boolean checkURL() {
        try {
            URL url = new URL(" https://todoserver-team4.herokuapp.com/todos/");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
