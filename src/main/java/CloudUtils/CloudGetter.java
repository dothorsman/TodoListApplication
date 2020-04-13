package CloudUtils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;

public class CloudGetter
{
    private HttpRequestFactory requestFactory;
    private String baseURL = "https://todoserver222.herokuapp.com";
    private String team4URL = "/team4/todos";

    public CloudGetter() { requestFactory = new NetHttpTransport().createRequestFactory(); }

    public String getTodoItemJsonString() throws IOException
    {
        HttpRequest getRequest = requestFactory.buildGetRequest( new GenericUrl(baseURL + team4URL));
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
    }

    public String getTodoItemJsonString(int id) throws IOException
    {
        String rawResponse;
        try
        {
            HttpRequest getRequest = requestFactory.buildGetRequest(
                    new GenericUrl("https://todoserver222.herokuapp.com/todos/" + id));
            rawResponse = getRequest.execute().parseAsString();
        }
        catch (Exception e)
        {
            return null;
        }

        return rawResponse;
    }
}
