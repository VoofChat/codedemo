package cn.voofchat.exercise.demo8;

import org.apache.commons.lang3.time.DateUtils;

import java.net.ServerSocket;
import java.util.Date;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/3/6
 * @time: 5:46 PM
 * Description:
 */
public class App {


    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//        Date t1 = new Date();
//        Date t2 = DateUtils.addHours(new Date(), 48);
//
//        long t3 = (t2.getTime() - t1.getTime()) / (24 * 60 * 60 * 1000);
//        System.out.println(t1.getTime());
//        System.out.println(t2.getTime());
//        System.out.println(t3);

        Date date1 = new Date();
        Date date2 = DateUtils.addHours(new Date(), 12);

        boolean sameDay = DateUtils.isSameDay(date1, date2);
        System.out.println(sameDay);


    }
}
