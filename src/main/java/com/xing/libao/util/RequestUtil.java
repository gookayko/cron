package com.xing.libao.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/8/4
 * Time: 10:22
 */
public class RequestUtil {
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;

    private static class RequestUtilHolder {
        private static final RequestUtil instance = new RequestUtil();
    }

    private RequestUtil() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000)
                .setConnectTimeout(2000)
                .setConnectionRequestTimeout(2000)
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(40);
        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }

    public static RequestUtil getInstance() {
        return RequestUtilHolder.instance;
    }

    public String getHtml(String url) {
        return this.getHtml(url, "gbk");
    }

    public String getHtml(HttpGet get) {
        return this.html(get, "gbk");
    }

    public String getHtml(String url, String charset) {
        HttpGet get = new HttpGet(url);
        return html(get, charset);
    }

    public String postHtml(HttpPost post) {
        return html(post, "gbk");
    }

    public String postHtml(HttpPost post, String charset) {
        return html(post, charset);
    }

    private String html(HttpUriRequest request, String charset) {
        String content = null;
        try {
            this.setHeader(request);
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("访问网站出错！ code: " + response.getStatusLine().getStatusCode() + " url: " + request.getURI().toString());
                return null;
            } else {
                System.out.println("StatusLine: " + response.getStatusLine().getStatusCode());
            }
            HttpEntity re = response.getEntity();
            content = EntityUtils.toString(re, charset);
            if(!request.isAborted()){
                request.abort();
            }
            response.close();
        } catch (SocketTimeoutException e) {
            System.out.println("Read timed out!" + request.getURI().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private void setHeader(HttpUriRequest request) {
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        request.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36");
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public CloseableHttpResponse execute(HttpUriRequest request) {
        try {
            return httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
