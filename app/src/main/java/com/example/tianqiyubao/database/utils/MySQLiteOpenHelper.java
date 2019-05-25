package com.example.tianqiyubao.database.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.tianqiyubao.database.gen.DaoMaster;


/**
 * Created by fanlei on 2017/5/10.
 * 数据库升级所需工具类
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    //在该方法里面保存数据(当应用更新的时候)
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        MigrationHelper.migrate(db, UserConfigTableEntityDao.class, LeiMuTableEntityDao.class, HistorySearchRecordTableEntityDao.class, RecentlyBrowseHistoryDao.class, MyFavoritesTableEntityDao.class);
//    }

}
