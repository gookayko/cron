package com.xing.libao.cron;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
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

    @Test
    public void image(){
        for(int i=1;i<100;i++){
            if(i<10) {
                System.out.println("[img]http://img.aitaotu.com/Pics/2015/0609/20/0" + i + ".jpg[/img]\n");
            }else{
                System.out.println("[img]http://img.aitaotu.com/Pics/2015/0609/20/" + i + ".jpg[/img]\n");
            }
        }
    }

    @Test
    public void downImage(){
        try {
            Document document = Jsoup.connect("http://www.douban.com/group/topic/76282076/").header("Cookie","bid=\"xXA9pRNROLY\"; ll=\"108288\"; _ga=GA1.2.24880976.1433149576; viewed=\"6082808_25920202_25858095_25871778_25918524_25829693\"; ps=y; ue=\"t2xingzhe@sina.com\"; dbcl2=\"62136338:t1NIWWC+5qY\"; ck=\"aCBL\"; ct=y; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1434017759%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DCm_2iLHVuEkSQj8TwDHz5HPxTju2WnpdeWQ50BIJ3Qe%26wd%3D%25E8%25B1%2586%25E7%2593%25A3%26issp%3D1%26f%3D3%26ie%3Dutf-8%26tn%3Dbaiduhome_pg%26oq%3Ddouban%26inputT%3D1774102%22%5D; ap=1; push_noty_num=0; push_doumail_num=6; _pk_id.100001.8cb4=b5dd054eb36b384d.1417500029.8.1434021468.1433931869.; _pk_ses.100001.8cb4=*; __utmt=1; __utma=30149280.1801161871.1417500032.1433931875.1434017759.12; __utmb=30149280.34.10.1434017759; __utmc=30149280; __utmz=30149280.1433931875.11.8.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmv=30149280.6213").get();
            System.out.println(document.toString());
            Elements elements = document.select("img");
            for(Element element:elements){
                String href = element.attr("src");
                    System.out.println(href);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void replace(){
        String s = "a|\\/:*?\"<>|";
        System.out.println(s);
        System.out.println(s.replaceAll("\\||\\\\|/|:|\\*|\\?|\"|<|>",""));
    }
}
