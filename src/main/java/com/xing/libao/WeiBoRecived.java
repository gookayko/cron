package com.xing.libao;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/7/24
 * Time: 10:08
 */
public class WeiBoRecived {
    public static void main(String[] args) throws Exception{
test();
    }

    public static void test() throws Exception{
        HttpPost post = new HttpPost("https://api.weibo.com/2/comments/create.json");
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("access_token", "2.008FydZFHBkcwBd7c6f317752gR2TD"));
        list.add(new BasicNameValuePair("comment", "Hi，您可以使用@微招聘 发布职位，上亿微博用户都是潜在求职者〜通过微招聘可以很好的管理职位和简历信息，还可以很方便找到同事来分享招聘信息，刷微博的同时也能招人啦〜快来试试吧！相关介绍信息请看这里：http://zhaopin.weibo.com/intro?src_feed=search"));
        list.add(new BasicNameValuePair("id", "3865312045114841"));
        list.add(new BasicNameValuePair("pic_id", "49c0332fjw1eujikh2pl8j204f03o747"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        post.setEntity(entity);
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = closeableHttpClient.execute(post);
        String content = EntityUtils.toString(response.getEntity(), "gbk");
        System.out.println(content);
    }
}
