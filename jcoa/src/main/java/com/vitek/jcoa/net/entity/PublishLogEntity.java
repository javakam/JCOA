package com.vitek.jcoa.net.entity;

/**
 * 发布日志/jc_oa/addlog (POST))
 * <p>
 * 参数名称	        参数类型	        参数说明
 * username
 * content
 * ispublished		                1,0（1：发布，2：保存为草稿）
 * workstatus		                工作状态 不为空,填二级选项的字符串
 * date		                        日期
 * <p>
 * Created by javakam on 2017/5/18 0018.
 */

public class PublishLogEntity extends BaseEntity {

    /**
     * models : null
     * logExtends : null
     */

    private Object models;
    private Object logExtends;

    public Object getModels() {
        return models;
    }

    public void setModels(Object models) {
        this.models = models;
    }

    public Object getLogExtends() {
        return logExtends;
    }

    public void setLogExtends(Object logExtends) {
        this.logExtends = logExtends;
    }
}
