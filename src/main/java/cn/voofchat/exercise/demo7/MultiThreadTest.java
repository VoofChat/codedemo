package cn.voofchat.exercise.demo7;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/21
 * @time: 11:31 AM
 * Description:
 */
public class MultiThreadTest {

    private static ConcurrentLinkedQueue<String> workQueue = new ConcurrentLinkedQueue<>();

    private static BlockingQueue<String> resultQueue = new LinkedBlockingQueue<>();

    private static AtomicInteger count = new AtomicInteger();

    private static CountDownLatch countDownLatch = new CountDownLatch(8);

    public static void main(String[] args) throws InterruptedException {

        System.out.println("准备数据...");
        for (int i = 0 ; i < 10000; i++ ) {
            workQueue.offer("work-" + i);
        }

        System.out.println("线程消费...");
        for (int i = 0 ; i < 32 ; i++ ) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!workQueue.isEmpty()) {
                        String resource = workQueue.poll();
                        System.out.println(Thread.currentThread().getName() + "-消费-" + resource);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String result = "result-" + resource;
                        resultQueue.offer(result);
                    }

                    System.out.println("工作-消费线程执行完毕：" + Thread.currentThread().getName() );
                }
            }).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0 ; i < 8; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String resource = null;
                        try {
                            resource = resultQueue.poll(10, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }

                        if (resource == null) {
                            System.out.println("十秒钟内未获取到结果，退出");
                            break;
                        }

                        System.out.println(Thread.currentThread().getName() + "-消费-" + resource);
                        count.addAndGet(1);
                    }
                    System.out.println("结果-消费线程执行完毕：" + Thread.currentThread().getName() );
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();
        System.out.println("结果消费总数：" + count.get());

    }
}
