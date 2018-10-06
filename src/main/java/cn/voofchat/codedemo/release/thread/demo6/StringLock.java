package cn.voofchat.codedemo.release.thread.demo6;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午5:02
 * Description:
 *  synchronized代码块对字符串的锁，注意String常量池的缓存功能
 */
public class StringLock {

    private void method(){
        synchronized ("字符串常量"){
            try{
                while (true){
                    System.out.println("当前线程：" + Thread.currentThread().getName() + ",开始");
                    Thread.sleep(1000);
                    System.out.println("当前线程：" + Thread.currentThread().getName() + ",结束");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果是字符串常量的话，锁切换不了
     */
    public static void main(String[] args) {
        StringLock sl = new StringLock();
        new Thread(() -> sl.method(),"t1").start();
        new Thread(() -> sl.method(),"t2").start();

    }
}
