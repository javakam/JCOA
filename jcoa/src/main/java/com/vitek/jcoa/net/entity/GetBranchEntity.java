package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 获取回退及指派的提交值以及是否可以归档/jc_oa/flow/get_taskbranch（GET）
 * <p>
 * Created by javakam on 2017/6/2 0002.
 */
public class GetBranchEntity extends BaseEntity {
    @Override
    public String toString() {
        return "GetBranchEntity{" +
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

    public static class ModelsBean {
        @Override
        public String toString() {
            return "ModelsBean{" +
                    "the_file='" + the_file + '\'' +
                    ", rollBACK=" + rollBACK +
                    ", submit=" + submit +
                    '}';
        }

        /**
         * the_file : 0
         * rollBACK : []
         * submit : ["5","4"]
         */

        private String the_file;
        private List<?> rollBACK;
        private List<String> submit;

        public String getThe_file() {
            return the_file;
        }

        public void setThe_file(String the_file) {
            this.the_file = the_file;
        }

        public List<?> getRollBACK() {
            return rollBACK;
        }

        public void setRollBACK(List<?> rollBACK) {
            this.rollBACK = rollBACK;
        }

        public List<String> getSubmit() {
            return submit;
        }

        public void setSubmit(List<String> submit) {
            this.submit = submit;
        }
    }
}
