package com.xing.libao.image;

import com.xing.libao.BerkeleyDBUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/11
 * Time: 18:10
 */
public class AllDown {
    String path = "d:/douban/images/";

    public static void main(String[] args) {
        AllDown allDown = new AllDown();
        allDown.task();
    }

    public void task() {
        String url = "http://www.douban.com/group/haixiuzu/discussion?start=";
        String containsUrl = "/topic/";
        if (createSavePath(path)) {
            for (int i = 300; i < 3000; i++) {
                try {
                    System.out.println(i);
                    Document document = Jsoup.connect(url + (25 * i)).get();
                    Elements elements = document.select("a[href]");
                    for (Element element : elements) {
                        String href = element.attr("abs:href");
                        String title = element.text();
                        if (href.contains(containsUrl)) {
                            System.out.println(href + " --- " + title);
                            detail(href, "group_topic",title);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean detail(String url, String contains,String title) {
        try {
            Document document = Jsoup.connect(url).header("Cookie", "bid=\"xXA9pRNROLY\"; ll=\"108288\"; _ga=GA1.2.24880976.1433149576; viewed=\"6082808_25920202_25858095_25871778_25918524_25829693\"; ps=y; ue=\"t2xingzhe@sina.com\"; dbcl2=\"62136338:t1NIWWC+5qY\"; ck=\"aCBL\"; ct=y; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1434017759%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DCm_2iLHVuEkSQj8TwDHz5HPxTju2WnpdeWQ50BIJ3Qe%26wd%3D%25E8%25B1%2586%25E7%2593%25A3%26issp%3D1%26f%3D3%26ie%3Dutf-8%26tn%3Dbaiduhome_pg%26oq%3Ddouban%26inputT%3D1774102%22%5D; ap=1; push_noty_num=0; push_doumail_num=6; _pk_id.100001.8cb4=b5dd054eb36b384d.1417500029.8.1434021468.1433931869.; _pk_ses.100001.8cb4=*; __utmt=1; __utma=30149280.1801161871.1417500032.1433931875.1434017759.12; __utmb=30149280.34.10.1434017759; __utmc=30149280; __utmz=30149280.1433931875.11.8.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmv=30149280.6213").get();
            Elements elements = document.select("img[src]");
            int i=0;
            for (Element element : elements) {
                String href = element.attr("src");
                if (href.contains(contains)) {
//                    System.out.println(href);
                    if (BerkeleyDBUtil.getInstance().add(href)) {
                        downImage(href,title+i);
                        i++;
                    }
                }
            }
            Thread.sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void downImage(String img,String title) {
        try {

            // 创建流
            BufferedInputStream in = new BufferedInputStream(new URL(img)
                    .openStream());

            // 生成图片名
//            int index = img.lastIndexOf("/");
//            String sName = img.substring(index + 1, img.length());
            title = title.replaceAll("\\||\\\\|/|:|\\*|\\?|\"|<|>","");
            System.out.println(title);
            // 存放地址
            File imgF = new File(path + title + ".jpg");
            // 生成图片
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(imgF));
            byte[] buf = new byte[2048];
            int length = in.read(buf);
            while (length != -1) {
                out.write(buf, 0, length);
                length = in.read(buf);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean createSavePath(String path) {
        File file = new File(path);
        return file.exists() || file.mkdirs();
    }

}
