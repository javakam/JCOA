package com.vitek.jcoa.net.entity;

/**
 * 登录实体
 * <p>
 * 字段名称	           字段类型	     说明
 * ispass	           boolean	     true：成功，false：失败
 * defaultMessage	   String	     提示信息
 * <p>
 * Created by javakam on 2017/5/18 0018.
 */

public class LoginEntity extends BaseEntity {
    /**
     * models : null
     * Cookie : 111E306928731F1ED6FBC4F52B497EBA
     */

    private Object models;
    private String Cookie;


    public Object getModels() {
        return models;
    }

    public void setModels(Object models) {
        this.models = models;
    }

    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String Cookie) {
        this.Cookie = Cookie;
    }

}
