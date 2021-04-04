package com.xing.libao.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/7/20
 * Time: 17:58
 */
public class BtDig {
    private static String base = "https://btdigg.org/search?q=avop";
    private static String key = "avop";

    public static void main(String[] args) throws Exception {
//        String sKey = URLEncoder.encode(key, "utf-8");
//        HttpGet get = new HttpGet(base + sKey);
//        get.setHeader("cookie", "__cfduid=d3945b4e907d68662d823a6251a6e43c31427970290; lang=cn; flush_ts=1437035975.68; __utmt=1; __utma=200931581.1852259498.1427970290.1437035975.1437386072.14; __utmb=200931581.2.10.1437386072; __utmc=200931581; __utmz=200931581.1427970290.1.1.utmcsr=yuese.info|utmccn=(referral)|utmcmd=referral|utmcct=/");
//        get.setHeader("referer", "https://btdigg.org/");
//        get.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36");
//
//        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
//        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
//        registryBuilder.register("http", plainSF);
////指定信任密钥存储对象和连接套接字工厂
//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            //信任任何链接
//            TrustStrategy anyTrustStrategy = new TrustStrategy() {
//                @Override
//                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                    return true;
//                }
//            };
//            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
//            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            registryBuilder.register("https", sslSF);
//        } catch (KeyStoreException e) {
//            throw new RuntimeException(e);
//        } catch (KeyManagementException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
//        //设置连接管理器
//        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
////      connManager.setDefaultConnectionConfig(connConfig);
////      connManager.setDefaultSocketConfig(socketConfig);
//        //构建客户端
//        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
//        System.out.println("Executing request " + get.getRequestLine());
//        CloseableHttpResponse response = closeableHttpClient.execute(get);
//        String result = EntityUtils.toString(response.getEntity(), "utf-8");
//        Document document = Jsoup.parse(result);
//        Elements elements = document.select("td.torrent_name");
//        for (Element element : elements) {
//            System.out.println(element.text());
//        }
        testSSL2();
    }

    private static void testSSL2() throws Exception{
        KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream instream = new FileInputStream(new File("d:/key.keystore"));
        try {
            trustStore.load(instream, "xingzhe4245".toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            HttpGet httpget = new HttpGet(base);
            httpget.setHeader("cookie", "__cfduid=d3945b4e907d68662d823a6251a6e43c31427970290; lang=cn; flush_ts=1437035975.68; __utmt=1; __utma=200931581.1852259498.1427970290.1437035975.1437386072.14; __utmb=200931581.2.10.1437386072; __utmc=200931581; __utmz=200931581.1427970290.1.1.utmcsr=yuese.info|utmccn=(referral)|utmcmd=referral|utmcct=/");
            httpget.setHeader("referer", "https://btdigg.org/");
            httpget.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36");

            System.out.println("executing request" + httpget.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    private static void testSSL() throws Exception{
        String sKey = URLEncoder.encode(key, "utf-8");
        HttpClient httpClient = new DefaultHttpClient();
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = new FileInputStream(new File("d:/key.keystore"));
            trustStore.load(in, "xingzhe4245".toCharArray());
            in.close();
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
            Scheme sch = new Scheme("https", 443, socketFactory);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);

            HttpGet get = new HttpGet(base + sKey);
            System.out.println("executing request" + get.getRequestLine());
            // 执行请求
            HttpResponse response = httpClient.execute(get);// 获得响应实体
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(entity);
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
            }
            // 销毁实体
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
            httpClient.getConnectionManager().shutdown();
        }
    }

}
