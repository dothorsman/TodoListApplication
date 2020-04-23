package piechart;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

public class DataFetcher {

    public static String getRawJSONfromAPI() throws Exception {
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl("https://todoserver-team4.herokuapp.com/todos"));
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
    }

}