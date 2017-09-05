package com.shuoyi.user.service;

import com.shuoyi.user.dao.IUserBaseInfoDao;
import com.shuoyi.user.po.User;
import com.shuoyi.user.po.UserBaseInfo;
import com.shuoyi.user.cache.UserCacheUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaosy-c on 2017/8/30.
 */
@Service("userTempService")
@Transactional("userTransactionManager")
public class UserTempService implements IUSerTempService{

    @Autowired
    private IUserBaseInfoDao userBaseInfoDao;

    @Override
    public void delete(User user) {

        UserBaseInfo oUserInfo = new UserBaseInfo();
        BeanUtils.copyProperties(user, oUserInfo);
        userBaseInfoDao.delete(oUserInfo);
        UserCacheUtil.delUserFromCache(user);
    }

    @Override
    public User getUserById(Integer userId) {
        User oUSer = UserCacheUtil.getUserByUserId(userId);
        if(null == oUSer) {
            oUSer = userBaseInfoDao.findOneUserByUserId(userId);
            if(null != oUSer && oUSer.getDeleted())
                UserCacheUtil.setUser(oUSer);
        }
        return oUSer;
    }

    @Override
    public void update(User user) {
        UserBaseInfo oUserInfo = (UserBaseInfo) userBaseInfoDao.get(user.getUserId());
        if(null != oUserInfo){
            BeanUtils.copyProperties(user, oUserInfo);
            UserCacheUtil.delUserFromCache(user);
            userBaseInfoDao.update(oUserInfo);
        }
    }

    @Override
    public void save(User oUser){
        UserBaseInfo oUserInfo = new UserBaseInfo();
        BeanUtils.copyProperties(oUser, oUserInfo);
        userBaseInfoDao.save(oUserInfo);
    }

}
