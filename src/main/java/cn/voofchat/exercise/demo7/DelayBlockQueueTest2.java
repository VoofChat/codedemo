package cn.voofchat.exercise.demo7;

import java.util.Random;
import java.util.concurrent.DelayQueue;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/22
 * @time: 11:14 AM
 * Description:
 */
public class DelayBlockQueueTest2 {

    private static DelayQueue<Task> delayQueue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
//        delayQueue.add(new Task("t1", 5 * 1000 + System.currentTimeMillis()));
//        delayQueue.add(new Task("t2", 2 * 1000 + System.currentTimeMillis()));
//        delayQueue.add(new Task("t3", 6 * 1000 + System.currentTimeMillis()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                delayQueue.add(new Task("t1", 5 * 1000 + System.currentTimeMillis()));
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Task t = null;
                try {
                    t = delayQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(t);
            }
        }).start();



    }

}
