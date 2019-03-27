package cn.voofchat.codedemo.release.thread.demo3;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午3:33
 * Description:
 *
 * 对象锁，同步和异步的概念
 * 同步：synchronized 同步的概念就是共享
 * 异步：asynchronized 异步的概念就是独立，相互之间不收任何约束。
 * 同步的目的就是为了线程安全，其实对于线程安全来哦说，需要满足两个特性：
 *  原子性（同步）
 *  可见性
 */
public class MyObject {

    public synchronized void method1(){
        System.out.println( Thread.currentThread().getName() + ", method1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * 如果在method2上加上synchronized 关键字，结果会是怎样
     */
//    public void method2(){
    public synchronized void method2(){

        System.out.println( Thread.currentThread().getName() + ", method2");
    }

    /***
     * 分析：
     * t1 线程先持有object对象的lock锁，t2线程可以以异步的方式调用对象中的非synchronized修饰的方法
     * t1 线程先持有object对象的lock锁，t2线程如果在这个时候调用对象中的同步（synchronized）方法则需要等待，也就是同步
     */
    public static void main(String[] args) {
        MyObject mo = new MyObject();
        Thread t1 = new Thread(() -> mo.method1(), "t1");
        Thread t2 = new Thread(() -> mo.method2(), "t2");
        t1.start();
        t2.start();
    }
}
