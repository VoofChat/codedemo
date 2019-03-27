package cn.voofchat.exercise.demo7;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/22
 * @time: 2:52 PM
 * Description:
 */
public class Task implements Delayed {

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Long startTime;

    /**
     * TimeUnit
     */
    private TimeUnit unit = TimeUnit.SECONDS;

    public Task(String name, Long startTime) {
        this.name = name;
        this.startTime = startTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return startTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Task task = (Task) o;
        if (this.getDelay(null) > task.getDelay(null) ){
            return 1;
        }else {
            if (this.getDelay(null) < task.getDelay(null)) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", unit=" + unit +
                '}';
    }
}
