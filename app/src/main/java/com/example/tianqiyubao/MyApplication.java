package com.example.tianqiyubao;

import android.content.Context;

import com.example.tianqiyubao.database.GreenDaoManager;
import com.example.tianqiyubao.database.gen.DaoMaster;
import com.example.tianqiyubao.database.gen.DaoSession;

import org.greenrobot.greendao.database.Database;
import org.litepal.LitePalApplication;

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //setUpDataBase(this);
        GreenDaoManager.getInstance().init(this);// 初始化数据库
    }

    private void setUpDataBase(Context context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, "weather");
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession session = daoMaster.newSession();
    }
}
