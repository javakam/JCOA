package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 查询用户信息
 * <p>
 * Created by javakam on 2017/5/18 0018.
 */
public class UserInfoEntity extends BaseEntity {
    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "users=" + users +
                ", userExtends=" + userExtends +
                '}';
    }

    /**
     * users : null
     * userExtends : [{"id":120,
     * "username":"weixiao",
     * "password":"password",
     * "realname":"wei",
     * "departmentid":"8",
     * "job":"行政主任",
     * "job_type":"",
     * "phone":null,
     * "rtime":1493887395000,
     * "department":"行政主任",
     * "role_name":null,
     * "powerid":"2",
     * "departments":null,
     * "date":null,
     * "pdepartment":null,
     * "actiontime":null,
     * "endtime":null,
     * "lusername":null,
     * "log_actiontime":null,
     * "log_endtime":null}]
     */

    private Object users;
    private List<UserExtendsBean> userExtends;

    public Object getUsers() {
        return users;
    }

    public void setUsers(Object users) {
        this.users = users;
    }

    public List<UserExtendsBean> getUserExtends() {
        return userExtends;
    }

    public void setUserExtends(List<UserExtendsBean> userExtends) {
        this.userExtends = userExtends;
    }

    public static class UserExtendsBean {
        @Override
        public String toString() {
            return "UserExtendsBean{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", realname='" + realname + '\'' +
                    ", departmentid='" + departmentid + '\'' +
                    ", job='" + job + '\'' +
                    ", job_type='" + job_type + '\'' +
                    ", phone=" + phone +
                    ", rtime=" + rtime +
                    ", department='" + department + '\'' +
                    ", role_name=" + role_name +
                    ", powerid='" + powerid + '\'' +
                    ", departments=" + departments +
                    ", date=" + date +
                    ", pdepartment=" + pdepartment +
                    ", actiontime=" + actiontime +
                    ", endtime=" + endtime +
                    ", lusername=" + lusername +
                    ", log_actiontime=" + log_actiontime +
                    ", log_endtime=" + log_endtime +
                    '}';
        }

        /**
         * id : 120
         * username : weixiao
         * password : password
         * realname : wei
         * departmentid : 8
         * job : 行政主任
         * job_type :
         * phone : null
         * rtime : 1493887395000
         * department : 行政主任
         * role_name : null
         * powerid : 2
         * departments : null
         * date : null
         * pdepartment : null
         * actiontime : null
         * endtime : null
         * lusername : null
         * log_actiontime : null
         * log_endtime : null
         *
         */
        //eg 2 :
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
        private String job_type;
        private Object phone;
        private long rtime;
        private String department;
        private Object role_name;
        private String powerid;
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

        public String getJob_type() {
            return job_type;
        }

        public void setJob_type(String job_type) {
            this.job_type = job_type;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public long getRtime() {
            return rtime;
        }

        public void setRtime(long rtime) {
            this.rtime = rtime;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public Object getRole_name() {
            return role_name;
        }

        public void setRole_name(Object role_name) {
            this.role_name = role_name;
        }

        public String getPowerid() {
            return powerid;
        }

        public void setPowerid(String powerid) {
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
