package com.xing.libao.cron;

import com.xing.libao.bean.Code;
import com.xing.libao.dao.CodeDao;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/4/1
 * Time: 18:13
 */
@Component
public class CL115Task {
    private static Set<String> hrefSet = new HashSet<String>();
    @Autowired
    CodeDao codeDao;

    public static void main(String[] args) {

//            Document document = Jsoup.connect("http://wo.yao.cl/htm_data/7/1503/1436195.html").get();
//            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//            HttpGet get = new HttpGet("http://wo.yao.cl/htm_data/7/1503/1436195.html");
//            CloseableHttpResponse response = httpClient.execute(get);
//            if (response.getStatusLine().getStatusCode() != 200) {
//                System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode());
//                System.exit(0);
//            }
//            HttpEntity re = response.getEntity();
//            String content = EntityUtils.toString(re, "gbk");
//            System.out.println(content);
        String content = "14px\" href=\"http://www.viidii.info/?http://115______com/lb/5lbc6cwi6lqf&z\">http://115.com/lb/5lbc6cwi6lqf</a><br><br> ";
        Pattern pattern = Pattern.compile("http:\\/\\/115\\.com/lb/5[0-9a-z]+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    //
    public void test() {
        Document document = null;
        System.out.println("let's begin - " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            document = Jsoup.connect("http://www.t66y.com/thread0806.php?fid=7").get();
            Elements elements = document.select("a[href]");
            for (Element element : elements) {
                if (element.text().equals(".::")) {
                    if (!hrefSet.contains(element.attr("abs:href"))) {
                        hrefSet.add(element.attr("abs:href"));
                        System.out.println("get: " + element.attr("abs:href") + " - " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        String content = getContent(element.attr("abs:href"));
                        Pattern pattern = Pattern.compile("http:\\/\\/115\\.com/lb/5[0-9a-z]+");
                        Matcher matcher = pattern.matcher(content);
                        while (matcher.find()) {
                            String url115 = matcher.group();
                            System.out.println("115 haha: " + url115);
                            String code = url115.substring(url115.lastIndexOf("/") + 1, url115.length());
                            if (codeDao.countByCode(code) != 1) {
                                System.out.println(saveLiBao(url115, code, codeDao));
                            }
                        }
                    } else {
                        System.out.println("already had " + " - " + element.attr("abs:href") + " - " + element.text());
                    }
                }
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

    private static int saveLiBao(String url, String code, CodeDao codeDao) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            return 0;
        }
        HttpGet get = new HttpGet(uri);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
        } catch (IllegalStateException e) {
            return 0;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        HttpEntity re = response.getEntity();
        String content = null;
        try {
            content = EntityUtils.toString(re, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        Document document = Jsoup.parse(content);
        if (document.select("div.spree-info>em") == null || document.select("div.spree-info>em").first() == null) {
            return 0;
        }
        String size = document.select("div.spree-info>em").first().text();
        if (size.contains("kb") || size.contains("KB")) {
            return 0;
        }
        if (size.contains("MB")) {
            String temp = size.replace("MB", "").replace("大小：", "");
            try {
                double dSize = Double.parseDouble(temp);
                if (dSize < 100) {
                    return 0;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println(temp);
                return 0;
            }
        }
        size = size.replace("大小：", "");
        Element element = document.select("div.spree-info>span").first();
        if (element == null) {
            return 0;
        }
        String title = element.text();
        Code code1 = new Code();
        code1.setCode(code);
        code1.setSize(size);
        code1.setTitle(title);
        code1.setTime((int) (new Date().getTime() / 1000));
        System.out.println(code1.toString());
        return codeDao.saveCode(code1);
    }
}
