package com.xing.libao.cron;

import com.xing.libao.bean.Title;
import com.xing.libao.dao.CodeDao;
import com.xing.libao.dao.TitleDao;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/8
 * Time: 15:39
 */
@Component
public class RecordTitle {
    private static Map<Integer, String> map = new HashMap<Integer, String>();
    private Set<String> md5Set = new HashSet<String>();
    @Autowired
    TitleDao titleDao;

    public static void main(String[] args) {
        RecordTitle recordTitle = new RecordTitle();
        recordTitle.task();
    }

    @Scheduled(fixedRate = 120000)
    public void task() {
        String base = "http://cl.bearhk.info/thread0806.php?fid=";
        Set<Integer> fidSet = map.keySet();
        System.out.println("let's begin - RecordTitle" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        for (Integer fid : fidSet) {
            HttpGet get = new HttpGet(base + fid);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try {
                CloseableHttpResponse response = httpClient.execute(get);
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "gbk");
                Document document = Jsoup.parse(content);
                Elements elements = document.select("tr.tr3.t_one");
                for (Element element : elements) {
                    String title = element.child(1).child(0).child(0).text();
                    String url = element.child(1).child(0).child(0).attr("href");
                    if (url.contains("htm_data")) {
                        String md5 = MD5(title);
                        if (!md5Set.contains(md5)) {
                            int count = titleDao.getBtMd5(md5);
                            if (count < 1) {
                                Title title1 = new Title();
                                title1.setFid(fid);
                                title1.setTitle(title);
                                title1.setMd5(md5);
                                title1.setUrl(url);
                                title1.setTime(getSecond());
                                titleDao.saveTitle(title1);
                                if (md5Set.size() > 10000) {
                                    md5Set.clear();
                                }
                                md5Set.add(md5);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getSecond() {
        return (int) (new Date().getTime() / 1000);
    }

    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        map.put(2, "亚洲无码");
        map.put(15, "亚洲有码");
        map.put(4, "欧美原创");
        map.put(5, "动漫原创");
        map.put(7, "技术讨论");
        map.put(16, "自拍分享");
        map.put(20, "成人文学");
    }
}
