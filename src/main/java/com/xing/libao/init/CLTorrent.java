package com.xing.libao.init;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/8/3
 * Time: 14:42
 */
public class CLTorrent {
    public static void main(String[] args) throws Exception {
        String url = "http://dz.cldz.biz/htm_data/15/1508/1582524.html";
        HttpGet get = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode() + " url: " + url);
        }
        HttpEntity re = response.getEntity();
        String content = EntityUtils.toString(re, "gbk");
        System.out.println(content);
        Pattern pattern = Pattern.compile("<b>本頁主題:</b> (.*?)</td>");
        Matcher matcher = pattern.matcher(content);
        String title = null;
        if (matcher.find()) {
            title = matcher.group(1);
            System.out.println(title);
        }
        Pattern pattern1 = Pattern.compile("http://www\\.rmdown\\.com/link\\.php\\?hash=(.*?)</a>");
        Matcher matcher1 = pattern1.matcher(content);
        String hash = null;
        if (matcher1.find()) {
            hash = matcher1.group(1);
            System.out.println(hash);
        }
        if (hash != null && title!=null) {
            HttpGet get1 = new HttpGet("http://www.rmdown.com/link.php?hash=" + hash);
            CloseableHttpClient httpClient1 = HttpClientBuilder.create().build();
            CloseableHttpResponse response1 = httpClient1.execute(get1);
            if (response1.getStatusLine().getStatusCode() != 200) {
                System.out.println("访问网站出错！ code: " + response1.getStatusLine().getStatusCode() + " url: " + url);
            }
            HttpEntity re1 = response1.getEntity();
            String content1 = EntityUtils.toString(re1, "gbk");
            System.out.println(content1);
            Pattern pattern2 = Pattern.compile("name=\"reff\" value=\"(.*?)\">",Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(content1);
            String reff = null;
            if(matcher2.find()){
                reff = matcher2.group(1);
                System.out.println(reff);
            }
            if(reff!=null) {
                HttpPost post = new HttpPost("http://www.rmdown.com/download.php");
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("ref", hash));
                list.add(new BasicNameValuePair("submit", "download"));
                list.add(new BasicNameValuePair("reff", reff));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
                post.setEntity(entity);
                response = httpClient.execute(post);
                title = title.replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "");
                File file = new File("D:\\" + title + ".torrent");
                file.createNewFile();
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(file));
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                bw.write(result);
                bw.close();
            }
        }
    }

    private static void tes() throws Exception {
        String uri = "http://dz.cldz.biz/thread0806.php?fid=15&page=2";
        HttpGet get = new HttpGet(uri);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode() + " url: " + uri);
        }
        HttpEntity re = response.getEntity();
        String content = EntityUtils.toString(re, "gbk");
        Document document = Jsoup.parse(content);
        Elements elements = document.select("h3>a");
        for (Element element : elements) {
            String href = element.attr("href");
            if (href.contains("htm_data")) {
                System.out.println(href);
            }
        }
    }

    private static void test() throws Exception {
        Document document = Jsoup.connect("http://dz.cldz.biz/thread0806.php?fid=15").get();
        Elements elements = document.select("h3>a");
        for (Element element : elements) {
            System.out.println(element.text());
        }
    }
}
