package com.xing.libao.image;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/5
 * Time: 15:49
 */
public class AiTaoTuImage {
    public static void main(String[] args) {
        AiTaoTuImage aiTaoTuImage = new AiTaoTuImage();
        aiTaoTuImage.one();
    }

    public void one(){
        HttpGet get = new HttpGet("http://www.aitaotu.com/tag/shaonv.html");
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(get);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity,"utf-8");
            System.out.println(content);
//            Document document = Jsoup.parse(content);
            Document document = Jsoup.connect("http://www.aitaotu.com/tag/shaonv.html").get();
            Elements elements = document.select("a[href]");
            for(Element element:elements){
                System.out.println(element.text() + " --- " + element.attr("abs:href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
