package com.xing.libao;

import com.xing.libao.cron.CL115Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/3/25
 * Time: 14:00
 */
public class Main {
    public static void main(String[] args) {
        if(args == null || args.length<1) {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
            context.start();
        }else{
            CL115Task cl115Task = new CL115Task();
            cl115Task.test();
        }
    }
}
