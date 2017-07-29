package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 部门查询
 * <p>
 * 参数名称	    参数类型	            参数说明
 * powerid	    职位的powerid	    可为空，也可不传空（不带参返所有部门）
 * <p>
 * <p>
 * "models": [
 * {"departmentid": 1,"department": "大队长","powerid": 5},
 * {"departmentid": 2,"department": "副队长","powerid": 3},
 * {"departmentid": 3,"department": "稽查管理科","powerid": 2},
 * {"departmentid": 4,"department": "稽查督查科","powerid": 2},
 * {"departmentid": 5,"department": "建筑市场稽查科","powerid": 2},
 * {"departmentid": 7,"department": "房地产市场稽查科","powerid": 2},
 * {"departmentid": 8,"department": "行政主任","powerid": 9}]
 * <p>
 * Created by javakam on 2017/5/18 0018.
 */
public class DepartmentEntity extends BaseEntity {
    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "leve_department=" + leve_department +
                ", models=" + models +
                '}';
    }

    private List<LeveDepartmentBean> leve_department;
    private List<ModelsBean> models;

    public List<LeveDepartmentBean> getLeve_department() {
        return leve_department;
    }

    public void setLeve_department(List<LeveDepartmentBean> leve_department) {
        this.leve_department = leve_department;
    }

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class LeveDepartmentBean {
        @Override
        public String toString() {
            return "LeveDepartmentBean{" +
                    "departmentid=" + departmentid +
                    ", department='" + department + '\'' +
                    ", powerid=" + powerid +
                    '}';
        }

        /**
         * departmentid : 2
         * department : 副队长
         * powerid : 3
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

    public static class ModelsBean {
        public ModelsBean() {
        }

        public ModelsBean(int departmentid, String department, int powerid) {
            this.departmentid = departmentid;
            this.department = department;
            this.powerid = powerid;
        }

        @Override
        public String toString() {
            return "ModelsBean{" +
                    "departmentid=" + departmentid +
                    ", department='" + department + '\'' +
                    ", powerid=" + powerid +
                    '}';
        }

        /**
         * departmentid : 3
         * department : 稽查管理科
         * powerid : 2
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
