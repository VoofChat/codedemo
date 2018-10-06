package cn.voofchat.codedemo.snapshot_1.thread.demo3;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午3:43
 * Description:
 */
public class MyObject {

    public synchronized void method1(){
        System.out.println(Thread.currentThread().getName() + " , method1!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("execute bussiness over!");
    }

    public void method2(){
        System.out.println(Thread.currentThread().getName() + ", method2 !");
    }

    public static void main(String[] args) {
        MyObject mo = new MyObject();
        new Thread(()-> mo.method1(),"t1").start();
        new Thread(()-> mo.method2(),"t2").start();
    }
}
