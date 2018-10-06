package cn.voofchat.codedemo.thread.demo2;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/1
 * @time: 上午10:16
 * Description:
 * 多个对象多把锁的问题
 * 多个线程，每个线程都有自己制定的锁，分别获得锁之后，执行synchronized方法体的内容
 */
public class MutiThread1 {

    /**
     *
     */
    private Integer num;

    public void printNum(String tag) {

    }
}
