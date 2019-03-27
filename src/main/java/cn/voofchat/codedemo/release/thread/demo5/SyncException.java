package cn.voofchat.codedemo.release.thread.demo5;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午4:34
 * Description: 锁出现异常会自动释放锁
 */
public class SyncException {

    /**
     *
     */
    private Integer num = 0;


    public synchronized void operation(){
        while (num < 20){
            try{
                num++;
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName() + ", num = " + num);
                if (num == 10){
                    Integer.parseInt("a");
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("log info [ num = " + num + " ]") ;
            }
        }
    }

    public static void main(String[] args) {
        SyncException se = new SyncException();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> se.operation()).start();
        }
    }

}
