package com.vitek.jcoa.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * 创建数据库<p>
 * Created by javakam on 2017/5/16.
 */
public class DBHelper extends CreateDBToSDCardSQLiteOpenHelper {

    private static final String DATABASE_PATH = File.separator + "Vitek" + File.separator + "Jcoa";
    private static final String DATABASE_NAME = "jcoa.db";
    private static final int DATABASE_VERSION = 1;

    public static final String DB_USERINFO = "userinfo";
    public static final String DB_DEPARTMENT = "department";
    public static final String DB_JOB = "job";
    public static final String DB_DEPARTMENTID = "departmentid";


    private static DBHelper mInstance = null;
    private SQLiteDatabase db = null;

    public DBHelper() {
        super(DATABASE_PATH, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance() {
        if (mInstance == null) {
            mInstance = new DBHelper();
        }
        return mInstance;
    }

    /**
     * 判断数据库文件（xxx.db）是否存在
     */
    public boolean isDBFileExist() {
        File file = new File(DATABASE_PATH + File.separator + DATABASE_NAME);
        return (file.exists() && file.isFile());
    }

    /**
     * open DB
     *
     * @return SQLiteDatabase
     * @throws SQLException
     */
    public SQLiteDatabase readDatabase() throws SQLException {
        if (db == null || !db.isOpen())
            db = this.getReadableDatabase();
        return db;
    }

    public SQLiteDatabase writeDatabase() throws SQLException {
        if (db == null || !db.isOpen())
            db = this.getWritableDatabase();
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*部门查询
  {"departmentid": 1,"department": "大队长","powerid": 5},
 * {"departmentid": 2,"department": "副队长","powerid": 3},
 * {"departmentid": 3,"department": "稽查管理科","powerid": 2},
 * {"departmentid": 4,"department": "稽查督查科","powerid": 2},
 * {"departmentid": 5,"department": "建筑市场稽查科","powerid": 2},
 * {"departmentid": 7,"department": "房地产市场稽查科","powerid": 2},
 * {"departmentid": 8,"department": "行政主任","powerid": 9}]
         */
        String department_db = "create table " + DB_DEPARTMENT + " ( " +
                " departmentid INTEGER PRIMARY KEY , " + // 部门id
                " department TEXT , " + // 部门名称
                " powerid TEXT " + // 权重
                " )";
        db.execSQL(department_db);
       /*职务查询
        *{"ispass":true,"defaultMessage":"获取成功",
        * "models":
        * [{"id":1,"role_name":"管理员","powerid":6}
        * ,{"id":2,"role_name":"行政主任","powerid":2}
        * ,{"id":3,"role_name":"大队长","powerid":5}
        * ,{"id":4,"role_name":"副大队长","powerid":3}
        * ,{"id":5,"role_name":"科长","powerid":2}
        * ,{"id":6,"role_name":"科员","powerid":1}]}
        */
        String job_db = "create table " + DB_JOB + " ( " +
                " id INTEGER PRIMARY KEY , " + // 职务id
                " role_name TEXT , " + // 职务名称
                " powerid TEXT " + // 权重
                " )";
        db.execSQL(job_db);
/**
 * 查询某一部门的所有用户/jc_oa/find_department_user (POST))
 * {
 "id": 94,
 "username": "李炳州",
 "password": "730619",
 "realname": "李炳州",
 "departmentid": "8",
 "job": "行政主任",
 "job_type": null,
 "phone": "13230267665",
 "rtime": null,
 "department": null,
 "role_name": null,
 "powerid": null,
 "departments": null,
 "date": null,
 "pdepartment": null,
 "actiontime": null,
 "endtime": null,
 "lusername": null,
 "log_actiontime": null,
 "log_endtime": null
 }
 */
        String departmentid_db = "create table " + DB_DEPARTMENTID + " ( " +
                " id INTEGER PRIMARY KEY , " + //
                " username TEXT , " + //
                " password TEXT , " + //
                " realname TEXT , " + //
                " departmentid TEXT , " + //
                " job TEXT , " + //
                " job_type TEXT , " + //
                " phone TEXT , " + //
                " rtime TEXT , " + //
                " department TEXT , " + //
                " role_name TEXT , " + //
                " powerid TEXT , " + //
                " departments TEXT , " + //
                " date TEXT , " + //
                " pdepartment TEXT , " + //
                " actiontime TEXT , " + //
                " endtime TEXT , " + //
                " lusername TEXT , " + //
                " log_actiontime TEXT , " + //
                " log_endtime TEXT " + //
                " )";
        db.execSQL(departmentid_db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}