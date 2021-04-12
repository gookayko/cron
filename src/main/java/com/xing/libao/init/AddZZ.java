package com.xing.libao.init;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/7/29
 * Time: 10:55
 */
public class AddZZ {
    public static void main(String[] args) throws Exception{
AddZZ addZZ = new AddZZ();
//        System.out.println(new String("\u53c2\u6570\u9519\u8bef\u3002"));
        addZZ.testUploadResume();
    }

    public void testUploadResume() throws Exception {
//        HttpPost httpPost = new HttpPost("http://59.151.73.6:8080/platform-c-2.1.2/resume/upload/save");
        HttpPost httpPost = new HttpPost("http://upload.115.com/upload?debugs=0&userid=2955186&ets=1438314049&appid=n&sig=D2D299AB19AE38D873B10F0B4BB5B703799A7E03");
        httpPost.setHeader("Cookie","115_lang=zh; payment=alipay; ssov_2955186=1_2955186_181c9134447ce8222432f8f4e9bc0352; UUID=11555B84B373A6D0; UUTK=fb8e44GeIl56c5%2Fnq5NNlwKGuxbbxom%2F2ZG9ZN%2FFivo126jfWZbtsmsMinnOa9vLKtjN2Vm%2FL8MR8%2FsQTZPnepSZVZ%2F2XMXfk7%2Fak; loginType=1; UID=2955186_A1_1438141248; CID=275d5e5c4d7672839faef61ff63db327; SEID=c6675c08aea0993469e65e7d7ee7c2d6f7cfd9609db2a18fa6aa9adb28e4c7bcaa361c5f7521b5946bce679071729770e13306e076857093a71770b2; OOFA=%2507%250ETSWZ%2507%250CC%2505%2540%250BX%2505LZW%2502%255CU%250D%2506%250BT%2506TPQ%255B%255B%2502%2502%250F%2507%2502U%2502%2504S%2507%2507%2502%255C%2500%2500V%2507Q%2506YP%2506Z%250C%2500U%2501%2507%2507%2507ZS%255EQ%250C; OOFL=2955186; __utmt=1; __utma=48116967.2024335568.1424070885.1438138556.1438141246.36; __utmb=48116967.1.10.1438141246; __utmc=48116967; __utmz=48116967.1424070885.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
        httpPost.setHeader("Referer","http://115.com/?ac=offline_tpl&is_wl_tpl=1");
        httpPost.setHeader("Host", "115.com");
        httpPost.setHeader("Origin", "http://115.com");
//        HttpPost httpPost = new HttpPost("http://172.10.10.110:8080/platform-c/resume/upload/dev/save");
        HttpEntity req = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addPart("Filedata", new FileBody(new File("C:\\Users\\YLZX-Z0107\\Downloads\\8c35db915496f57812f2d9d655d5edde1945134b.torrent")))
                .addTextBody("Filename", "8c35db915496f57812f2d9d655d5edde1945134b.torrent")
                .addTextBody("target", "U_1_458348252144818522")
                .addTextBody("Upload", "Submit Query")
                .build();
        httpPost.setEntity(req);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity, "utf-8");
                JSONObject object = new JSONObject(content);
                System.out.println(content);
                if(object.getJSONObject("data")!=null){
                    String pickCode = object.getJSONObject("data").get("pick_code").toString();
                    String sha1 = object.getJSONObject("data").get("sha1").toString();
                    String fileId = object.getJSONObject("data").get("file_id").toString();
                    System.out.println("fileId: " + fileId + " sha1: " + sha1 + " pickCode: " + pickCode);
                    HttpPost httpPost2 = new HttpPost("http://115.com/lixian/?ct=lixian&ac=torrent");
                    httpPost2.setHeader("Cookie","115_lang=zh; payment=alipay; ssov_2955186=1_2955186_181c9134447ce8222432f8f4e9bc0352; UUID=11555B84B373A6D0; UUTK=fb8e44GeIl56c5%2Fnq5NNlwKGuxbbxom%2F2ZG9ZN%2FFivo126jfWZbtsmsMinnOa9vLKtjN2Vm%2FL8MR8%2FsQTZPnepSZVZ%2F2XMXfk7%2Fak; loginType=1; UID=2955186_A1_1438141248; CID=275d5e5c4d7672839faef61ff63db327; SEID=c6675c08aea0993469e65e7d7ee7c2d6f7cfd9609db2a18fa6aa9adb28e4c7bcaa361c5f7521b5946bce679071729770e13306e076857093a71770b2; OOFA=%2507%250ETSWZ%2507%250CC%2505%2540%250BX%2505LZW%2502%255CU%250D%2506%250BT%2506TPQ%255B%255B%2502%2502%250F%2507%2502U%2502%2504S%2507%2507%2502%255C%2500%2500V%2507Q%2506YP%2506Z%250C%2500U%2501%2507%2507%2507ZS%255EQ%250C; OOFL=2955186; __utmt=1; __utma=48116967.2024335568.1424070885.1438138556.1438141246.36; __utmb=48116967.1.10.1438141246; __utmc=48116967; __utmz=48116967.1424070885.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
                    httpPost2.setHeader("Referer","http://115.com/?ac=offline_tpl&is_wl_tpl=1");
                    httpPost2.setHeader("Host", "115.com");
                    httpPost2.setHeader("Origin", "http://115.com");
                    List<NameValuePair> list = new ArrayList<NameValuePair>();
                    list.add(new BasicNameValuePair("pickcode", pickCode));
                    list.add(new BasicNameValuePair("sha1", sha1));
                    list.add(new BasicNameValuePair("uid", "2955186"));
                    list.add(new BasicNameValuePair("sign", "2df57e7e111be9d368a2877272494b9d"));
                    list.add(new BasicNameValuePair("time", "1438141257"));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
                    httpPost2.setEntity(entity);
                    response = httpClient.execute(httpPost2);
                    String content2 = EntityUtils.toString(response.getEntity(), "gbk");
                    System.out.println(content2);
                    JSONObject object2 = new JSONObject(content2);
                    String infoHash = object2.get("info_hash").toString();
                    String path = object2.get("torrent_name").toString();
                    JSONArray array = object2.getJSONArray("torrent_filelist_web");
                    String wanted = "";
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject objectTmp = array.getJSONObject(i);
                        int size = objectTmp.getInt("size");
                        String pathName = objectTmp.getString("path");
                        System.out.println(size);
                        if(pathName.contains(".jpg") || size>104857600){
                            wanted += i+",";
                        }
                    }
                    System.out.println(wanted);
//                    HttpPost httpPost3 = new HttpPost("http://115.com/lixian/?ct=lixian&ac=add_task_bt");
//                    httpPost3.setHeader("Cookie","115_lang=zh; payment=alipay; ssov_2955186=1_2955186_181c9134447ce8222432f8f4e9bc0352; UUID=11555B84B373A6D0; UUTK=fb8e44GeIl56c5%2Fnq5NNlwKGuxbbxom%2F2ZG9ZN%2FFivo126jfWZbtsmsMinnOa9vLKtjN2Vm%2FL8MR8%2FsQTZPnepSZVZ%2F2XMXfk7%2Fak; loginType=1; UID=2955186_A1_1438141248; CID=275d5e5c4d7672839faef61ff63db327; SEID=c6675c08aea0993469e65e7d7ee7c2d6f7cfd9609db2a18fa6aa9adb28e4c7bcaa361c5f7521b5946bce679071729770e13306e076857093a71770b2; OOFA=%2507%250ETSWZ%2507%250CC%2505%2540%250BX%2505LZW%2502%255CU%250D%2506%250BT%2506TPQ%255B%255B%2502%2502%250F%2507%2502U%2502%2504S%2507%2507%2502%255C%2500%2500V%2507Q%2506YP%2506Z%250C%2500U%2501%2507%2507%2507ZS%255EQ%250C; OOFL=2955186; __utmt=1; __utma=48116967.2024335568.1424070885.1438138556.1438141246.36; __utmb=48116967.1.10.1438141246; __utmc=48116967; __utmz=48116967.1424070885.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
//                    httpPost3.setHeader("Referer","http://115.com/?ac=offline_tpl&is_wl_tpl=1");
//                    httpPost3.setHeader("Host", "115.com");
//                    httpPost3.setHeader("Origin", "http://115.com");
//                    List<NameValuePair> list2 = new ArrayList<NameValuePair>();
//                    list2.add(new BasicNameValuePair("info_hash", infoHash));
//                    list2.add(new BasicNameValuePair("wanted", "0"));
//                    list2.add(new BasicNameValuePair("savepath", path));
//                    list2.add(new BasicNameValuePair("uid", "2955186"));
//                    list2.add(new BasicNameValuePair("sign", "2df57e7e111be9d368a2877272494b9d"));
//                    list2.add(new BasicNameValuePair("time", "1438141257"));
//                    UrlEncodedFormEntity entity2 = new UrlEncodedFormEntity(list2, Consts.UTF_8);
//                    httpPost3.setEntity(entity2);
//                    response = httpClient.execute(httpPost3);
//                    String content3 = EntityUtils.toString(response.getEntity(), "gbk");
//                    System.out.println(content3);
                }
            }
        } finally {
            response.close();
        }
    }

    private static void showName() throws Exception {
        String url = "http://115.com/lixian/?ct=lixian&ac=task_lists";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("page", "1"));
        list.add(new BasicNameValuePair("uid", "2955186"));
        list.add(new BasicNameValuePair("sign", "2df57e7e111be9d368a2877272494b9d"));
        list.add(new BasicNameValuePair("time", "1438141257"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httppost.setHeader("Cookie", "115_lang=zh; payment=alipay; ssov_2955186=1_2955186_181c9134447ce8222432f8f4e9bc0352; MSG_OPEN_ALERT=1; tjj_u=1; tjj_id=143813855636884198342; UUID=11555B84B373A6D0; UUTK=fb8e44GeIl56c5%2Fnq5NNlwKGuxbbxom%2F2ZG9ZN%2FFivo126jfWZbtsmsMinnOa9vLKtjN2Vm%2FL8MR8%2FsQTZPnepSZVZ%2F2XMXfk7%2Fak; loginType=1; UID=2955186_A1_1438141248; CID=275d5e5c4d7672839faef61ff63db327; SEID=c6675c08aea0993469e65e7d7ee7c2d6f7cfd9609db2a18fa6aa9adb28e4c7bcaa361c5f7521b5946bce679071729770e13306e076857093a71770b2; OOFA=%2507%250ETSWZ%2507%250CC%2505%2540%250BX%2505LZW%2502%255CU%250D%2506%250BT%2506TPQ%255B%255B%2502%2502%250F%2507%2502U%2502%2504S%2507%2507%2502%255C%2500%2500V%2507Q%2506YP%2506Z%250C%2500U%2501%2507%2507%2507ZS%255EQ%250C; OOFL=2955186; IM_ALERT_IND=1; 2955186_settings=b6589fc6ab0dc82cf12099d1c2d40ab994e8410c;");
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
}
