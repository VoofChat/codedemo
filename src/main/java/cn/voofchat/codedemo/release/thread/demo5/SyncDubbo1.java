package cn.voofchat.codedemo.release.thread.demo5;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午4:23
 * Description:
 */
public class SyncDubbo1 {

    public synchronized void method1(){
        System.out.println(Thread.currentThread().getName() + ", method1 ......");
        this.method2();
    }

    public synchronized void method2(){
        System.out.println(Thread.currentThread().getName() + ", method2 ......");
        this.method3();
    }

    public synchronized void method3(){
        System.out.println(Thread.currentThread().getName() + ", method3 ......");
    }


    /**
     * synchronized 锁重入问题，在同一个对象的方法中，调用其他synchronized修饰过的方法，会再次拿到对象锁
     * @param args
     */
    public static void main(String[] args) {
        SyncDubbo1 sd = new SyncDubbo1();
        new Thread(() -> sd.method1(),"t1").start();
    }
}
