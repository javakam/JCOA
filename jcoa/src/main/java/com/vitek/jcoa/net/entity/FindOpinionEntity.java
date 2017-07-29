package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 查看意见
 * <p>
 * Created by javakam on 2017/6/9 0009.
 */
public class FindOpinionEntity extends BaseEntity {

    private List<ModelsBean> models;

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean {
        /**
         * id : 45
         * c_id : 122
         * opinion : aaa
         * o_name : 行政主任行政主任wei
         * o_type : 处理意见
         */

        private int id;
        private String c_id;
        private String opinion;
        private String o_name;
        private String o_type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getOpinion() {
            return opinion;
        }

        public void setOpinion(String opinion) {
            this.opinion = opinion;
        }

        public String getO_name() {
            return o_name;
        }

        public void setO_name(String o_name) {
            this.o_name = o_name;
        }

        public String getO_type() {
            return o_type;
        }

        public void setO_type(String o_type) {
            this.o_type = o_type;
        }
    }
}
