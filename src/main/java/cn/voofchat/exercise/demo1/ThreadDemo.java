package cn.voofchat.exercise.demo1;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/23
 * @time: 1:11 PM
 * Description:
 */
public class ThreadDemo{

    /**
     * 票数
     */
    private static Boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(flag) {
                    System.out.println("hahahaha");
                }
            }
        }).start();

        Thread.sleep(1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                flag = false;
            }
        }).start();
    }
}
