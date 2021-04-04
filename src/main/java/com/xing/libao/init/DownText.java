package com.xing.libao.init;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/7/18 0018.
 */
public class DownText {
    private static Set<String> texts = new HashSet<String>();

    public static void main(String[] args) throws Exception {

//        Document document = null;
//        document = Jsoup.connect("http://174.127.195.202/bbs/thread-5509049-1-1.html").get();
//        Elements elements = document.getElementsByTag("a");
//        Set<String> urls = new HashSet<String>();
//        for(Element element:elements){
//            String  url = element.text();
//            if(url.contains("thread")){
//                urls.add(url);
//            }
//        }
//        System.out.println("size: " + urls.size());
//        for(String  s:urls){
//            down("http://174.127.195.202/bbs/"+s);
//        }
        int i=1;
        for(String s:texts) {
            CloseableHttpClient httpClient = HttpClients.custom().disableContentCompression().build();
            HttpGet get = new HttpGet(s);
            System.out.println(s);
            get.setHeader("Cookie", "__utmt=1; cdb3_oldtopics=D4247515D5509049D; cdb3_sid=QDTkP8; cdb3_cookietime=2592000; cdb3_auth=9Ewc0L8BnCFLHOFNwUuO2DVHaMzc5x672m6lBDZH6MrziOstG0fwkwCT55UK3lSpjOM; __utma=1.570637646.1437195915.1437195915.1437195915.1; __utmb=1.5.10.1437195915; __utmc=1; __utmz=1.1437195915.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); cdb3_fid402=1431499803; cdb3_smile=1D1");
            get.setHeader("Host", "174.127.195.202");
            get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            get.setHeader("Referer", "http://174.127.195.202/bbs/logging.php?action=login&");
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
            CloseableHttpResponse response = httpClient.execute(get);
            InputStream in = response.getEntity().getContent();
            FileOutputStream out = new FileOutputStream(new File("d:/"+i+".rar"));

            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            in.close();
            out.close();
            out.close();
            i++;
        }
//        Header contentHeader = response.getFirstHeader("Content-Disposition");
//        HeaderElement[] values = contentHeader.getElements();
//        NameValuePair param = values[0].getParameterByName("filename");
//        System.out.println(param.getValue());
//        System.out.println(URLDecoder.decode(param.getValue(), "utf-8"));
//        System.out.println(URLDecoder.decode(param.getValue(), "gbk"));
//        System.out.println(URLDecoder.decode(param.getValue(), "iso_8859_1"));
//        System.out.println(new String(param.getValue().getBytes(), "utf-8"));
//        System.out.println(new String(param.getValue().getBytes(), "gbk"));
//        System.out.println(new String(param.getValue().getBytes(), "iso_8859_1"));
//        if (response.getStatusLine().getStatusCode() != 200) {
//            System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode() + " url: ");
//            System.exit(0);
//        }
//        HttpEntity re = response.getEntity();
//        String content = EntityUtils.toString(re, "gbk");
//        System.out.println(content);
    }

    private static void down(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        get.setHeader("Cookie", "__utmt=1; cdb3_oldtopics=D4247515D5509049D; cdb3_sid=QDTkP8; cdb3_cookietime=2592000; cdb3_auth=9Ewc0L8BnCFLHOFNwUuO2DVHaMzc5x672m6lBDZH6MrziOstG0fwkwCT55UK3lSpjOM; __utma=1.570637646.1437195915.1437195915.1437195915.1; __utmb=1.5.10.1437195915; __utmc=1; __utmz=1.1437195915.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); cdb3_fid402=1431499803; cdb3_smile=1D1");
        get.setHeader("Host", "174.127.195.202");
        get.setHeader("Referer", "http://174.127.195.202/bbs/logging.php?action=login&");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode() + " url: " + url);
            System.exit(0);
        }
        HttpEntity re = response.getEntity();
        String content = EntityUtils.toString(re, "gbk");
        showLink(content);

    }

    private static void showLink(String con) {
        Pattern pattern = Pattern.compile("(attachment.php.*?)\"");
        Matcher matcher = pattern.matcher(con);
        if (matcher.find()) {
            System.out.println("http://174.127.195.202/bbs/" + matcher.group(1));
        }
    }

    static {
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1984453");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1842570");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2067548");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2139244");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2139031");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1857620");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1879855");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1857620");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1859997");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1960242");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2383341");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2049602");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1869550");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1845809");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1886477");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2112641");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1968993");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2004590");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2120768");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1987394");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1984453");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1964465");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1844398");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1968993");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2050537");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2007759");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2057256");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1885579");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2378537");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2060970");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1965385");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1969500");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1969054");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1973986");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1973421");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2048991");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2028617");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2179674");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2137724");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1844399");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2062936");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2062936");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1954553");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2139237");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1994217");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1842075");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1866482");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1961809");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1866482");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1986110");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1886537");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1844399");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2075204");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1857763");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1886537");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1990035");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2378457");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1973986");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2306346");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1845717");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1990047");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1845581");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1961809");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1859997");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2004590");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1965385");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2139641");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1869550");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1990756");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1954553");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1988418");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2179665");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2204842");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2398591");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1844398");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1886477");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1987793");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2076440");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2160763");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2120129");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1964465");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2442802");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1973421");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1963560");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1844378");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2383340");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1969500");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1969054");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2028617");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1899331");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1952458");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1845581");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2204782");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1879855");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1963560");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2022129");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1960829");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1837224");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1960829");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2120129");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1844378");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1968982");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2359457");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1841689");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1885579");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=1987793");
        texts.add("http://174.127.195.202/bbs/attachment.php?aid=2098362");
    }
}
