package com.xing.libao;

import com.xing.libao.bean.WeiBo;
import com.xing.libao.dao.TitleDao;
import com.xing.libao.init.MidSet;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/7/3
 * Time: 14:24
 */
public class WeiBoCron {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        TitleDao titleDao = (TitleDao) context.getBean("titleDao");
        MidSet midSet = MidSet.getInstance();
        URIBuilder builder = new URIBuilder("https://api.weibo.com/2/statuses/public_timeline.json");
        builder.setParameter("access_token", "2.008FydZFHBkcwBd7c6f317752gR2TD");
        builder.setParameter("count", "50");
        builder.setParameter("feature", "1");
        URI uri = builder.build();
        while (true) {
            HttpGet get = new HttpGet();
            get.setURI(uri);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response;
            String result = "";
            response = httpClient.execute(get);
            HttpEntity re = response.getEntity();
            if (re != null) {
                result = EntityUtils.toString(re, "utf-8");
            }
//        System.out.println(result);
            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("statuses");
            for (int i = 0; i < array.length(); i++) {
                JSONObject objectTmp = array.getJSONObject(i);
                long mid = objectTmp.getLong("mid");
                String text = objectTmp.getString("text");
                JSONObject user = objectTmp.getJSONObject("user");
                long uid = user.getLong("id");
                String userName = user.getString("name");
                int verifiedType = user.getInt("verified_type");
                if (!midSet.hasIn(mid)) {
                    WeiBo weiBo = new WeiBo(uid, mid, filterOffUtf8Mb4(text), userName, verifiedType);
//                    System.out.println(weiBo);
                    titleDao.saveWeiBo(weiBo);
                    midSet.setSet(mid);
                }
            }
            Thread.sleep(5000);
        }
    }

    private static String returnChinese(String s) {
        return s.replaceAll("[^\u4E00-\u9FA5]", "");
    }

    private static String chinese(String str){
        try {
            System.out.println(filterOffUtf8Mb4(str));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("utf-8");
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        int i = 0;
        while (i < bytes.length) {
            short b = bytes[i];
            if (b > 0) {
                buffer.put(bytes[i++]);
                continue;
            }


            b += 256; // 去掉符号位


            if (((b >> 5) ^ 0x6) == 0) {
                buffer.put(bytes, i, 2);
                i += 2;
            } else if (((b >> 4) ^ 0xE) == 0) {
                buffer.put(bytes, i, 3);
                i += 3;
            } else if (((b >> 3) ^ 0x1E) == 0) {
                i += 4;
            } else if (((b >> 2) ^ 0x3E) == 0) {
                i += 5;
            } else if (((b >> 1) ^ 0x7E) == 0) {
                i += 6;
            } else {
                buffer.put(bytes[i++]);
            }
        }
        buffer.flip();
        return new String(buffer.array(), "utf-8");
    }
}
