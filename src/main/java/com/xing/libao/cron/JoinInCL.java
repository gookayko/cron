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
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/3
 * Time: 10:53
 */
public class JoinInCL {
    private static String code = "a695eX609d56e33b";
    private static String postUrl = "http://cl.bearhk.info/register.php";

    public static void main(String[] args) {
        JoinInCL joinInCL = new JoinInCL();
        joinInCL.task();
    }

    private void task(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("action","reginvcodeck"));
        list.add(new BasicNameValuePair("reginvcode",code));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        HttpPost httppost = new HttpPost(postUrl);
        httppost.setEntity(entity);
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httppost);
            String result = EntityUtils.toString(response.getEntity(),"gb2312");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
