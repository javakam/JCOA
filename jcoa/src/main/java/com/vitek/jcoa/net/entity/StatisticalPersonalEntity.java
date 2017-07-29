package com.vitek.jcoa.net.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 查询权限下用户的工作动态
 * <p>
 * Created by javakam on 2017/5/22 0022.
 */
public class StatisticalPersonalEntity extends BaseEntity implements Serializable {
    @Override
    public String toString() {
        return "StatisticalPersonalEntity{" +
                "dstateModel=" + dstateModel +
                '}';
    }

    /**
     * dstateModel : {"d_workday":1,"d_not_workday":0,
     * "stateModels":
     * [{"username":"weixiao",
     * "work_day":1,
     * "notwork_day":0,
     * "shijia":0,
     * "bingjia":0,
     * "waichu":0,
     * "gongshang":0,
     * "danweibangong":1,
     * "teshuxiujia":0,
     * "waidiao":0}]}
     */

    private DstateModelBean dstateModel;

    public DstateModelBean getDstateModel() {
        return dstateModel;
    }

    public void setDstateModel(DstateModelBean dstateModel) {
        this.dstateModel = dstateModel;
    }

    public static class DstateModelBean {
        @Override
        public String toString() {
            return "DstateModelBean{" +
                    "d_workday=" + d_workday +
                    ", d_not_workday=" + d_not_workday +
                    ", stateModels=" + stateModels +
                    '}';
        }

        /**
         * d_workday : 1
         * d_not_workday : 0
         * stateModels : [{"username":"weixiao","work_day":1,"notwork_day":0,"shijia":0,"bingjia":0,"waichu":0,"gongshang":0,"danweibangong":1,"teshuxiujia":0,"waidiao":0}]
         */

        private int d_workday;
        private int d_not_workday;
        private List<StateModelsBean> stateModels;

        public int getD_workday() {
            return d_workday;
        }

        public void setD_workday(int d_workday) {
            this.d_workday = d_workday;
        }

        public int getD_not_workday() {
            return d_not_workday;
        }

        public void setD_not_workday(int d_not_workday) {
            this.d_not_workday = d_not_workday;
        }

        public List<StateModelsBean> getStateModels() {
            return stateModels;
        }

        public void setStateModels(List<StateModelsBean> stateModels) {
            this.stateModels = stateModels;
        }

        public static class StateModelsBean implements Serializable {
            @Override
            public String toString() {
                return "姓名：" + username + "\r\n" +
                        " 在岗（天）：" + work_day + "\r\n" +
                        " 不在岗（天）" + notwork_day + "\r\n" +
                        " 事假：" + shijia + "\r\n" +
                        " 病假：" + bingjia + "\r\n" +
                        " 外出：" + waichu + "\r\n" +
                        " 工伤：" + gongshang + "\r\n" +
                        " 单位办公：" + danweibangong + "\r\n" +
                        " 特殊休假：" + teshuxiujia + "\r\n" +
                        " 外调：" + waidiao + "\r\n";
            }

            /**
             * username : weixiao
             * work_day : 1
             * notwork_day : 0
             * shijia : 0
             * bingjia : 0
             * waichu : 0
             * gongshang : 0
             * danweibangong : 1
             * teshuxiujia : 0
             * waidiao : 0
             */

            private String username;
            private int work_day;
            private int notwork_day;
            private int shijia;
            private int bingjia;
            private int waichu;
            private int gongshang;
            private int danweibangong;
            private int teshuxiujia;
            private int waidiao;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public int getWork_day() {
                return work_day;
            }

            public void setWork_day(int work_day) {
                this.work_day = work_day;
            }

            public int getNotwork_day() {
                return notwork_day;
            }

            public void setNotwork_day(int notwork_day) {
                this.notwork_day = notwork_day;
            }

            public int getShijia() {
                return shijia;
            }

            public void setShijia(int shijia) {
                this.shijia = shijia;
            }

            public int getBingjia() {
                return bingjia;
            }

            public void setBingjia(int bingjia) {
                this.bingjia = bingjia;
            }

            public int getWaichu() {
                return waichu;
            }

            public void setWaichu(int waichu) {
                this.waichu = waichu;
            }

            public int getGongshang() {
                return gongshang;
            }

            public void setGongshang(int gongshang) {
                this.gongshang = gongshang;
            }

            public int getDanweibangong() {
                return danweibangong;
            }

            public void setDanweibangong(int danweibangong) {
                this.danweibangong = danweibangong;
            }

            public int getTeshuxiujia() {
                return teshuxiujia;
            }

            public void setTeshuxiujia(int teshuxiujia) {
                this.teshuxiujia = teshuxiujia;
            }

            public int getWaidiao() {
                return waidiao;
            }

            public void setWaidiao(int waidiao) {
                this.waidiao = waidiao;
            }
        }
    }
}
