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

import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Administrator on 2015/7/18 0018.
 */
public class AddTask {
    private static String cookie = "115_lang=zh; payment=alipay; ssov_2955186=1_2955186_181c9134447ce8222432f8f4e9bc0352; PHPSESSID=d1vt5tbm13fol11qbn0p2btk53; MSG_OPEN_ALERT=1; UUID=11555AF6180B1D19; UUTK=460c92KMsXGr3mZoop1n%2Femd0XpLsSvO36U%2FFPrgPsXShMqh4v2jQW53%2FM7v2urkt6QXote%2FMVAiEx1fExmg7Gl0PV3ckbblb3I3U; loginType=1; UID=2955186_A1_1437557127; CID=c0f3668b6bc3bed15cef5d7c73ea9e0e; SEID=4d786f41726f148db468f78f634ca7fc5cd5498fd0dea1d087dc3e5b6949aa760efb4dabb7a4db186c928cf4eef0f29976a183483dd6616eebe271da; OOFA=%2507%250ETSWZ%2507%250CC%2505%2540%250BX%2505LZW%2502%255CU%250D%2506%2504P%2507RSWT%255B%2502%2502%250F%2506SW%2505W%2507%2502%2506%2505%255E%2500%2501Q%2500P%2506%2500P%2506%2501%2501%2505%2506%2505V%2500%2501T%2505%250B%250A%2505; OOFL=2955186; IM_ALERT_IND=7; tjj_repeat=0; tjj_u=1; tjj_id=143755713029968723672; __utma=48116967.2024335568.1424070885.1437471619.1437557130.34; __utmb=48116967.1.10.1437557130; __utmc=48116967; __utmz=48116967.1424070885.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)";
    private static Map<String, String> tasks = new HashMap<String, String>();

