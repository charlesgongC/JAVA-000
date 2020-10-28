package main.java.java0.nio01;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientUtils {


    public static String doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity());
            response.close();
            return result;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        String url = "http://localhost:8801";
        System.out.println(doGet(url));
    }
}
