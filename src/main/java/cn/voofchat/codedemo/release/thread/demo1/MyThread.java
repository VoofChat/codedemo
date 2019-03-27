package cn.voofchat.codedemo.release.thread.demo1;

import com.sun.javafx.binding.StringFormatter;

import java.util.concurrent.TimeUnit;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午2:37
 * Description:
 * 分析： 当多个线程访问MyThread的run方法时, 以排队的方式进行处理（这里排队的事按照cpu分配的先后顺序而定的）
 *      一个线程想要执行synchronized修饰的方法里的代码：
 *      1、尝试获得锁
 *      2、如果拿到锁，执行synchronized代码体内容：拿不到锁，这个线程就会不断的尝试获得这把锁，直到拿到为止，而且是多个线程同时去竞争这把锁。
 *
 *      该问题描述的是多个线程一个锁的问题
 *      问题：会导致锁竞争的问题，在锁竞争的那一瞬间会导致CPU利用率陡然提高，容易造成卡顿，甚至宕机
 */
public class MyThread extends Thread{

    /**
     * 票数
     */
    private Integer count = 5;

    @Override
//    public void run() {
    public synchronized void run() {
        super.run();
        try {
            Thread.sleep(0);
            count--;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread-" + currentThread().getName() + "\t count = " + count);
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();

        for (int i = 0; i < 5; i++) {
            new Thread(thread,"t" + i).start();
        }

    }
}
