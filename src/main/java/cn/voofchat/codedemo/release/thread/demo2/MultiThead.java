package cn.voofchat.codedemo.release.thread.demo2;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午2:57
 * Description:
 * 多个线程多个锁的问题：多个线程，每个线程都可以拿到自己指定的锁，分别获得锁之后，执行synchronized方法体的内容
 *      关键字synchronized取得的锁都是对象锁，而不是把一段代码（方法）当做锁，所以那个线程执行synchronized关键字的方法，那个线程就持有该方法所属对象的锁（Lock）,线程获得的就是不同的对象锁，因此他们互不相影响
 *      有一种情况则是相同的锁，即在静态方法上加synchronized关键字，表示锁定.class类，类一级别的锁（独占.class类）
 */
public class MultiThead {

    /**
     *
     */
//    private static Integer num = 0;
    private Integer num = 0;

//    public static synchronized void printNum(String tag){
    public synchronized void printNum(String tag){

        try {
            if ("a".equals(tag)){
                num = 100;
                System.out.println("tag a , set num over !" );
                Thread.sleep(1000);
            }else{
                num = 200;
                System.out.println("tag b, set num over !");
            }
            System.out.println("tag " + tag + ", num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        final MultiThead m1 = new MultiThead();
        final MultiThead m2 = new MultiThead();


//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                m1.printNum("a");
//            }
//        });


//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                m2.printNum("b");
//            }
//        });

        Thread t1 = new Thread( () -> m1.printNum("a"));
        Thread t2 = new Thread( () -> m2.printNum("b"));


        t1.start();
        t2.start();

    }
}
