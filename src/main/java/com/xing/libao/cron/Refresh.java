package com.xing.libao.cron;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/3
 * Time: 14:32
 */
public class Refresh {
    private int initPate = 27;
    private static String url = "http://cl.bearhk.info/read.php?tid=1505392&fpage=0&toread=&page=";
    private int count = 0;
    public static void main(String[] args) {
        Refresh refresh = new Refresh();
        refresh.task();
    }

    private void task(){
        while(true){
            System.out.println("let's begin - Refresh" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " count: " + count);
            try {
                if(count == 25){
                    initPate++;
                }
                Document document = Jsoup.connect(url + initPate).header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36").header("Host", "cl.bearhk.info").get();
                Elements elements = document.select("tr.tr1 > th.r_one > div.tpc_content");
                if(count!=elements.size()){
                    for(Element element:elements){
                        System.out.println(element.text());
                    }
                    count = elements.size();
                }
                Thread.sleep(100000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
