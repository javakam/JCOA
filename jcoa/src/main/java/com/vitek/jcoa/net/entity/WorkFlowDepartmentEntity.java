package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 查看公文可流转的下一部门/jc_oa/flow/Workflow_Department（GET）
 * <p>
 * Created by Administrator on 2017/6/2 0002.
 */

public class WorkFlowDepartmentEntity extends BaseEntity {

    private List<ModelsBean> models;

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean {
        /**
         * departmentid : 1
         * department : 大队长
         * powerid : 5
         */

        private int departmentid;
        private String department;
        private int powerid;

        public int getDepartmentid() {
            return departmentid;
        }

        public void setDepartmentid(int departmentid) {
            this.departmentid = departmentid;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getPowerid() {
            return powerid;
        }

        public void setPowerid(int powerid) {
            this.powerid = powerid;
        }
    }
}
