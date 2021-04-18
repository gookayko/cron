package com.xing.libao.cron;

import com.xing.libao.util.RequestUtil;
import org.apache.http.client.methods.HttpGet;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/8/4
 * Time: 14:44
 */
public class CheckOut {
    public static void main(String[] args) {
        String url = "http://mapp.115.com/?ct=checkin";
        RequestUtil requestUtil = RequestUtil.getInstance();
        HttpGet get = new HttpGet(url);
        get.setHeader("Cookie","115_lang=zh; ssov_2955186=1_2955186_181c9134447ce8222432f8f4e9bc0352; __utma=48116967.1170559670.1437994038.1437994038.1438585703.2; __utmz=48116967.1437994038.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); loginType=1; UID=2955186_A1_1438659828; CID=c9e8d010ede64fe69ad00b2207034624; SEID=c6675c08aea0993469e65e7d7ee7c2d6f7cfd9609db2a18fa6aa9adb28e4c7bcaa361c5f7521b5946bce679071729770e13306e076857093a71770b2; OOFA=%2507%250ETSWZ%2507%250CC%2505%2540%250BX%2505LZW%2502%255CU%250D%2506%250BS%2507%255CZW%255B%255B%2502%2502%250FS%2502%2500SPW%2504UR%255DP%2500%2503%2507%250A%2504%250B%255E%2507%250C%2501WP%2501VW%2501WU%250E%2507%2507; OOFL=2955186; PHPSESSID=0451jl7k92esp55cvdtv5ho4a6");
        String content = requestUtil.getHtml(get);
        System.out.println(content);
    }
}
