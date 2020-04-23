package cloudutils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import todo.TodoItem;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CloudGetter
{
    private HttpRequestFactory requestFactory;
    private String baseURL = "https://todoserver-team4.herokuapp.com/todos/";

    public CloudGetter() { requestFactory = new NetHttpTransport().createRequestFactory(); }

    public String getTodoItemJsonString() throws IOException
    {
        HttpRequest getRequest = requestFactory.buildGetRequest( new GenericUrl(baseURL));
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
    }

    public String getTodoItemJsonString(int id) throws IOException
    {
        String rawResponse;
        try
        {
            HttpRequest getRequest = requestFactory.buildGetRequest(
                    new GenericUrl(baseURL + id));
            rawResponse = getRequest.execute().parseAsString();
        }
        catch (Exception e)
        {
            return null;
        }
        return rawResponse;
    }

    public boolean checkURL(){
        try{
            URL url = new URL(" https://todoserver-team4.herokuapp.com/todos/");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;

        }
        catch (Exception e){
            return false;
        }
    }


}
