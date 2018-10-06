package cn.voofchat.codedemo.release.thread.demo6;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午4:56
 * Description: 锁对象的改变问题
 */
public class ChangeLock {

    private String lock = "lock";

    private void method(){
        synchronized (lock){
            System.out.println("当前线程： " + Thread.currentThread().getName() + "开始");
            try {
                lock = "change lock";
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程： " + Thread.currentThread().getName() + "结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeLock cl = new ChangeLock();
        Thread t1 = new Thread(() -> cl.method(), "t1");
        Thread t2 = new Thread(() -> cl.method(), "t2");
        t1.start();
        Thread.sleep(100);
        t2.start();
    }
}
