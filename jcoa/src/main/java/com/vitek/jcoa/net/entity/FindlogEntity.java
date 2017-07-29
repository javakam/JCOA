package com.vitek.jcoa.net.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 查询日志
 * <p>
 * Created by javakam on 2017/5/21 0021.
 */
public class FindlogEntity extends BaseEntity  implements Serializable{
    @Override
    public String toString() {
        return "FindlogEntity{" +
                "models=" + models +
                ", logExtends=" + logExtends +
                '}';
    }

    /**
     * models : null
     * logExtends : [{"id":178,"date":1493654400000,"username":"李炳州","content":"1、起草申报国家住建系统先进集体事迹材料。2、准备2016年度财务审计工作。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":179,"date":1493740800000,"username":"赵琼","content":"上午将昨天审批的文件下发许队，魏队及各科室，今天收文一份，日常工作","ispublished":1,"workstatus":"单位办公","job":"科员","job_type":null,"powerid":1,"department":"行政主任"},{"id":181,"date":1493740800000,"username":"麻培","content":"开守法证明12份","ispublished":1,"workstatus":"单位办公","job":"科员","job_type":null,"powerid":1,"department":"行政主任"},{"id":182,"date":1493740800000,"username":"李炳州","content":"1、按照局通知，报送大队落实省长办公会纪要要求，管控市场情况报局办公室汇总上报。\n2、汇总落实省长办公会纪要管控市场情况，报权属处汇总上报。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":190,"date":1493827200000,"username":"赵琼","content":"收发文件，给两个科室要水，开守法证明，接局电话通知明天行风热线","ispublished":1,"workstatus":"单位办公","job":"科员","job_type":null,"powerid":1,"department":"行政主任"},{"id":198,"date":1493827200000,"username":"李炳州","content":"1、按照局通知要求上报政府近期中介典型案件。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":201,"date":1493913600000,"username":"weixiao","content":"今天，去办了一件公务。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"},{"id":243,"date":1494345600000,"username":"李炳州","content":"1、上午参加局维稳工作会。2、下午填报住建系统先进单位资料。3、准备局内部审计资料。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":244,"date":1494259200000,"username":"李炳州","content":"1、准备局内部审计资料。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":245,"date":1494172800000,"username":"李炳州","content":"1、准备局内部审计资料。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":273,"date":1494518400000,"username":"李炳州","content":"准备2016年度内部审计资料。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":275,"date":1494432000000,"username":"李炳州","content":"一参加局政协人大提案交办会。二协助草拟房地产开发遗留问题处理报告。三准备2016年财务审计工作资料。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":277,"date":1494518400000,"username":"赵琼","content":"开证明，收发文件，接发传真，接打电话，","ispublished":1,"workstatus":"单位办公","job":"科员","job_type":null,"powerid":1,"department":"行政主任"},{"id":296,"date":1494777600000,"username":"李炳州","content":"1、先进集体推荐报告审批程序旅行完毕。2、录入核对执法人员信息。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":326,"date":1495036800000,"username":"李炳州","content":"1、按照局执法事项清单逐条研究，提出初步修改意见。2、增加印制周六宣传资料。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":368,"date":1495382400000,"username":"李炳州","content":"1、报送一问责八清理自查表。2、处理日常工作。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":374,"date":1495123200000,"username":"李炳州","content":"1、协调周六宣传资料、宣传用桌子。2、报送执法事项修改建议。","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":null,"powerid":2,"department":"行政主任"},{"id":379,"date":1495468800000,"username":"weixiao","content":"今天天气不快点今天有放了三个月我操错不让明天的你会怎么样明天天气毕节天气更好","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"},{"id":400,"date":1495555200000,"username":"weixiao","content":"今天天气不错就明天天气更好些","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"},{"id":406,"date":1495555200000,"username":"weixiao","content":"泼墨","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"},{"id":409,"date":1495555200000,"username":"weixiao","content":"工作日志魔","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"},{"id":410,"date":1495555200000,"username":"weixiao","content":"工作日志","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"},{"id":415,"date":1495641600000,"username":"weixiao","content":"ceshi","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"},{"id":449,"date":1495728000000,"username":"weixiao","content":"嗯呢","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"},{"id":455,"date":1495814400000,"username":"weixiao","content":"工作日志今天天气不错","ispublished":1,"workstatus":"单位办公","job":"行政主任","job_type":"","powerid":2,"department":"行政主任"}]
     */

    private Object models;
    private List<LogExtendsBean> logExtends;

    public Object getModels() {
        return models;
    }

    public void setModels(Object models) {
        this.models = models;
    }

    public List<LogExtendsBean> getLogExtends() {
        return logExtends;
    }

    public void setLogExtends(List<LogExtendsBean> logExtends) {
        this.logExtends = logExtends;
    }

    public static class LogExtendsBean implements Serializable{
        @Override
        public String toString() {
            return "LogExtendsBean{" +
                    "id=" + id +
                    ", date=" + date +
                    ", username='" + username + '\'' +
                    ", content='" + content + '\'' +
                    ", ispublished=" + ispublished +
                    ", workstatus='" + workstatus + '\'' +
                    ", job='" + job + '\'' +
                    ", job_type=" + job_type +
                    ", powerid=" + powerid +
                    ", department='" + department + '\'' +
                    '}';
        }

        /**
         * id : 178
         * date : 1493654400000
         * username : 李炳州
         * content : 1、起草申报国家住建系统先进集体事迹材料。2、准备2016年度财务审计工作。
         * ispublished : 1
         * workstatus : 单位办公
         * job : 行政主任
         * job_type : null
         * powerid : 2
         * department : 行政主任
         */

        private int id;
        private long date;
        private String username;
        private String content;
        private int ispublished;
        private String workstatus;
        private String job;
        private Object job_type;
        private int powerid;
        private String department;

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

        public int getPowerid() {
            return powerid;
        }

        public void setPowerid(int powerid) {
            this.powerid = powerid;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }
}
