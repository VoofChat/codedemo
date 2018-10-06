package cn.voofchat.codedemo.thread.demo1;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/1
 * @time: 上午8:38
 * Description:
 * 多个线程一把锁的问题
 */
public class MyThread extends Thread {

    /**
     * 票的个数
     */
    private Integer count = 5;

    @Override
    public synchronized  void run() {
//    public void run() {
        super.run();

        count--;
        System.out.println(this.currentThread().getName() + "\t count = " + count);
    }

    public static void main(String[] args) {
        /***
         * 分析： 当多个线程访问MyThread的run方法时, 以排队的方式进行处理（这里排队的事按照cpu分配的先后顺序而定的）
         *      一个线程想要执行synchronized修饰的方法里的代码：
         *      1、尝试获得锁
         *      2、如果拿到所，执行synchronized代码体内容：拿不到锁，这个线程就会不断的尝试获得这把锁，直到拿到为止，而且是多个线程同时去竞争这把锁。
         */

        MyThread thread = new MyThread();
        Thread t1 = new Thread(thread);
        Thread t2 = new Thread(thread);
        Thread t3 = new Thread(thread);
        Thread t4 = new Thread(thread);
        Thread t5 = new Thread(thread);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }
}
