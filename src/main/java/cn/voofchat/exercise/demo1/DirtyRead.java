package cn.voofchat.exercise.demo1;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/23
 * @time: 1:39 PM
 * Description:
 */

public class DirtyRead {

    /**
     * 用户名
     */
    private String username = "zzx";

    /**
     * 密码
     */
    private String password = "123456";

    public synchronized void setUsernameAndPassword(String username, String password) {

        this.username = username;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.password = password;

        System.out.println("username = " + this.username + ", password = " + password);
    }


    public void getUsernameAndPassword(){
        System.out.println("username = " + this.username + ", password = " + password);
    }

    public static void main(String[] args) throws InterruptedException {
        DirtyRead dr = new DirtyRead();
        dr.getUsernameAndPassword();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dr.setUsernameAndPassword("zhangsan", "098765");
            }
        }).start();

        Thread.sleep(100);

        dr.getUsernameAndPassword();
    }
}
