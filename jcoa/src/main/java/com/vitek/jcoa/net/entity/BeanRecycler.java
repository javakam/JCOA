package com.vitek.jcoa.net.entity;

/**
 * Created by javakam on 2017/5/18 0018.
 */

public class BeanRecycler {
    private int _id;
    private String name;
    private int resId;
    private String path;

    public BeanRecycler() {
    }

    public BeanRecycler(int _id, String path) {
        this._id = _id;
        this.path = path;
    }

    public BeanRecycler(int _id, String name, int resId) {
        this._id = _id;
        this.name = name;
        this.resId = resId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
