package com.xing.libao.cron;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/3
 * Time: 17:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationTest.xml"})
public class Task {

    @Test
    public void href() {
        System.out.println("let's begin - " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Document document = null;
        try {
            document = Jsoup.connect("http://cl.bearhk.info/thread0806.php?fid=7").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36").header("Host", "cl.bearhk.info").get();
            Elements elements = document.select("tr.tr3.t_one");
            for (Element element : elements) {
                String title = element.child(1).child(0).child(0).text();
                System.out.println(element.child(1).child(0).child(0).attr("abs:href") + " --- " + title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
