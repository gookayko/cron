package com.xing.libao.cron;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Administrator on 2015/6/3 0003.
 */
public class PostData {
    private Set<String> strings = new HashSet<String>();

    public static void main(String[] args) {
        PostData postData = new PostData();
        postData.task();
    }

    private void task() {
        System.out.println("let's begin - " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Document document = null;
        try {
            document = Jsoup.connect("http://cl.bearhk.info/thread0806.php?fid=7&page=2").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36").header("Host", "cl.bearhk.info").get();
            Elements elements = document.select("tr.tr3.t_one");
            for (Element element : elements) {
                String title = element.child(1).child(0).child(0).text();
                String url = element.child(1).child(0).child(0).attr("abs:href");
                String sTid = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
                int tid = 0;
                if (StringUtils.isNotEmpty(sTid) && StringUtils.isNumeric(sTid)) {
                    tid = Integer.parseInt(sTid);
                }
                System.out.println(url + " --- " + title);
                if (!strings.contains(url) && tid > 0) {
                    List<NameValuePair> list = new ArrayList<NameValuePair>();
                    list.add(new BasicNameValuePair("atc_usesign", "1"));
                    list.add(new BasicNameValuePair("atc_convert", "1"));
                    list.add(new BasicNameValuePair("atc_autourl", "1"));
                    list.add(new BasicNameValuePair("atc_title", "感谢分享"));
                    list.add(new BasicNameValuePair("atc_content", "1024"));
                    list.add(new BasicNameValuePair("step", "2"));
                    list.add(new BasicNameValuePair("action", "reply"));
                    list.add(new BasicNameValuePair("fid", "7"));
                    list.add(new BasicNameValuePair("atc_attachment", "none"));
                    list.add(new BasicNameValuePair("tid", tid + ""));
                    list.add(new BasicNameValuePair("pid", ""));
                    list.add(new BasicNameValuePair("article", ""));
                    list.add(new BasicNameValuePair("verify", "verify"));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
                    HttpPost httppost = new HttpPost("http://cl.bearhk.info/post.php");
                    httppost.setHeader("Cookie","__cfduid=d4586a7ca299796975eaa54ae57e4ac0c1432880887;227c9_winduser=BQEOU1dcP1YBUg1SDVUDVgoCBwQDAQMABABRCA1RBVBTAwEADlcB;");
                    httppost.setHeader("Host","cl.bearhk.info");
                    httppost.setHeader("Accept-Language","zh-CN,zh;q=0.8");
                    httppost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                    httppost.setHeader("Content-Type","application/x-www-form-urlencoded");
                    httppost.setHeader("Proxy-Authorization","Basic eW9sdS12cG46YmQ2NTdkODRkc3A=");
                    httppost.setHeader("Origin","http://cl.bearhk.info");
                    httppost.setHeader("Referer",url);
                    httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
                    httppost.setEntity(entity);
                    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
                    CloseableHttpResponse response = closeableHttpClient.execute(httppost);
                    String content = EntityUtils.toString(response.getEntity(),"gbk");
                    if(content.contains("發貼完畢點擊進入主題列表")) {
                        System.out.println("回帖完毕，积分到手！");
                    }
                    strings.add(url);
                }
                Thread.sleep(1025000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
