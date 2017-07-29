package com.vitek.jcoa.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vitek.jcoa.net.entity.DepartmentEntity;
import com.vitek.jcoa.net.entity.FindDepartmentUserEntity;
import com.vitek.jcoa.net.entity.JobEntity;
import com.vitek.jcoa.net.entity.MessageEntity;
import com.vitek.jcoa.net.entity.UserInfoEntity;
import com.vitek.jcoa.util.VLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javakam on 2016/8/17.
 */
public class DBOperation {

    private static DBOperation instance = null;
    private static final String CSYNSIGN = "syn";// 同步标记
    private static final String MESSAGE = "message";//


    private DBOperation() {// 将构造方法私有
        // this.context = context;
    }

    public static DBOperation getInstance() {// 实例化引用
        if (instance == null) {
            instance = new DBOperation();
        }
        return instance;
    }

    /**
     * 数据库实例；
     */
    private SQLiteDatabase mdb;

    /**
     * 打开数据库
     */
    private void readDB() {
        if (mdb == null || !mdb.isOpen()) {
            mdb = DBHelper.getInstance().readDatabase();
        }
    }

    private void writeDB() {
        if (mdb == null || !mdb.isOpen()) {
            mdb = DBHelper.getInstance().writeDatabase();
        }
    }

    /**
     * 判断数据库文件（quiz.db）是否存在
     */
    public boolean isDBFileExist() {
        return DBHelper.getInstance().isDBFileExist();
    }


    /**
     * 关闭数据库
     */
    private void closeDB() {
        if (mdb != null || mdb.isOpen()) {
            DBHelper.getInstance().close();
        }
    }

    private String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }

    //----------------===================数据库操作部分==================----------------/

    /**
     * 插入一个用户信息
     *
     * @param bean
     */
    public void insertUserInfo(UserInfoEntity.UserExtendsBean bean) {
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
        synchronized (CSYNSIGN) {
            writeDB();
            ContentValues values = new ContentValues();
            values.put("id", bean.getId() + "");
            values.put("username", bean.getUsername());
            values.put("password", bean.getPassword());
            values.put("realname", bean.getRealname());
            values.put("departmentid", bean.getDepartmentid());
            values.put("job", bean.getJob());
            values.put("job_type", bean.getJob_type());
            values.put("phone", bean.getPhone() + "");
            values.put("rtime", bean.getRtime());
            values.put("department", bean.getDepartment());
            values.put("role_name", bean.getRole_name() + "");
            values.put("powerid", bean.getPowerid());
            values.put("departments", bean.getDepartments() + "");
            values.put("date", bean.getDate() + "");
            values.put("pdepartment", bean.getPdepartment() + "");
            values.put("actiontime", bean.getActiontime() + "");
            values.put("endtime", bean.getEndtime() + "");
            values.put("lusername", bean.getLusername() + "");
            values.put("log_actiontime", bean.getLog_actiontime() + "");
            values.put("log_endtime", bean.getLog_endtime() + "");
//            VLogUtil.e("caocaooca===" + messageBean.toString());
            String sql = "select * from " + DBHelper.DB_USERINFO + " where id = ? ";
            Cursor cursor = mdb.rawQuery(sql, new String[]{bean.getId() + ""});
            if (cursor != null && cursor.getCount() > 0) {
                mdb.update(DBHelper.DB_USERINFO, values, " id = ? "
                        , new String[]{bean.getId() + ""});
            } else {
                mdb.insert(DBHelper.DB_USERINFO, null, values);
            }
            closeCursor(cursor);
        }
    }

    /**
     * 插入用户信息（集合）
     *
     * @param jopEntityList
     */
