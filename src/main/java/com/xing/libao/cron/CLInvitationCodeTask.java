package com.xing.libao.cron;

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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/3
 * Time: 10:20
 */
@Component
public class CLInvitationCodeTask {
    private static Set<String> hrefSet = new HashSet<String>();

    public static void main(String[] args) {
        CLInvitationCodeTask codeTask = new CLInvitationCodeTask();
        codeTask.task();
    }

    @Scheduled(fixedRate = 120000)
    public void task() {
        System.out.println("let's begin - " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Document document = null;
        try {
            document = Jsoup.connect("http://cl.bearhk.info/thread0806.php?fid=7").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36").header("Host", "cl.bearhk.info").get();
            Elements elements = document.select("tr.tr3.t_one");
            for (Element element : elements) {
                String title = element.child(1).child(0).child(0).text();
                if ((title.contains("码") || title.contains("福利") || title.contains("游客")) && !hrefSet.contains(title)) {
                    System.out.println(element.child(1).child(0).child(0).attr("abs:href") + " --- " + title);
                    hrefSet.add(title);
                }
            }
        } catch (Exception e) {
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
