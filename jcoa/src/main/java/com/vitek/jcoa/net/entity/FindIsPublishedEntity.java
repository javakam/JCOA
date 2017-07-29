package com.vitek.jcoa.net.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 查询日志草稿
 * <p>
 * Created by javakam on 2017/5/21 0021.
 */

public class FindIsPublishedEntity extends BaseEntity implements Serializable {
    @Override
    public String toString() {
        return "FindIsPublishedEntity{" +
                "models=" + models +
                '}';
    }

    private List<ModelsBean> models;

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean implements Serializable {
        @Override
        public String toString() {
            return "ModelsBean{" +
                    "id=" + id +
                    ", date=" + date +
                    ", username='" + username + '\'' +
                    ", content='" + content + '\'' +
                    ", ispublished=" + ispublished +
                    ", workstatus='" + workstatus + '\'' +
                    '}';
        }

        /**
         * id : 200
         * date : 1493913600000
         * username : weixiao
         * content : 今天，去办了一件公务。
         * ispublished : 0
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
