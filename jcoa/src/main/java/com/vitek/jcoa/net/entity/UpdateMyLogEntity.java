package com.vitek.jcoa.net.entity;

/**
 * 更新日志草稿
 * <p>
 * ispublished		1,0（1：发布，0：保存为草稿）
 * workstatus		工作状态 不为空,填二级选项的字符串
 * <p>
 * Created by javakam on 2017/5/21 0021.
 */
public class UpdateMyLogEntity extends BaseEntity {

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
