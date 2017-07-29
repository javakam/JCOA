package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 获取用户消息(只可查询一次一次后就删除 需手机保留)<p>
 * uid	用户id
 * 发送类型	GET<p>
 * 返回值<p>
 * code	错误代码
 * 1000	成功
 * 1001	没有数据<p>
 * data	{
 * "msg": "领取成功",<p>
 * "code": "1000",<p>
 * "data": [<p>
 * {<p>
 * "id": "222",<p>
 * "uid": "30",<p>
 * "text": "您在话题我是话题内容上班还是手机上获得了480奖励",<p>
 * "time": "1470120276",<p>
 * "url": "0",<p>
 * "type": "4"<p>
 * }<p>
 * ]<p>
 * }<p>
 * type	通知类型 1发奖通知 2出题奖励通知 3投注无效通知 4奖励到账 5粉丝 6公告<p>
 * Created by javakam on 2016/8/17.
 */
public class MessageEntity {
    //test
    public MessageEntity(String msg, String code, List<MessageBean> data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    /**
     * msg : 获取成功
     * code : 1000
     * data : [{"id":"668","uid":"36","text":"话题本身就是加我加我,被1234裁定为2016-08-16 15:20发生，正方获胜。12小时内如果无人投诉您将获得相应的奖励","time":"1471332047","url":"0","type":"1"},{"id":"664","uid":"36","text":"话题v个不好好,被1234裁定为2016-08-16 15:20发生，正方获胜。12小时内如果无人投诉您将获得相应的奖励","time":"1471332033","url":"0","type":"1"},{"id":"683","uid":"36","text":"话题v个不好好，被裁定为2016-08-16 15:20发生正方获胜。您获得191金币奖励已经到账","time":"1471375228","url":"0","type":"1"},{"id":"689","uid":"36","text":"话题本身就是加我加我，被裁定为2016-08-16 15:20发生正方获胜。您获得8862金币奖励已经到账","time":"1471375242","url":"0","type":"1"}]
     */

    private String msg;
    private String code;
    /**
     * id : 668
     * uid : 36
     * text : 话题本身就是加我加我,被1234裁定为2016-08-16 15:20发生，正方获胜。12小时内如果无人投诉您将获得相应的奖励
     * time : 1471332047
     * url : 0
     * type : 1
     */

    private List<MessageBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MessageBean> getData() {
        return data;
    }

    public void setData(List<MessageBean> data) {
        this.data = data;
    }

    public static class MessageBean {
        private String id;
        private String uid;
        private String text;
        private String time;
        private String url;
        private String type;

        public MessageBean() {
        }

        //test
        public MessageBean(String id, String uid, String text, String time, String url, String type) {
            this.id = id;
            this.uid = uid;
            this.text = text;
            this.time = time;
            this.url = url;
            this.type = type;
        }

        @Override
        public String toString() {
            return "MessageBean{" +
                    "id='" + id + '\'' +
                    ", uid='" + uid + '\'' +
                    ", text='" + text + '\'' +
                    ", time='" + time + '\'' +
                    ", url='" + url + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
