package com.vitek.jcoa.net.entity;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class BeanTest {
    private String date;
    private String state;
    private String name;

    public BeanTest(String date, String state, String name) {
        this.date = date;
        this.state = state;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
