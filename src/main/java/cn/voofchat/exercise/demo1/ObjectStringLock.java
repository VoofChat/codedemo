package cn.voofchat.exercise.demo1;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/23
 * @time: 2:03 PM
 * Description:
 */
public class ObjectStringLock {

    /**
     * 常量加锁的情况
     */

    private void print1(){

        synchronized ("字符串常量") {
//            while(true) {
                System.out.println(Thread.currentThread().getName() + "\t 开始");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "\t 结束");
            }
//        }
    }

    public static void main(String[] args) {
        ObjectStringLock osl = new ObjectStringLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                osl.print1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                osl.print1();
            }
        }).start();
    }
}
