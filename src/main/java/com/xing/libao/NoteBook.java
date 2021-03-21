package com.xing.libao;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/7/1
 * Time: 14:44
 */
public class NoteBook {
    private static List<String> books = new ArrayList<String>();
    static {
        books.add("http://www.23wx.com/html/21/21779/20683049.html");
        books.add("http://www.23wx.com/html/21/21779/20695020.html");
        books.add("http://www.23wx.com/html/21/21779/20700844.html");
        books.add("http://www.23wx.com/html/21/21779/20704497.html");
        books.add("http://www.23wx.com/html/21/21779/20709040.html");
        books.add("http://www.23wx.com/html/21/21779/20712943.html");
        books.add("http://www.23wx.com/html/21/21779/20728926.html");
        books.add("http://www.23wx.com/html/21/21779/20735907.html");
        books.add("http://www.23wx.com/html/21/21779/20739767.html");
        books.add("http://www.23wx.com/html/21/21779/20741526.html");
        books.add("http://www.23wx.com/html/21/21779/20753433.html");
        books.add("http://www.23wx.com/html/21/21779/20760898.html");
        books.add("http://www.23wx.com/html/21/21779/20765370.html");
        books.add("http://www.23wx.com/html/21/21779/20768870.html");
        books.add("http://www.23wx.com/html/21/21779/20774527.html");
        books.add("http://www.23wx.com/html/21/21779/20776602.html");
        books.add("http://www.23wx.com/html/21/21779/20779380.html");
        books.add("http://www.23wx.com/html/21/21779/20787685.html");
        books.add("http://www.23wx.com/html/21/21779/20791192.html");
        books.add("http://www.23wx.com/html/21/21779/20793427.html");
        books.add("http://www.23wx.com/html/21/21779/20799320.html");
        books.add("http://www.23wx.com/html/21/21779/20818985.html");
        books.add("http://www.23wx.com/html/21/21779/20818986.html");
        books.add("http://www.23wx.com/html/21/21779/20818987.html");
        books.add("http://www.23wx.com/html/21/21779/20831546.html");
        books.add("http://www.23wx.com/html/21/21779/20849242.html");
        books.add("http://www.23wx.com/html/21/21779/20849254.html");
        books.add("http://www.23wx.com/html/21/21779/20854670.html");
        books.add("http://www.23wx.com/html/21/21779/20858915.html");
        books.add("http://www.23wx.com/html/21/21779/20872052.html");
        books.add("http://www.23wx.com/html/21/21779/20872142.html");
        books.add("http://www.23wx.com/html/21/21779/20874828.html");
        books.add("http://www.23wx.com/html/21/21779/20878138.html");
        books.add("http://www.23wx.com/html/21/21779/20879614.html");
        books.add("http://www.23wx.com/html/21/21779/20882685.html");
        books.add("http://www.23wx.com/html/21/21779/20887884.html");
        books.add("http://www.23wx.com/html/21/21779/20893799.html");
        books.add("http://www.23wx.com/html/21/21779/20902111.html");
        books.add("http://www.23wx.com/html/21/21779/20910969.html");
        books.add("http://www.23wx.com/html/21/21779/20918324.html");
        books.add("http://www.23wx.com/html/21/21779/20923357.html");
        books.add("http://www.23wx.com/html/21/21779/20927737.html");
        books.add("http://www.23wx.com/html/21/21779/20938197.html");
        books.add("http://www.23wx.com/html/21/21779/20946878.html");
        books.add("http://www.23wx.com/html/21/21779/20953475.html");
        books.add("http://www.23wx.com/html/21/21779/20956835.html");
        books.add("http://www.23wx.com/html/21/21779/20958347.html");
        books.add("http://www.23wx.com/html/21/21779/20961123.html");
        books.add("http://www.23wx.com/html/21/21779/20965779.html");
        books.add("http://www.23wx.com/html/21/21779/20967356.html");
        books.add("http://www.23wx.com/html/21/21779/20971801.html");
        books.add("http://www.23wx.com/html/21/21779/21008148.html");
        books.add("http://www.23wx.com/html/21/21779/21008149.html");
        books.add("http://www.23wx.com/html/21/21779/21008150.html");
        books.add("http://www.23wx.com/html/21/21779/21011557.html");
        books.add("http://www.23wx.com/html/21/21779/21017521.html");
        books.add("http://www.23wx.com/html/21/21779/21032136.html");
        books.add("http://www.23wx.com/html/21/21779/21044223.html");
        books.add("http://www.23wx.com/html/21/21779/21052242.html");
        books.add("http://www.23wx.com/html/21/21779/21059505.html");
        books.add("http://www.23wx.com/html/21/21779/21067108.html");
        books.add("http://www.23wx.com/html/21/21779/21071075.html");
        books.add("http://www.23wx.com/html/21/21779/21083765.html");
        books.add("http://www.23wx.com/html/21/21779/21088350.html");
        books.add("http://www.23wx.com/html/21/21779/21098561.html");
        books.add("http://www.23wx.com/html/21/21779/21102520.html");
        books.add("http://www.23wx.com/html/21/21779/21107882.html");
        books.add("http://www.23wx.com/html/21/21779/21110165.html");
        books.add("http://www.23wx.com/html/21/21779/21125584.html");
        books.add("http://www.23wx.com/html/21/21779/21127269.html");
        books.add("http://www.23wx.com/html/21/21779/21128803.html");
        books.add("http://www.23wx.com/html/21/21779/21163043.html");
        books.add("http://www.23wx.com/html/21/21779/21163044.html");
        books.add("http://www.23wx.com/html/21/21779/21170074.html");
        books.add("http://www.23wx.com/html/21/21779/21172365.html");
        books.add("http://www.23wx.com/html/21/21779/21179548.html");
        books.add("http://www.23wx.com/html/21/21779/21184973.html");
        books.add("http://www.23wx.com/html/21/21779/21204509.html");
        books.add("http://www.23wx.com/html/21/21779/21206660.html");
        books.add("http://www.23wx.com/html/21/21779/21235383.html");
        books.add("http://www.23wx.com/html/21/21779/21235384.html");
        books.add("http://www.23wx.com/html/21/21779/21294114.html");
        books.add("http://www.23wx.com/html/21/21779/21324802.html");
        books.add("http://www.23wx.com/html/21/21779/21324803.html");
        books.add("http://www.23wx.com/html/21/21779/21331774.html");
        books.add("http://www.23wx.com/html/21/21779/21336081.html");
        books.add("http://www.23wx.com/html/21/21779/21342708.html");
        books.add("http://www.23wx.com/html/21/21779/21349646.html");
        books.add("http://www.23wx.com/html/21/21779/21356040.html");
        books.add("http://www.23wx.com/html/21/21779/21373768.html");
        books.add("http://www.23wx.com/html/21/21779/21379276.html");
        books.add("http://www.23wx.com/html/21/21779/21393007.html");
        books.add("http://www.23wx.com/html/21/21779/21398987.html");
        books.add("http://www.23wx.com/html/21/21779/21408971.html");
        books.add("http://www.23wx.com/html/21/21779/21416886.html");
        books.add("http://www.23wx.com/html/21/21779/21425570.html");
        books.add("http://www.23wx.com/html/21/21779/21432164.html");
        books.add("http://www.23wx.com/html/21/21779/21435783.html");
        books.add("http://www.23wx.com/html/21/21779/21438873.html");
        books.add("http://www.23wx.com/html/21/21779/21441035.html");
        books.add("http://www.23wx.com/html/21/21779/21442930.html");
        books.add("http://www.23wx.com/html/21/21779/21446577.html");
        books.add("http://www.23wx.com/html/21/21779/21450176.html");
        books.add("http://www.23wx.com/html/21/21779/21450952.html");
        books.add("http://www.23wx.com/html/21/21779/21455938.html");
        books.add("http://www.23wx.com/html/21/21779/21456883.html");
        books.add("http://www.23wx.com/html/21/21779/21461928.html");
        books.add("http://www.23wx.com/html/21/21779/21464309.html");
        books.add("http://www.23wx.com/html/21/21779/21466881.html");
        books.add("http://www.23wx.com/html/21/21779/21468622.html");
        books.add("http://www.23wx.com/html/21/21779/21470883.html");
        books.add("http://www.23wx.com/html/21/21779/21476496.html");
        books.add("http://www.23wx.com/html/21/21779/21481569.html");
        books.add("http://www.23wx.com/html/21/21779/21485860.html");
        books.add("http://www.23wx.com/html/21/21779/21490370.html");
        books.add("http://www.23wx.com/html/21/21779/21495436.html");
        books.add("http://www.23wx.com/html/21/21779/21500204.html");
        books.add("http://www.23wx.com/html/21/21779/21505972.html");
        books.add("http://www.23wx.com/html/21/21779/21514088.html");
        books.add("http://www.23wx.com/html/21/21779/21522319.html");
        books.add("http://www.23wx.com/html/21/21779/21527487.html");
        books.add("http://www.23wx.com/html/21/21779/21531836.html");
        books.add("http://www.23wx.com/html/21/21779/21542462.html");
        books.add("http://www.23wx.com/html/21/21779/21547747.html");
        books.add("http://www.23wx.com/html/21/21779/21561448.html");
        books.add("http://www.23wx.com/html/21/21779/21565263.html");
        books.add("http://www.23wx.com/html/21/21779/21570244.html");
        books.add("http://www.23wx.com/html/21/21779/21570266.html");
        books.add("http://www.23wx.com/html/21/21779/21573498.html");
        books.add("http://www.23wx.com/html/21/21779/21577400.html");
        books.add("http://www.23wx.com/html/21/21779/21582218.html");
        books.add("http://www.23wx.com/html/21/21779/21582219.html");
    }
    public static void main(String[] args) throws Exception {
        for(String book:books){
            Document document = Jsoup.connect(book).get();
            System.out.println(book);
            Element element = document.select("dd#contents").first();
            String content = element.html().replaceAll("&nbsp;","");
            seeBook(content);
        }
    }

    private static void seeBook(String content){
        content = content.replaceAll("<br>\n|<br>","");
        String[] lines = content.split("\n");
        for(String line:lines){
            System.out.println(line);
        }
    }

    private static void test() throws Exception{
        Document document = Jsoup.connect("http://www.23wx.com/html/21/21779/").get();
        Elements elements = document.select("a[href]");
        for (Element element : elements) {
            String href = element.attr("abs:href");
            if(href.contains("/21779/")) {
                System.out.println(href);
            }
        }
    }
}