//    public void insertUserInfoList(List<UserInfoEntity.UserExtendsBean> userExtendsBeen) {
//        synchronized (CSYNSIGN) {
//            writeDB();
//            Cursor cursor = null;
//            for (UserInfoEntity.UserExtendsBean modelsBean : userExtendsBeen) {
//                ContentValues values = new ContentValues();
//                values.put("id", modelsBean.getId());
//                values.put("role_name", modelsBean.getRole_name());
//                values.put("powerid", modelsBean.getPowerid());
//                String sql = "select * from " + DBHelper.DB_JOB + " where id = ? ";
//                cursor = mdb.rawQuery(sql, new String[]{modelsBean.getId() + ""});
////                VLogUtil.e(modelsBean.getId() + "------" + modelsBean.getRole_name());
//                if (cursor != null && cursor.getCount() > 0) {
//                    mdb.update(DBHelper.DB_JOB, values, " id = ? "
//                            , new String[]{modelsBean.getId() + ""});
//                } else {
//                    mdb.insert(DBHelper.DB_JOB, null, values);
//                }
//            }
//            closeCursor(cursor);
//        }
//    }

    /**
     * 插入部门（集合）
     *
     * @param modelsBeanList
     */
    public void insertDeparmentList(List<DepartmentEntity.ModelsBean> modelsBeanList) {
        synchronized (CSYNSIGN) {
            writeDB();
            Cursor cursor = null;
            for (DepartmentEntity.ModelsBean modelsBean : modelsBeanList) {
                ContentValues values = new ContentValues();
                values.put("departmentid", modelsBean.getDepartmentid());
                values.put("department", modelsBean.getDepartment());
                values.put("powerid", modelsBean.getPowerid());
                String sql = "select * from " + DBHelper.DB_DEPARTMENT + " where departmentid = ? ";
                cursor = mdb.rawQuery(sql, new String[]{modelsBean.getDepartmentid() + ""});
//                VLogUtil.e(modelsBean.getDepartmentid() + "------" + modelsBean.getDepartment());
                if (cursor != null && cursor.getCount() > 0) {
                    mdb.update(DBHelper.DB_DEPARTMENT, values, " departmentid = ? "
                            , new String[]{modelsBean.getDepartmentid() + ""});
                } else {
                    mdb.insert(DBHelper.DB_DEPARTMENT, null, values);
                }
            }
            closeCursor(cursor);
        }
    }

    /**
     * 获取所有部门
     *
     * @return
     */
    public List<DepartmentEntity.ModelsBean> getDepartmentList() {
        readDB();
        List<DepartmentEntity.ModelsBean> modelsBeanList = new ArrayList<>();
        String sql = "select * from " + DBHelper.DB_DEPARTMENT;
        Cursor cursor = mdb.rawQuery(sql, null);
        DepartmentEntity.ModelsBean modelsBean = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                modelsBean = new DepartmentEntity.ModelsBean();
                modelsBean.setDepartmentid(getInt(cursor, "departmentid"));
                modelsBean.setDepartment(getString(cursor, "department"));
                modelsBean.setPowerid(getInt(cursor, "powerid"));
                modelsBeanList.add(modelsBean);
            } while (cursor.moveToNext());
        }
        closeCursor(cursor);
//        VLogUtil.e(modelsBeanList.toString() + "***"+modelsBeanList.size());
        return modelsBeanList;
    }

    /**
     * 插入职务（集合）
     *
     * @param jopEntityList
     */
    public void insertJobList(List<JobEntity.ModelsBean> jopEntityList) {
        synchronized (CSYNSIGN) {
            writeDB();
            Cursor cursor = null;
            for (JobEntity.ModelsBean modelsBean : jopEntityList) {
                ContentValues values = new ContentValues();
                values.put("id", modelsBean.getId());
                values.put("role_name", modelsBean.getRole_name());
                values.put("powerid", modelsBean.getPowerid());
                String sql = "select * from " + DBHelper.DB_JOB + " where id = ? ";
                cursor = mdb.rawQuery(sql, new String[]{modelsBean.getId() + ""});
//                VLogUtil.e(modelsBean.getId() + "------" + modelsBean.getRole_name());
                if (cursor != null && cursor.getCount() > 0) {
                    mdb.update(DBHelper.DB_JOB, values, " id = ? "
                            , new String[]{modelsBean.getId() + ""});
                } else {
                    mdb.insert(DBHelper.DB_JOB, null, values);
                }
            }
            closeCursor(cursor);
        }
    }

    /**
     * 获取所有职务
     *
     * @return
     */
    public List<JobEntity.ModelsBean> getJobList() {
        readDB();
        List<JobEntity.ModelsBean> modelsBeanList = new ArrayList<>();
        String sql = "select * from " + DBHelper.DB_JOB;
        Cursor cursor = mdb.rawQuery(sql, null);
        JobEntity.ModelsBean modelsBean = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                modelsBean = new JobEntity.ModelsBean();
                modelsBean.setId(getInt(cursor, "id"));
                modelsBean.setRole_name(getString(cursor, "role_name"));
                modelsBean.setPowerid(getInt(cursor, "powerid"));
                modelsBeanList.add(modelsBean);
            } while (cursor.moveToNext());
        }
        closeCursor(cursor);
