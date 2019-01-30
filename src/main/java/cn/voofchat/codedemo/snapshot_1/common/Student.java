package cn.voofchat.codedemo.snapshot_1.common;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/8
 * @time: 下午8:02
 * Description:
 */
public class Student {

    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String address;

    private String gsxTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGsxTime() {
        return gsxTime;
    }

    public void setGsxTime(String gsxTime) {
        this.gsxTime = gsxTime;
    }
}
