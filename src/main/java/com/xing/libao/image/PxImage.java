package com.xing.libao.image;

import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/4
 * Time: 17:35
 */
public class PxImage {
    private String url = "https://prime.500px.com/api/photos?category=travel&filters=landscape&availability=related&sort=highest_rating&per_page=40&page=1";
    public static void main(String[] args) {
        PxImage pxImage = new PxImage();
        pxImage.one();
    }

    public void one(){
        ObjectMapper mapper = new ObjectMapper();
        String content = getContent(url);
        try {
            JsonNode resumeNode = mapper.readTree(content);
            JsonNode array = resumeNode.get("data");
            for(int i=0;i<array.size();i++){
                System.out.println(array.get(i).get("images").get(4).get("url").asText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getContent(String uri) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(uri);
        String content = "";
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode() + " url: " + uri);
            }
            HttpEntity re = response.getEntity();
            content = EntityUtils.toString(re, "gbk");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
