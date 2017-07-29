package com.vitek.jcoa.net.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 查询某一部门的所有用户/jc_oa/find_department_user (POST))
 * <p>
 * Created by javakam on 2017/5/22 0022.
 */
public class FindDepartmentUserEntity extends BaseEntity implements Serializable {
    @Override
    public String toString() {
        return "FindDepartmentUserEntity{" +
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
            return  "姓名：" + username + "\r\n" +
                    " 真实姓名：" + realname + "\r\n" +
                    " 职务：" + job + "\r\n" +
                    " 电话：" + phone + "\r\n" +
                    " 时间：" + rtime + "\r\n" +
                    " 部门：" + department + "\r\n"
//                    "  actiontime=" + actiontime +
//                    ", endtime=" + endtime +
//                    " 发布时间：" + log_actiontime +
//                    " 结束时间：" + log_endtime +
                    ;
        }

        /**
         * id : 94
         * username : 李炳州
         * password : 730619
         * realname : 李炳州
         * departmentid : 8
         * job : 行政主任
         * job_type : null
         * phone : 13230267665
         * rtime : null
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
