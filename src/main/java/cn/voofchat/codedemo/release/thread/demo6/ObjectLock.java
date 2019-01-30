package cn.voofchat.codedemo.release.thread.demo6;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午4:51
 * Description:
 */
public class ObjectLock {

    /**
     * 对象锁
     */
    public void method1(){
        synchronized (this){
            try{
                System.out.println(" do method1 ...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 类对象锁
     */
    public void method2(){
        synchronized (ObjectLock.class){
            try{
                System.out.println(" do method2 ...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 任何对象锁
     */
    private Object objectLock = new Object();
    public void method3(){
        synchronized (objectLock){
            try{
                System.out.println(" do method3 ...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ObjectLock ol = new ObjectLock();
        new Thread(() -> ol.method1()).start();
        new Thread(() -> ol.method1()).start();
        new Thread(() -> ol.method2()).start();
        new Thread(() -> ol.method2()).start();
        new Thread(() -> ol.method3()).start();
        new Thread(() -> ol.method3()).start();
    }
}
