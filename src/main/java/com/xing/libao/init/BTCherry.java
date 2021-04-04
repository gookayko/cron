package com.xing.libao.init;

import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/7/21
 * Time: 11:27
 */
public class BTCherry {
    private static String key = "国产";

    public static void main(String[] args) throws Exception {
        URIBuilder builder = new URIBuilder("http://www.btcherry.net/search");
//        String sKey = URLEncoder.encode(key, "utf-8");
        builder.setParameter("keyword", key);
        for (int i = 1; i < 10; i++) {
            builder.setParameter("p", i + "");
            Document document = Jsoup.connect(builder.build().toString()).get();
            Elements elements = document.select("div.r");
            for (Element element : elements) {
                if (element.select("span.prop_val").size() > 0) {
                    String size = element.select("span.prop_val").get(1).text();
                    if (!size.contains("bytes") && !size.contains("KB")) {
                        System.out.println(element.select("h5.h").text() + " \t " + size + " \t " + element.getElementsByTag("a").get(1).attr("href"));
                    }
                }
            }
        }
    }
}
