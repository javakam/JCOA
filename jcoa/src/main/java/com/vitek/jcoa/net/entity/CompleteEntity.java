package com.vitek.jcoa.net.entity;

/**
 * 归档+转交下一部门
 * Created by javakam on 2017/6/3 0003.
 */
public class CompleteEntity extends BaseEntity {
    @Override
    public String toString() {
        return "CompleteEntity{" +
                "models=" + models +
                '}';
    }

    /**
     * models : null
     */

    private Object models;

    public Object getModels() {
        return models;
    }

    public void setModels(Object models) {
        this.models = models;
    }
}
