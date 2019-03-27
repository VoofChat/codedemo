package cn.voofchat.exercise.demo7;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/22
 * @time: 5:23 PM
 * Description:
 */
public class Demo {

    public String print(){

        Integer i = 100;
        i++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i.toString();
    }
}
