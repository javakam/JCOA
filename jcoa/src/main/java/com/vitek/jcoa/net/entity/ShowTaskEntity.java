package com.vitek.jcoa.net.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 查看公文
 * <p>
 * {"c_id": 55,
 * "number": "123",                    编号
 * "d_number": "234",                  文号
 * "s_number": "1234",                 序号
 * "source": "上级交办",                           公文来源  写死：上级交办  其他部门移交     同：待办公文中的 现审批部门
 * "source_time": 1494950400000,                  收到时间  2017-05-17
 * "d_source": "sdaf",                            来文机关
 * "title": "测试 测试 ",             公文主题    题目    ----   公文详情
 * "this_d": 1,                        正件
 * "enclosure": 1,                     附件
 * "ecpy_d": 2,                        复印件
 * "remarks": "测试 ",                 备注    ----   公文详情
 * "create_people": "weixiao",         创建者
 * "assignees": "weixiao",
 * "instid": 100050,
 * "d_usernames": "weixiao,张春晖,",
 * "the_file": 1,
 * "handle_type": "违法施工类",         事件类别   公文主题类别   ----   预售违法类 ； 违法施工类； 其他
 * "mytask": {
 * "id": "100054",
 * "name": "综合办公室",
 * "processInstanceId": "100050",
 * "assignee": "weixiao",
 * "processDefinitionId": "myLeaveProcess:1:4",
 * "executionId": "100050"}}
 * <p>
 * BUG  :
 * MytaskBean  也必须是想 Serializable 接口
 * <p>
 * Created by javakam on 2017/5/26 0026.
 */

public class ShowTaskEntity extends BaseEntity implements Serializable {
    @Override
    public String toString() {
        return "ShowTaskEntity{" +
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
                    "c_id=" + c_id +
                    ", number='" + number + '\'' +
                    ", d_number='" + d_number + '\'' +
                    ", s_number='" + s_number + '\'' +
                    ", source='" + source + '\'' +
                    ", source_time=" + source_time +
                    ", d_source='" + d_source + '\'' +
                    ", title='" + title + '\'' +
                    ", this_d=" + this_d +
                    ", enclosure=" + enclosure +
                    ", ecpy_d=" + ecpy_d +
                    ", remarks='" + remarks + '\'' +
                    ", create_people='" + create_people + '\'' +
                    ", assignees='" + assignees + '\'' +
                    ", instid=" + instid +
                    ", d_usernames=" + d_usernames +
                    ", the_file=" + the_file +
                    ", handle_type='" + handle_type + '\'' +
                    ", mytask=" + mytask +
                    '}';
        }

        /**
         * c_id : 0
         * number : 123
         * d_number : 123
         * s_number : 123
         * source : 上级交办
         * source_time : 1494950400000
         * d_source : 23
         * title : 特让他业务人员微软也
         * this_d : 2
         * enclosure : 2
         * ecpy_d : 2
         * remarks :
         * create_people : weixiao
         * assignees : weixiao
         * instid : 97533
         * d_usernames : null
         * the_file : 1
         * handle_type : 预售违法类
         * mytask : {"id":null,"name":"副大队长办公室","processInstanceId":"97533","assignee":"v_测试1","processDefinitionId":"myLeaveProcess:1:4","executionId":"100045"}
         */


        private int c_id;
        private String number;
        private String d_number;
        private String s_number;
        private String source;
        private long source_time;
        private String d_source;
        private String title;
        private int this_d;
        private int enclosure;
        private int ecpy_d;
        private String remarks;
        private String create_people;
        private String assignees;
        private int instid;
        private Object d_usernames;
        private int the_file;
        private String handle_type;
        private MytaskBean mytask;

        public int getC_id() {
            return c_id;
        }

        public void setC_id(int c_id) {
            this.c_id = c_id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getD_number() {
            return d_number;
        }

        public void setD_number(String d_number) {
            this.d_number = d_number;
        }

        public String getS_number() {
            return s_number;
        }

        public void setS_number(String s_number) {
            this.s_number = s_number;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public long getSource_time() {
            return source_time;
        }

        public void setSource_time(long source_time) {
            this.source_time = source_time;
        }

        public String getD_source() {
            return d_source;
        }

        public void setD_source(String d_source) {
            this.d_source = d_source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getThis_d() {
            return this_d;
        }

        public void setThis_d(int this_d) {
            this.this_d = this_d;
        }

        public int getEnclosure() {
            return enclosure;
        }

        public void setEnclosure(int enclosure) {
            this.enclosure = enclosure;
        }

        public int getEcpy_d() {
            return ecpy_d;
        }

        public void setEcpy_d(int ecpy_d) {
            this.ecpy_d = ecpy_d;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreate_people() {
            return create_people;
        }

        public void setCreate_people(String create_people) {
            this.create_people = create_people;
        }

        public String getAssignees() {
            return assignees;
        }

        public void setAssignees(String assignees) {
            this.assignees = assignees;
        }

        public int getInstid() {
            return instid;
        }

        public void setInstid(int instid) {
            this.instid = instid;
        }

        public Object getD_usernames() {
            return d_usernames;
        }

        public void setD_usernames(Object d_usernames) {
            this.d_usernames = d_usernames;
        }

        public int getThe_file() {
            return the_file;
        }

        public void setThe_file(int the_file) {
            this.the_file = the_file;
        }

        public String getHandle_type() {
            return handle_type;
        }

        public void setHandle_type(String handle_type) {
            this.handle_type = handle_type;
        }

        public MytaskBean getMytask() {
            return mytask;
        }

        public void setMytask(MytaskBean mytask) {
            this.mytask = mytask;
        }

        public static class MytaskBean implements Serializable {
            @Override
            public String toString() {
                return "MytaskBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", processInstanceId='" + processInstanceId + '\'' +
                        ", assignee='" + assignee + '\'' +
                        ", processDefinitionId='" + processDefinitionId + '\'' +
                        ", executionId='" + executionId + '\'' +
                        '}';
            }

            /**
             * id : null
             * name : 副大队长办公室
             * processInstanceId : 97533
             * assignee : v_测试1
             * processDefinitionId : myLeaveProcess:1:4
             * executionId : 100045
             */

            private Object id;
            private String name;
            private String processInstanceId;
            private String assignee;
            private String processDefinitionId;
            private String executionId;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProcessInstanceId() {
                return processInstanceId;
            }

            public void setProcessInstanceId(String processInstanceId) {
                this.processInstanceId = processInstanceId;
            }

            public String getAssignee() {
                return assignee;
            }

            public void setAssignee(String assignee) {
                this.assignee = assignee;
            }

            public String getProcessDefinitionId() {
                return processDefinitionId;
            }

            public void setProcessDefinitionId(String processDefinitionId) {
                this.processDefinitionId = processDefinitionId;
            }

            public String getExecutionId() {
                return executionId;
            }

            public void setExecutionId(String executionId) {
                this.executionId = executionId;
            }
        }
    }
}
