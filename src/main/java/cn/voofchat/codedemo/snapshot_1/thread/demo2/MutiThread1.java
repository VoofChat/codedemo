package cn.voofchat.codedemo.thread.demo2;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/1
 * @time: 上午10:16
 * Description:
 * 每个线程在执行synchronized方法体的时候，拿到的锁是该方法所属该对象的锁，因此不同对象执行synchronize的方法体是获得锁是不一样的
 * 还有一种情况是，在synchronized 修饰的方法体上加上static关键字，这样线程在执行synchronized该代码块的时候，拿到的锁就是类锁，因此多个线程竞争的都是同一把锁，这样就能是线程得到正确的执行
 */
public class MutiThread1 {

    /**
     *
     */
    private static Integer num = 0;

    public static synchronized void printNum(String tag){

        try{
            if ("a".equals(tag)){
                num = 100;
                System.out.println("tag a, set num over !");
                Thread.sleep(1000);
            }else{
                num = 200;
                System.out.println("tag b, set num over !");
            }
            System.out.println("tag " + tag + ", set num = " + num);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MutiThread1 m1 = new MutiThread1();
        MutiThread1 m2 = new MutiThread1();

        Thread t1 = new Thread(() -> m1.printNum("a"));
        Thread t2 = new Thread(() -> m2.printNum("b"));
        t1.start();
        t2.start();
    }

}
