package com.xing.libao.init;

import com.xing.libao.util.RequestUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/8/4
 * Time: 17:06
 */
public class CLImage {
    public static void main(String[] args) {
        RequestUtil requestUtil = RequestUtil.getInstance();
        String content = requestUtil.getHtml("http://dz.cldz.biz/htm_data/15/1507/1563149.html");
        String title = getPattern("<b>本頁主題:</b> (.*?)</td>", content);
        title = title.replaceAll("\\[.*?\\]", "");
        String fan = title.substring(0, 6);
        System.out.println(fan);
        Pattern pattern = Pattern.compile("<img src='(.*?)'", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String group = matcher.group(1);
            if (group.contains(fan.toLowerCase())) {
                String fileName = group.substring(group.lastIndexOf("/") + 1, group.length());
                System.out.println(fileName);
                System.out.println(group);
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
}
