package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 获得某个部门可以接受公文的人员
 * <p>
 * Created by javakam on 2017/6/2 0002.
 */

public class FindWorkflowUserdEntity extends BaseEntity {

    private List<ModelsBean> models;

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean {
        /**
         * id : 115
         * username : 魏洪利
         * password : whl123
         * realname : 魏洪利
         * departmentid : 2,3,5
         * job : 副大队长
         * job_type : null
         * phone : 13903228510
         * rtime : 1493775457000
         * department : null
         * role_name : null
         * powerid : null
         * departments : null
         * date : null
         * pdepartment : null
         * actiontime : null
         * endtime : null
         * lusername : null
         * log_actiontime : null
         * log_endtime : null
         */

        private int id;
        private String username;
        private String password;
        private String realname;
        private String departmentid;
        private String job;
        private Object job_type;
        private String phone;
        private long rtime;
        private Object department;
        private Object role_name;
        private Object powerid;
        private Object departments;
        private Object date;
        private Object pdepartment;
        private Object actiontime;
        private Object endtime;
        private Object lusername;
        private Object log_actiontime;
        private Object log_endtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getDepartmentid() {
            return departmentid;
        }

        public void setDepartmentid(String departmentid) {
            this.departmentid = departmentid;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public Object getJob_type() {
            return job_type;
        }

        public void setJob_type(Object job_type) {
            this.job_type = job_type;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getRtime() {
            return rtime;
        }

        public void setRtime(long rtime) {
            this.rtime = rtime;
        }

        public Object getDepartment() {
            return department;
        }

        public void setDepartment(Object department) {
            this.department = department;
        }

        public Object getRole_name() {
            return role_name;
        }

        public void setRole_name(Object role_name) {
            this.role_name = role_name;
        }

        public Object getPowerid() {
            return powerid;
        }

        public void setPowerid(Object powerid) {
            this.powerid = powerid;
        }

        public Object getDepartments() {
            return departments;
        }

        public void setDepartments(Object departments) {
            this.departments = departments;
        }

        public Object getDate() {
            return date;
        }

        public void setDate(Object date) {
            this.date = date;
        }

        public Object getPdepartment() {
            return pdepartment;
        }

        public void setPdepartment(Object pdepartment) {
            this.pdepartment = pdepartment;
        }

        public Object getActiontime() {
            return actiontime;
        }

        public void setActiontime(Object actiontime) {
            this.actiontime = actiontime;
        }

        public Object getEndtime() {
            return endtime;
        }

        public void setEndtime(Object endtime) {
            this.endtime = endtime;
        }

        public Object getLusername() {
            return lusername;
        }

        public void setLusername(Object lusername) {
            this.lusername = lusername;
        }

        public Object getLog_actiontime() {
            return log_actiontime;
        }

        public void setLog_actiontime(Object log_actiontime) {
            this.log_actiontime = log_actiontime;
        }

        public Object getLog_endtime() {
            return log_endtime;
        }

        public void setLog_endtime(Object log_endtime) {
            this.log_endtime = log_endtime;
        }
    }
}
