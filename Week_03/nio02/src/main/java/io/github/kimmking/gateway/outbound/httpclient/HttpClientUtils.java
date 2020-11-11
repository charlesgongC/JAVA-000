package io.github.kimmking.gateway.outbound.httpclient;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public static String doGet(String url, List<Map.Entry<String, String>> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> map:
             headers) {
            httpGet.setHeader(map.getKey(),map.getValue());
        }
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