//        VLogUtil.e(modelsBeanList.toString() + "***"+modelsBeanList.size());
        return modelsBeanList;
    }

    /**
     * 插入某一部门的所有用户（集合）
     * <p>
     * API:查询某一部门的所有用户/jc_oa/find_department_user (POST))
     *
     * @param modelsBeanList
     */
    public void insertDeptUserListByDeptId(List<FindDepartmentUserEntity.ModelsBean> modelsBeanList) {
        synchronized (CSYNSIGN) {
            writeDB();
            Cursor cursor = null;
            for (FindDepartmentUserEntity.ModelsBean modelsBean : modelsBeanList) {
                ContentValues values = new ContentValues();
                values.put("id", modelsBean.getDepartmentid());
                values.put("username", modelsBean.getUsername());
                values.put("password", modelsBean.getPassword());
                values.put("realname", modelsBean.getRealname());
                values.put("departmentid", modelsBean.getDepartmentid());
                values.put("job", modelsBean.getJob());
                values.put("job_type", modelsBean.getJob_type() + "");
                values.put("phone", modelsBean.getPhone());
                values.put("rtime", modelsBean.getRtime() + "");
                values.put("department", modelsBean.getDepartment() + "");
                values.put("role_name", modelsBean.getRole_name() + "");
                values.put("powerid", modelsBean.getPowerid() + "");
                values.put("departments", modelsBean.getDepartments() + "");
                values.put("date", modelsBean.getDate() + "");
                values.put("pdepartment", modelsBean.getPdepartment() + "");
                values.put("actiontime", modelsBean.getActiontime() + "");
                values.put("endtime", modelsBean.getEndtime() + "");
                values.put("lusername", modelsBean.getLusername() + "");
                values.put("log_actiontime", modelsBean.getLog_actiontime() + "");
                values.put("log_endtime", modelsBean.getLog_endtime() + "");
                String sql = "select * from " + DBHelper.DB_DEPARTMENTID + " where departmentid = ? ";
                cursor = mdb.rawQuery(sql, new String[]{modelsBean.getDepartmentid() + ""});
//                VLogUtil.e(modelsBean.getDepartmentid() + "------" + modelsBean.getDepartment());
                if (cursor != null && cursor.getCount() > 0) {
                    mdb.update(DBHelper.DB_DEPARTMENTID, values, " departmentid = ? "
                            , new String[]{modelsBean.getDepartmentid() + ""});
                } else {
                    mdb.insert(DBHelper.DB_DEPARTMENTID, null, values);
                }
            }
            closeCursor(cursor);
        }
    }

    /**
     * 获取某一部门的所有用户（集合）
     *
     * @param departmentid
     * @return
     */
    public List<FindDepartmentUserEntity.ModelsBean> getDeptUserListByDeptId(String departmentid) {
        readDB();
        List<FindDepartmentUserEntity.ModelsBean> modelsBeanList = new ArrayList<>();
        String sql = "select * from " + DBHelper.DB_DEPARTMENTID + " where departmentid = ? ";
        Cursor cursor = mdb.rawQuery(sql, new String[]{departmentid});
        FindDepartmentUserEntity.ModelsBean modelsBean = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                modelsBean = new FindDepartmentUserEntity.ModelsBean();
                modelsBean.setId(getInt(cursor, "id"));
                modelsBean.setUsername(getString(cursor, "username"));
                modelsBean.setPassword(getString(cursor, "password"));
                modelsBean.setRealname(getString(cursor, "realname"));
                modelsBean.setDepartmentid(getString(cursor, "departmentid"));
                modelsBean.setJob(getString(cursor, "job"));
                modelsBean.setJob_type(getString(cursor, "job_type"));
                modelsBean.setPhone(getString(cursor, "phone"));
                modelsBean.setRtime(Long.valueOf(getString(cursor, "rtime")));
                modelsBean.setDepartment(getString(cursor, "department"));
                modelsBean.setRole_name(getString(cursor, "role_name"));
                modelsBean.setPowerid(getString(cursor, "powerid"));
                modelsBean.setDepartments(getString(cursor, "departments"));
                modelsBean.setDate(getString(cursor, "date"));
                modelsBean.setPdepartment(getString(cursor, "pdepartment"));
                modelsBean.setActiontime(getString(cursor, "actiontime"));
                modelsBean.setEndtime(getString(cursor, "endtime"));
                modelsBean.setLusername(getString(cursor, "lusername"));
                modelsBean.setLog_actiontime(getString(cursor, "log_actiontime"));
                modelsBean.setLog_endtime(getString(cursor, "log_endtime"));
                modelsBeanList.add(modelsBean);
            } while (cursor.moveToNext());
        }
        closeCursor(cursor);
