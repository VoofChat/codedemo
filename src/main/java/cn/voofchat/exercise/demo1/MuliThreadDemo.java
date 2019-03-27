package cn.voofchat.exercise.demo1;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/23
 * @time: 1:22 PM
 * Description:
 */
public class MuliThreadDemo {

    /**
     *
     */
    private static Integer num = 0;


    private static synchronized void printTag(String tag) {

        if (tag.equals("a")) {
            num = 100;
            System.out.println("tag = " + tag + " set num !");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            num = 200;
            System.out.println("tag = " + tag + " set num !");
        }

        System.out.println("num = " + num);
    }


    public static void main(String[] args) {

        MuliThreadDemo mtd1 = new MuliThreadDemo();
        MuliThreadDemo mtd2 = new MuliThreadDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mtd1.printTag("a");
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                mtd2.printTag("b");
            }
        }).start();
    }
}

