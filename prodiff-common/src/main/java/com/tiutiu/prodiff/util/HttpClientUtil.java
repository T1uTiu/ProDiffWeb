package com.tiutiu.prodiff.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Map;

public class HttpClientUtil {
    public static void doGet(String url, Map<String, String> param){
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            CloseableHttpResponse response;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void doPost(String url, Map<String, String> param){
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            CloseableHttpResponse response;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