//        VLogUtil.e(modelsBeanList.toString() + "***"+modelsBeanList.size());
        return modelsBeanList;
    }
//----------------======================================----------------------//

    /**
     * 插入一个消息
     *
     * @param messageBean
     */
    public void insertNotice(MessageEntity.MessageBean messageBean) {
        synchronized (CSYNSIGN) {
            writeDB();
            ContentValues values = new ContentValues();
            values.put("id", messageBean.getId());
            values.put("uid", messageBean.getUid());
            values.put("text", messageBean.getText());
            values.put("time", messageBean.getTime());
            values.put("url", messageBean.getUrl());
            values.put("type", messageBean.getType());
            VLogUtil.e("caocaooca===" + messageBean.toString());
            String sql = "select * from " + MESSAGE + " where id = ? and uid = ? ";
            Cursor cursor = mdb.rawQuery(sql, new String[]{messageBean.getId(), messageBean.getUid()});
            if (cursor != null && cursor.getCount() > 0) {
                mdb.update(MESSAGE, values, " id = ? and uid = ? ", new String[]{messageBean.getId(), messageBean.getUid()});
            } else {
                mdb.insert(MESSAGE, null, values);
            }
            closeCursor(cursor);
        }
    }


    /**
     * 获取单个消息
     *
     * @param id 消息id
     * @return
     */
    public MessageEntity.MessageBean getNoticeById(String id) {
        readDB();
        MessageEntity.MessageBean messageBean = null;
        String sql = "select * from " + MESSAGE + " where id = ? ";
        Cursor cursor = mdb.rawQuery(sql, new String[]{id});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            messageBean = new MessageEntity.MessageBean();
            messageBean.setId(getString(cursor, "id"));
            messageBean.setUid(getString(cursor, "uid"));
            messageBean.setText(getString(cursor, "text"));
            messageBean.setTime(getString(cursor, "time"));
            messageBean.setUrl(getString(cursor, "url"));
            messageBean.setType(getString(cursor, "type"));
        }
        closeCursor(cursor);
        return messageBean;
    }

    /**
     * 获取所有消息
     *
     * @param uid 用户id
     * @return
     */
    public List<MessageEntity.MessageBean> getNotices(String uid) {
        readDB();
        List<MessageEntity.MessageBean> notices = new ArrayList<>();
        String sql = "select * from " + MESSAGE + " where uid = ? ";// order by time desc
        Cursor cursor = mdb.rawQuery(sql, new String[]{uid});
        MessageEntity.MessageBean messageBean = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                messageBean = new MessageEntity.MessageBean();
                messageBean.setId(getString(cursor, "id"));
                messageBean.setUid(getString(cursor, "uid"));
                messageBean.setText(getString(cursor, "text"));
                messageBean.setTime(getString(cursor, "time"));
                messageBean.setUrl(getString(cursor, "url"));
                messageBean.setType(getString(cursor, "type"));
                notices.add(messageBean);
            } while (cursor.moveToNext());
        }
        closeCursor(cursor);
        VLogUtil.e(notices.toString() + "***");
        return notices;
    }

    /**
     * 删除消息
     *
     * @param id
     */
    public void deleteNoticeById(String id) {
        String sql = "delete from " + MESSAGE + " where id = ? ";
        mdb.execSQL(sql, new String[]{id});
    }
}