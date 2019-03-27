package cn.voofchat.exercise.demo7;

import java.util.Random;
import java.util.concurrent.DelayQueue;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/22
 * @time: 11:14 AM
 * Description:
 */
public class DelayBlockQueueTest {

    private static DelayQueue<Task> delayQueue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
        delayQueue.add(new Task("t1", 5 * 1000 + System.currentTimeMillis()));
        delayQueue.add(new Task("t2", 2 * 1000 + System.currentTimeMillis()));
        delayQueue.add(new Task("t3", 6 * 1000 + System.currentTimeMillis()));
//
//        Task take = delayQueue.take();
//        System.out.println(take);
//
//        take = delayQueue.take();
//        System.out.println(take);
//
//        take = delayQueue.take();
//        System.out.println(take);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 3; i++) {
//                   try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    Integer random = new Random().nextInt(9) + 1;
//                    System.out.println("random:" + random);
//                    delayQueue.add(new Task("t" + i, random * 1000 + System.currentTimeMillis()));
//                }
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!delayQueue.isEmpty()) {
                    System.out.println("size:" + delayQueue.size());
                    Task task = null;
                    try {
                        task = delayQueue.take();
                        System.out.println(task);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("执行完毕");
            }
        }).start();

    }

}
