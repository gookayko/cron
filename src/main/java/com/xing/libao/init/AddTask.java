package com.xing.libao.init;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Administrator on 2015/7/18 0018.
 */
public class AddTask {
    private static String cookie = "115_lang=zh; payment=alipay; ssov_2955186=1_2955186_181c9134447ce8222432f8f4e9bc0352; loginType=1; UID=2955186_A1_1437188682; CID=3971aaaa8fc36e43f3fb85bc0411e231; SEID=4d786f41726f148db468f78f634ca7fc5cd5498fd0dea1d087dc3e5b6949aa760efb4dabb7a4db186c928cf4eef0f29976a183483dd6616eebe271da; OOFA=%2507%250ETSWZ%2507%250CC%2505%2540%250BX%2505LZW%2502%255CU%250D%2506%2504T%250A%255DT%255DQ%255B%2502%2502%250FV%2505%2503SZWRQ%2500YQ%250F%255B%2500S%2500%2508QV%255DV%2503%2500%2506RT%255C%2506%2503%2501V%2507; OOFL=2955186; IM_ALERT_IND=1; tjj_u=1; tjj_id=143718862198161172923; tjj_repeat=1; __utmt=1; __utma=48116967.209781880.1420205127.1437188622.1437205064.64; __utmb=48116967.1.10.1437205064; __utmc=48116967; __utmz=48116967.1420205127.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)";
    private static Map<String, String> tasks = new HashMap<String, String>();

    static {
        tasks.put("AVOP-059", "magnet:?xt=urn:btih:573992693c24a887326e989d22466bcae3730dcf&dn=AVOP-059");
        tasks.put("AVOP-058", "magnet:?xt=urn:btih:bd767a73b55218bbfacae6cc691cf156db9732c3&dn=AVOP-058");
        tasks.put("AVOP-060", "magnet:?xt=urn:btih:be545f8c44ab509e5b0ca3f970189234786e675f&dn=%5BAVOP-060%5D%20SILK%20LABO%20x%20SOD%20-%20Iori%20Kogawa%20-%20Girl%27s%20Pleasure");
        tasks.put("AVOP-051", "magnet:?xt=urn:btih:b95234a9e6d03c1c79895ac9a968dc80484a1145&dn=AVOP-051");
        tasks.put("AVOP-008", "magnet:?xt=urn:btih:5a85acaa400e31bee62385b458f2fcaac8341f02&dn=%5BAVOP-008%5D%20Derived%20From%20Natural%20Ingredients%2C%20Prestige%20Juice%2010%2C000%25");
        tasks.put("AVOP-002", "magnet:?xt=urn:btih:e0b0744680a1b3741542da82a84bf69376c853ad&dn=AVOP-002%20%E3%81%82%E3%81%AA%E3%81%9F%E3%80%81%E8%A8%B1%E3%81%97%E3%81%A6%E2%80%A6%E3%80%82-%E7%97%B4%E5%A7%A6%E3%81%AB%E6%95%A3%E3%82%89%E3%81%95%E3%82%8C%E3%81%9F%E7%B4%94%E6%BD%94-%20%E7%A5%9E%E7%94%B0%E5%85%89%20%E4%B8%AD%E6%96%87%E5%AD%97%E5%B9%95");
        tasks.put("AVOP-006", "magnet:?xt=urn:btih:b0fe934db9fb803817a517a1bd333f73f8cba22d&dn=AVOP-006");
        tasks.put("AVOP-069", "magnet:?xt=urn:btih:ffa24c1b0951425fded4bf5a0934691308f9cfec&dn=AVOP-069");
        tasks.put("AVOP-056", "magnet:?xt=urn:btih:c7cd055628572723b49d233a86e66b25f6872bd1&dn=AVOP-056");
        tasks.put("AVOP-41", "magnet:?xt=urn:btih:51248fff78d515cb82f6b5b5ce62fab9966ff48c&dn=AVOP-41");
        tasks.put("AVOP-062", "magnet:?xt=urn:btih:fb6e1840033cc2fd1ca9604020e3f195f051fd76&dn=AVOP-062");
        tasks.put("AVOP-080", "magnet:?xt=urn:btih:175dc247396fb6da8116256907334a6dc33dc4eb&dn=AVOP-080");
        tasks.put("AVOP-079", "magnet:?xt=urn:btih:3743621e188240bf560898eec0e6b8871bf9a97e&dn=%5BAVOP-079%5D%20Dream%20Shower%20AV%20Debut%2029%20Aya%20Mikami%20%28Censored%29%20%5BHD%20720p%5D");
        tasks.put("", "");
        tasks.put("", "");
        tasks.put("", "");
    }

