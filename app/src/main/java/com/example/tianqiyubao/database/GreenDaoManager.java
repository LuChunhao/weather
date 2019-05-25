package com.example.tianqiyubao.database;

import android.content.Context;

import com.example.tianqiyubao.database.gen.DaoMaster;
import com.example.tianqiyubao.database.gen.DaoSession;
import com.example.tianqiyubao.database.utils.MySQLiteOpenHelper;


/**
 * greenDao的管理类
 */
public class GreenDaoManager {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static volatile GreenDaoManager mInstance = null;

    private GreenDaoManager() {
    }

    public static GreenDaoManager getInstance() {
        // 双重校验，保证线程安全
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    // 初始化
    public void init(Context context){
        if (mInstance != null) {
            MySQLiteOpenHelper devOpenHelper = new MySQLiteOpenHelper(context, "weather");
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }


}