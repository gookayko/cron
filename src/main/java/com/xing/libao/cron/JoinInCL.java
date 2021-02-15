package com.xing.libao.cron;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/3
 * Time: 10:53
 */
public class JoinInCL {
    private String code = "a695eX60*d56*e33b";
    private static String postUrl = "http://cl.bearhk.info/register.php";

    public static void main(String[] args) {
        JoinInCL joinInCL = new JoinInCL();
        joinInCL.task();
    }

    private void task() {
        String[] codes = code.split("\\*");
        if (codes.length == 2) {
            for (int i = 0; i <= 9; i++) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("action", "reginvcodeck"));
                list.add(new BasicNameValuePair("reginvcode", codes[0] + i + codes[1]));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
                HttpPost httppost = new HttpPost(postUrl);
                httppost.setEntity(entity);
                CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
                try {
                    CloseableHttpResponse response = closeableHttpClient.execute(httppost);
                    String result = EntityUtils.toString(response.getEntity(), "gb2312");
                    System.out.println(result + " - code: " + codes[0] + i + codes[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (codes.length == 3) {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    List<NameValuePair> list = new ArrayList<NameValuePair>();
                    list.add(new BasicNameValuePair("action", "reginvcodeck"));
                    list.add(new BasicNameValuePair("reginvcode", codes[0] + i + codes[1] + j + codes[2]));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
                    HttpPost httppost = new HttpPost(postUrl);
                    httppost.setEntity(entity);
                    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
                    try {
                        CloseableHttpResponse response = closeableHttpClient.execute(httppost);
                        String result = EntityUtils.toString(response.getEntity(), "gb2312");
                        System.out.println(result + " - code: " + codes[0] + i + codes[1] + j + codes[2]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
