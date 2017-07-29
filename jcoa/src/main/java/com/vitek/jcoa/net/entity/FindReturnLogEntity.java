package com.vitek.jcoa.net.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 退件箱
 * <p>
 * Created by javakam on 2017/6/7 0007.
 */
public class FindReturnLogEntity extends BaseEntity implements Serializable {

    private List<ModelsBean> models;

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean implements Serializable{
        /**
         * id : 552
         * date : 1496937600000
         * username : weixiao
         * content : ceshi
         * ispublished : 3
         * workstatus : 单位办公
         */

        private int id;
        private long date;
        private String username;
        private String content;
        private int ispublished;
        private String workstatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIspublished() {
            return ispublished;
        }

        public void setIspublished(int ispublished) {
            this.ispublished = ispublished;
        }

        public String getWorkstatus() {
            return workstatus;
        }

        public void setWorkstatus(String workstatus) {
            this.workstatus = workstatus;
        }
    }
}
