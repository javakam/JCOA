package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 查询当前登录用户今天是否已发布日志
 * <p>
 * Created by javakam on 2017/5/21 0021.
 */

public class WebChecklogEntity extends BaseEntity {

    private List<?> models;

    public List<?> getModels() {
        return models;
    }

    public void setModels(List<?> models) {
        this.models = models;
    }
}