    static {
        tasks.put("Tokyo Hot n1043", "magnet:?xt=urn:btih:719B1EF8EDE670AE13938F074E195DF38EEA2B31");
        tasks.put("Tokyo Hot n0051", "magnet:?xt=urn:btih:6C1B87A822FE981AD8B717E26979717B5C983162");
        tasks.put("Tokyo Hot k1154", "magnet:?xt=urn:btih:7994351465183FEA6ECB9CAFB14ED822BA2C2BED");
        tasks.put("Tokyo Hot k1149", "magnet:?xt=urn:btih:7DD2A5C6748AF8EB0C3244C61156F764788AC349");
        tasks.put("Tokyo Hot n1016", "magnet:?xt=urn:btih:7F31167A6E443CF6C8D371868A105E476089849D");
        tasks.put("Tokyo Hot n0994", "magnet:?xt=urn:btih:7F87241E1CDA787228407EA160A0246DC5F66D62");
        tasks.put("Tokyo Hot n0662", "magnet:?xt=urn:btih:8032456E18F581DF7109140A96400E6585B754F2");
        tasks.put("Tokyo Hot.13.10.18", "magnet:?xt=urn:btih:7F5DA64FDD9BB87113DA255F3F426759EBF34CBC");
        tasks.put("Tokyo Hot n0697", "magnet:?xt=urn:btih:6533F87CE0525358CE22EFA0E2F9661116A83BFD");
        tasks.put("Tokyo Hot n0753", "magnet:?xt=urn:btih:727212A663474EB75DAD3A85D54A28609DB741BB");
        tasks.put("Tokyo Hot n0784", "magnet:?xt=urn:btih:6FE9CC5B130289D0B8F624A4046CAA19CA0597CD");
        tasks.put("Tokyo Hot n0796", "magnet:?xt=urn:btih:6642EAD3017BD8A8E18A6191675A052416C2A961");
        tasks.put("Tokyo Hot n0699", "magnet:?xt=urn:btih:7D02382081417355730A897FC4D1F9214C105295");
        tasks.put("Tokyo Hot n0868", "magnet:?xt=urn:btih:7D02B1D7B5C0647DF36077F620A55A687EC6C8DF");
        tasks.put("Tokyo Hot n0282", "magnet:?xt=urn:btih:6BBC1E6E82D711CF5F168D2604C03B5CAC760953");
        tasks.put("Tokyo Hot n1013", "magnet:?xt=urn:btih:77C38548EEB29AA60933EA736CDC07047CE33297");
        tasks.put("Tokyo Hot n1037", "magnet:?xt=urn:btih:601AD408AB33820CECE2B21EA52C8A9157659CF1");
        tasks.put("Tokyo Hot n1042", "magnet:?xt=urn:btih:7779D4EEA877B265F9AA8AC3BE183D377DBC4036");
        tasks.put("Tokyo Hot k1145", "magnet:?xt=urn:btih:7791DBD1247549ECD88D12783D251D003EB1AC26");
        tasks.put("Tokyo Hot n1038", "magnet:?xt=urn:btih:743D91CDA319A5E81AE282FD5BE549F9B7E8ABF9");
        tasks.put("Tokyo Hot n0866", "magnet:?xt=urn:btih:746CD5D5426804BC3ACFD414B0485E622AB63985");
        tasks.put("Tokyo Hot n0328", "magnet:?xt=urn:btih:7601976C83E9BC71534534A6202C48BE0F4D41B3");
        tasks.put("Tokyo Hot n0483", "magnet:?xt=urn:btih:7A31A1DF31C912F27A0482E3A63758438FC4708E");
        tasks.put("Tokyo Hot k1153", "magnet:?xt=urn:btih:74BF832393D69EA34A23653B3C4389CF7D7B1ABB");
        tasks.put("Tokyo Hot n1016", "magnet:?xt=urn:btih:489A455313BD2B3476C6AF68B12767342E1183C0");
        tasks.put("Tokyo Hot k1150", "magnet:?xt=urn:btih:503E0EB070FE20EEE9E19FA85A060D50EA691375");
        tasks.put("Tokyo Hot n1042", "magnet:?xt=urn:btih:55FC5FB43550220BFAD10C53436CDD74CC00AA29");
        tasks.put("Tokyo Hot n0787", "magnet:?xt=urn:btih:657E167E998EAFC2517C92257B22F5D55D37548B");
        tasks.put("Tokyo Hot n0714", "magnet:?xt=urn:btih:6EBA7C3537FC4D6E2BE752D9ECB34E102776EF01");
        tasks.put("Tokyo Hot n1040", "magnet:?xt=urn:btih:6F63465612B700D36682CAD819C63C880113B332");
        tasks.put("Tokyo Hot n0798", "magnet:?xt=urn:btih:69226DA11FB43D4E439CB1BC49DE836D8C6D124D");
        tasks.put("Tokyo Hot k1149", "magnet:?xt=urn:btih:666BC3CF51BEA642CECC0B8941CEC04ADCBB86CE");
        tasks.put("Tokyo Hot n0917", "magnet:?xt=urn:btih:1F3BB3D74F06EED2256C1DC66766D6C77A611E26");
        tasks.put("Tokyo Hot n1017", "magnet:?xt=urn:btih:4400E888472A89131DA2BCF38BA78BD53267E6AB");
        tasks.put("Tokyo Hot n0074", "magnet:?xt=urn:btih:4DE2B7969F420E446328704678167DC8B29DECD8");
        tasks.put("Tokyo Hot n0811", "magnet:?xt=urn:btih:5E2E7259EB500BA846F74EDBEF62F9E85981FB4F");
        tasks.put("Tokyo Hot n1040", "magnet:?xt=urn:btih:5F09C5071F96E6EC256AFC8379179C72174723DA");
        tasks.put("Tokyo Hot n1039", "magnet:?xt=urn:btih:5AF9379553C1B1FA598EAFD5C95BAA4C967D4D8D");
        tasks.put("Tokyo Hot n1036", "magnet:?xt=urn:btih:5C287932231BB5AC340356A49D879B4D3CD77CDA");
        tasks.put("Tokyo Hot n0855", "magnet:?xt=urn:btih:5CCCAAA93B4C0A5624C9CF75A2FCC86C27D350B1");
        tasks.put("Tokyo Hot n1041", "magnet:?xt=urn:btih:5D0953418FC01E70FC74A550C3E9D65C5751B79C");
        tasks.put("Tokyo Hot n0708", "magnet:?xt=urn:btih:5951EAB1E3806EFDDBB84FE503CD2D4C290D50CB");
        tasks.put("Tokyo Hot n0755", "magnet:?xt=urn:btih:4227F5A18B2DB80924C4B15F41BF4E6425388E1B");
        tasks.put("Tokyo Hot n0872", "magnet:?xt=urn:btih:1BCB7F9191AD70BF8EEC709762DB332D1C6FAFDA");
        tasks.put("Tokyo Hot n0871", "magnet:?xt=urn:btih:1AFD706FBC5CE14E47A9CEC7A8CF9F896FD77AC7");
        tasks.put("Tokyo Hot n0802", "magnet:?xt=urn:btih:16728FC8C446E0137C84556545ED9D5CF0B4ACF2");
        tasks.put("Tokyo Hot n0406", "magnet:?xt=urn:btih:1448D344130842514D089A445CE5D9BC6FA4B019");
        tasks.put("Tokyo Hot n0784", "magnet:?xt=urn:btih:36E13886BCFE7AD5A8446CB921D3F2D1688B42B4");
        tasks.put("Tokyo Hot n0617", "magnet:?xt=urn:btih:0B86A706E732896DC8B840CA76A7EBD0D12588C5");
        tasks.put("Tokyo Hot n1010", "magnet:?xt=urn:btih:3AED1DA5A7EF178A7A1A516788354790FC597098");
        tasks.put("Tokyo Hot n0872", "magnet:?xt=urn:btih:47A0AB04D16A226C512B963B14AC0BB6AAB9D807");
        tasks.put("Tokyo Hot n0811", "magnet:?xt=urn:btih:421F917919FBC977C219E4A71011495ED277A606");
        tasks.put("Tokyo Hot n0765", "magnet:?xt=urn:btih:0371157AE42AA4F931EB875A7E79501CD687CBA6");
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
        String url = "http://web.api.115.com/files/search?offset=0&limit=30&search_value=" + URLEncoder.encode(searchWord,"utf-8") + "&date=&aid=-1&pick_code=&type=&source=&format=json";
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
