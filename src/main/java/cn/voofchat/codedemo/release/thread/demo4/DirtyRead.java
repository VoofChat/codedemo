package cn.voofchat.codedemo.release.thread.demo4;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/6
 * @time: 下午3:58
 * Description:
 */
public class DirtyRead {

    /**
     * 姓名
     */
    private String username = "zhangsan";

    /**
     * 密码
     */
    private String password = "123456";

    public synchronized void setValue(String username, String password){
        this.username = username;

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.password = password;

        System.out.println("setValue 最终结果： username = " + this.username + ", password = " + this.password);
    }

    /**
     * synchronized
     */
    public void getValue(){
        System.out.println("getValue 最终结果：username = " + this.username + ", password = " + this.password);
    }

    /**
     * 在setValue的时候，getValue 等待，可以解决脏读问题
     */
    public static void main(String[] args) throws InterruptedException {

        DirtyRead dr = new DirtyRead();
        new Thread(() -> dr.setValue("lisi","admin")).start();
        Thread.sleep(1000);
        dr.getValue();

    }
}
