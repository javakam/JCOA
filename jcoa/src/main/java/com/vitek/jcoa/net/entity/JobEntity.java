package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 职务查询
 * <p>
 * 参数名称
 * 无参数
 * <p>
 * {"ispass":true,"defaultMessage":"获取成功",
 * "models":[{"id":1,"role_name":"管理员","powerid":6}
 * ,{"id":2,"role_name":"行政主任","powerid":2}
 * ,{"id":3,"role_name":"大队长","powerid":5}
 * ,{"id":4,"role_name":"副大队长","powerid":3}
 * ,{"id":5,"role_name":"科长","powerid":2}
 * ,{"id":6,"role_name":"科员","powerid":1}]}
 * <p>
 * Created by javakam on 2017/5/18 0018.
 */
public class JobEntity extends BaseEntity {

    private List<ModelsBean> models;

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean {
        public ModelsBean() {
        }

        public ModelsBean(int id, String role_name, int powerid) {
            this.id = id;
            this.role_name = role_name;
            this.powerid = powerid;
        }

        @Override
        public String toString() {
            return "ModelsBean{" +
                    "id=" + id +
                    ", role_name='" + role_name + '\'' +
                    ", powerid=" + powerid +
                    '}';
        }

        /**
         * id : 1
         * role_name : 管理员
         * powerid : 6
         */

        private int id;
        private String role_name;
        private int powerid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public int getPowerid() {
            return powerid;
        }

        public void setPowerid(int powerid) {
            this.powerid = powerid;
        }
    }
}
