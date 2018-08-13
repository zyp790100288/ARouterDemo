package com.intretech.app.base.db;

import android.content.Context;

/**
 * Created by yq06171 on 2017/2/8.
 * 数据库操作类
 */

public class DbUtils {
    private static DbUtils mDbUtils;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DbUtils() {
    }

    private DbUtils(Context context, String pwd) {
        DataOpenHelper dataHelper = new DataOpenHelper(context, "intretech-db", null);
        if (pwd == null || pwd.length() < 1) {
             mDaoMaster = new DaoMaster(dataHelper.getWritableDb());
        } else {
             mDaoMaster = new DaoMaster(dataHelper.getEncryptedReadableDb(pwd));
        }
        mDaoSession = mDaoMaster.newSession();
    }

    public static DbUtils getInstance(Context context, String pwd) {
        if (mDbUtils == null) {
            mDbUtils = new DbUtils(context, pwd);
        }
        return mDbUtils;
    }

    private DaoMaster getMaster() {
        return mDaoMaster;
    }

    private DaoSession getSession() {
        return mDaoSession;
    }

    public UserDao getUserDao() {
        return getSession().getUserDao();
    }

    private DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }


}
