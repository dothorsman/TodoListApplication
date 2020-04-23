package CloudUtilsTest;


import cloudutils.CloudGetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CloudGetterTest
{
    CloudGetter cloudGetter;

    @BeforeEach
    void setup() { cloudGetter = new CloudGetter(); }


    @Test
    void getTodoItemJsonString() throws IOException
    {
        var result = cloudGetter.getTodoItemJsonString();
        assertEquals("[\n" +
                        "  {\n" +
                        "    \"title\": \"test task 1\",\n" +
                        "    \"owner\": \"team4\",\n" +
                        "    \"description\": \"first test task, This should not be deleted.\",\n" +
                        "    \"creation time\": \"2020-04-22T12:07:50.592819100\",\n" +
                        "    \"deadline time\": \"2020-05-18T12:30\",\n" +
                        "    \"status\": \"false\",\n" +
                        "    \"id\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"test task 2\",\n" +
                        "    \"owner\": \"team4\",\n" +
                        "    \"description\": \"second test task, This should not be deleted.\",\n" +
                        "    \"creation time\": \"2020-04-22T12:07:50.606775400\",\n" +
                        "    \"deadline time\": \"2020-04-18T12:30\",\n" +
                        "    \"status\": \"false\",\n" +
                        "    \"id\": 2\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"test task 3\",\n" +
                        "    \"owner\": \"team4\",\n" +
                        "    \"description\": \"third test task, This should not be deleted.\",\n" +
                        "    \"creation time\": \"2020-04-22T12:07:50.606775400\",\n" +
                        "    \"deadline time\": \"2021-03-18T12:30\",\n" +
                        "    \"status\": \"false\",\n" +
                        "    \"id\": 3\n" +
                        "  }\n" +
                        "]"
                , result);
    }

    @Test
    void getTodoItemJsonStringWithExistingId() throws IOException
    {
        var result = cloudGetter.getTodoItemJsonString(1);
        assertEquals("{\n" +
                        "  \"title\": \"test task 1\",\n" +
                        "  \"owner\": \"team4\",\n" +
                        "  \"description\": \"first test task, This should not be deleted.\",\n" +
                        "  \"creation time\": \"2020-04-22T12:07:50.592819100\",\n" +
                        "  \"deadline time\": \"2020-05-18T12:30\",\n" +
                        "  \"status\": \"false\",\n" +
                        "  \"id\": 1\n" +
                        "}"
                , result);
    }

    @Test
    void getTodoItemJsonStringWithNotExistingId() throws IOException
    {
        var result = cloudGetter.getTodoItemJsonString(100000000);
        assertEquals(null, result);
    }

}



