package cn.voofchat.exercise.demo1;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/23
 * @time: 1:32 PM
 * Description:
 */
public class ObjectLock {

    private synchronized void print1(){

        System.out.println("print1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private synchronized void print2(){

        System.out.println("print2");

    }


    public static void main(String[] args) {

        ObjectLock ol = new ObjectLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ol.print1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ol.print2();
            }
        }).start();


    }
}