    public static void main(String[] args) throws Exception {
        Set keys = tasks.entrySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry1 = (Map.Entry<String, String>) iterator.next();
            String key = entry1.getKey();
            if (StringUtils.isNotEmpty(key) && searchDidNotHave(key)) {
                addTask(entry1.getValue());
            }
            Thread.sleep(1000);
        }

        showName();
    }

    private static boolean searchDidNotHave(String searchWord) throws Exception {
        String url = "http://web.api.115.com/files/search?offset=0&limit=30&search_value=" + searchWord + "&date=&aid=-1&pick_code=&type=&source=&format=json";
        HttpGet get = new HttpGet(url);
        get.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        get.setHeader("Cookie", cookie);
        get.setHeader("Host", "web.api.115.com");
        get.setHeader("Referer", "http://web.api.115.com/bridge_2.0.html?namespace=Core.DataAccess&api=UDataAPI&_t=v5");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
        get.setHeader("X-Requested-With", "XMLHttpRequest");
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = closeableHttpClient.execute(get);
        String content = EntityUtils.toString(response.getEntity(), "gbk");
        JSONObject object = new JSONObject(content);
        int count = object.getInt("count");
        if (count != 0) {
            System.out.println(content);
        }
        return count == 0;
    }

    private static void showName() throws Exception {
        String url = "http://115.com/lixian/?ct=lixian&ac=task_lists";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("page", "1"));
        list.add(new BasicNameValuePair("uid", "2955186"));
        list.add(new BasicNameValuePair("sign", "5dbcc4c563a3b3f99194e0ee0978774a"));
        list.add(new BasicNameValuePair("time", "1437205422"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httppost.setHeader("Cookie", cookie);
        httppost.setHeader("Host", "115.com");
        httppost.setHeader("Origin", "http://115.com");
        httppost.setHeader("Referer", "http://115.com/?tab=offline&mode=wangpan");
        httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
        httppost.setHeader("X-Requested-With", "XMLHttpRequest");
        httppost.setEntity(entity);
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = closeableHttpClient.execute(httppost);
        String content = EntityUtils.toString(response.getEntity(), "gbk");
        JSONObject object = new JSONObject(content);
        JSONArray array = object.getJSONArray("tasks");
        for (int i = 0; i < array.length(); i++) {
            JSONObject objectTmp = array.getJSONObject(i);
            System.out.println(objectTmp.get("name"));
        }
    }

    private static void addTask(String task) throws Exception {
        if (StringUtils.isEmpty(task))
            return;
        String url = "http://115.com/lixian/?ct=lixian&ac=add_task_url";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("url", task));
        list.add(new BasicNameValuePair("uid", "2955186"));
        list.add(new BasicNameValuePair("sign", "5dbcc4c563a3b3f99194e0ee0978774a"));
        list.add(new BasicNameValuePair("time", "1437205422"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httppost.setHeader("Cookie", cookie);
        httppost.setHeader("Host", "115.com");
        httppost.setHeader("Origin", "http://115.com");
        httppost.setHeader("Referer", "http://115.com/?tab=offline&mode=wangpan");
        httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
        httppost.setHeader("X-Requested-With", "XMLHttpRequest");
        httppost.setEntity(entity);
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = closeableHttpClient.execute(httppost);
        String content = EntityUtils.toString(response.getEntity(), "gbk");
        int errNo = 0;
        JSONObject object = new JSONObject(content);
        int reNo = object.getInt("errno");
        if (errNo == reNo) {
            System.out.println("Success");
        } else {
            System.out.println("Error! " + " " + content);
        }
        Thread.sleep(1000);

    }
}
