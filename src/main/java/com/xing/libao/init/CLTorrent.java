package com.xing.libao.init;

import com.xing.libao.util.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/8/3
 * Time: 14:42
 */
public class CLTorrent {
    public static void main(String[] args) throws Exception {
        RequestUtil requestUtil = RequestUtil.getInstance();
        for (int i = 25; i < 40; i++) {
            String url = "http://dz.cldz.biz/thread0806.php?fid=15&page=" + i;
            System.out.println("url: " + url);
            String content = requestUtil.getHtml(url);
            if (StringUtils.isEmpty(content))
                continue;
            Document document = Jsoup.parse(content);
            Elements elements = document.select("h3>a");
            for (Element element : elements) {
                String href = element.attr("href");
                if (href.contains("htm_data")) {
                    content = requestUtil.getHtml("http://dz.cldz.biz/" + href);
                    if (StringUtils.isEmpty(content))
                        continue;
                    System.out.println("detail: " + "http://dz.cldz.biz/" + href);
                    String title = getPattern("<b>本頁主題:</b> (.*?)</td>", content);
                    String hash = getPattern("http://www\\.rmdown\\.com/link\\.php\\?hash=(.*?)[<br>][</a>]", content);
                    if (hash != null && title != null) {
                        System.out.println("http://www.rmdown.com/link.php?hash=" + hash);
                        content = requestUtil.getHtml("http://www.rmdown.com/link.php?hash=" + hash);
                        if (StringUtils.isEmpty(content))
                            continue;
                        String ref = getPattern("name=\"reff\" value=\"(.*?)\">", content);
                        if (ref != null) {
                            HttpPost post = new HttpPost("http://www.rmdown.com/download.php");
                            List<NameValuePair> list = new ArrayList<NameValuePair>();
                            list.add(new BasicNameValuePair("ref", hash));
                            list.add(new BasicNameValuePair("submit", "download"));
                            list.add(new BasicNameValuePair("reff", ref));
                            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
                            post.setEntity(entity);
                            try {
                                title = title.replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "");
                                File file = new File("D:\\" + title + ".torrent");
                                if (!file.exists()) {
                                    if (file.createNewFile()) {
                                        CloseableHttpResponse response = requestUtil.execute(post);
                                        if (response != null) {
                                            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(file));
                                            byte[] result = EntityUtils.toByteArray(response.getEntity());
                                            bw.write(result);
                                            bw.close();
                                            response.close();
                                        }
                                    } else {
                                        System.out.println("file create fail!" + file.getName());
                                    }
                                } else {
                                    System.out.println("file already exist!" + file.getName());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            System.out.println("ref is null! " + title);
                        }
                    } else {
                        System.out.println("hash = null || title == null: " + content);
                    }
                } else {
                    System.out.println("content doesn't have href! " + href);
                }
                Thread.sleep(500);
            }
        }

    }

    private static String getPattern(String regex, String content) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        String title = null;
        if (matcher.find()) {
            title = matcher.group(1);
        } else {
            System.out.println("pattern can't find!" + regex);
        }
        return title;
    }

    private static void tes() throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setHost("dz.cldz.biz");
        uriBuilder.setPath("thread0806.php");
        uriBuilder.setParameter("fid", "15");
        uriBuilder.setParameter("page", "2");
        uriBuilder.setScheme("http");
        String uri = "http://dz.cldz.biz/thread0806.php?fid=15&page=2";
        HttpGet get = new HttpGet(uri);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode() + " url: " + uri);
        }
        HttpEntity re = response.getEntity();
        String content = EntityUtils.toString(re, "gbk");
        Document document = Jsoup.parse(content);
        Elements elements = document.select("h3>a");
        for (Element element : elements) {
            String href = element.attr("href");
            if (href.contains("htm_data")) {
                System.out.println(href);
            }
        }
    }

    private static void test() throws Exception {
        Document document = Jsoup.connect("http://dz.cldz.biz/thread0806.php?fid=15").get();
        Elements elements = document.select("h3>a");
        for (Element element : elements) {
            System.out.println(element.text());
        }
    }
}
