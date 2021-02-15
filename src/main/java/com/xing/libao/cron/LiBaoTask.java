package com.xing.libao.cron;

import com.xing.libao.bean.Code;
import com.xing.libao.dao.CodeDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/3/25
 * Time: 13:54
 */
@Component
public class LiBaoTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        CodeDao codeDao = (CodeDao) applicationContext.getBean("codeDaoImpl");
        String url;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet();
        int begin = 0;
        if (StringUtils.isNotEmpty(args[0])) {
            begin = Integer.parseInt(args[0]);
        } else {
            System.out.println("传参啊我去");
            return;
        }
        for (int i = begin; i < 9800; i++) {
            url = "http://libaoke.com/page/" + i;
            System.out.println("page: " + i);
            URI uri = new URI(url);
            get.setURI(uri);
            get.setHeader("Cookie","CNZZDATA5732619=cnzz_eid%3D1911017133-1426580762-%26ntime%3D1427780942");
            get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
            CloseableHttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode() + " url: " + uri.toString());
                System.exit(0);
            }
            HttpEntity re = response.getEntity();
            String content = EntityUtils.toString(re, "gbk");
            Document document = Jsoup.parse(content);
            Elements elements = document.select("table.lbtable>tbody>tr>td>a");
            for (Element element : elements) {
                String newUrl = "http://libaoke.com" + element.attr("href");
                URI newUri = new URI(newUrl);
                get.setURI(newUri);
                CloseableHttpResponse responseNew = httpClient.execute(get);
                if (responseNew.getStatusLine().getStatusCode() != 200) {
                    System.out.println("访问网站出错！ code: " + responseNew.getStatusLine().getStatusCode() + " url: " + newUri.toString());
                    System.exit(0);
                }
                HttpEntity reNew = responseNew.getEntity();
                String contentNew = EntityUtils.toString(reNew, "gbk");
                Document documentNew = Jsoup.parse(contentNew);
                Elements elements1 = documentNew.select("div.link>a");
                Element element1 = elements1.get(1);
                URI finalUrl = new URI("http://libaoke.com" + element1.attr("href"));
                get.setURI(finalUrl);
                CloseableHttpResponse responseFinal = httpClient.execute(get);
                if (responseFinal.getStatusLine().getStatusCode() != 200) {
                    System.out.println("访问网站出错！ code: " + responseFinal.getStatusLine().getStatusCode() + " url: " + finalUrl.toString());
                    System.exit(0);
                }
                HttpEntity reFinal = responseFinal.getEntity();
                String contentFinal = EntityUtils.toString(reFinal, "gbk");
                Document documentFinal = Jsoup.parse(contentFinal);
                if (documentFinal.select("div.libao>iframe").first() != null) {
                    String finalLiBao = documentFinal.select("div.libao>iframe").first().attr("src");
                    String code = finalLiBao.substring(finalLiBao.lastIndexOf("/") + 1, finalLiBao.length());
                    if (codeDao.countByCode(code) != 1) {
                        System.out.println(code);
                        System.out.println(saveLiBao(finalLiBao, code, codeDao));
                    } else {
                        System.out.println("网站已有： " + code);
                    }
                }
            }
        }
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
