package com.example.tianqiyubao.database.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.tianqiyubao.database.entity.CityEntity;
import com.example.tianqiyubao.database.entity.User;

import com.example.tianqiyubao.database.gen.CityEntityDao;
import com.example.tianqiyubao.database.gen.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cityEntityDaoConfig;
    private final DaoConfig userDaoConfig;

    private final CityEntityDao cityEntityDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cityEntityDaoConfig = daoConfigMap.get(CityEntityDao.class).clone();
        cityEntityDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        cityEntityDao = new CityEntityDao(cityEntityDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(CityEntity.class, cityEntityDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        cityEntityDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public CityEntityDao getCityEntityDao() {
        return cityEntityDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
