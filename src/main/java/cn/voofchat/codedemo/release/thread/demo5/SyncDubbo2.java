package cn.voofchat.codedemo.release.thread.demo5;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午4:27
 * Description:
 */
public class SyncDubbo2 {

    static class Main{
        /**
         *
         */
        public Integer num = 10;

        public synchronized void printMainNum(){
            num--;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Main print, num = " + this.num);
        }
    }

    static class Sub extends Main{

        public synchronized void printSubNum() {
            while (num > 0){
                num--;
                System.out.println("Sub print , num = " + this.num);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.printMainNum();
            }
        }
    }

    public static void main(String[] args) {
        Sub sub = new Sub();
        new Thread(() -> sub.printSubNum()).start();
    }
}
