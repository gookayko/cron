package com.xing.libao.cron;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/3
 * Time: 11:11
 */
@Component
public class RefreshContent {
    private static String uri = "http://cl.bearhk.info/htm_data/7/1505/1505144.html";
    private String content = null;

    public static void main(String[] args) {
        RefreshContent refreshContent = new RefreshContent();
        refreshContent.task();
    }

    //@Scheduled(fixedRate = 120000)
    public void task() {
        System.out.println("let's begin RefreshContent - " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String contentTemp = getContent(uri);
        Document document = Jsoup.parse(contentTemp);
        Elements elements = document.select("div.t.t2>table>tbody>tr.tr3.tr1>th.r_one");
        Element element = elements.first();
        contentTemp = element.text();
        if(StringUtils.isNotEmpty(contentTemp)) {
            if (content == null) {
                content = contentTemp;
            } else {
                if (!contentTemp.equals(content)) {
                    System.out.println(contentTemp);
                }
            }
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
