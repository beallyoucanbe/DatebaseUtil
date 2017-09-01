package com.shuoyi.user.service;

import com.shuoyi.user.cache.UserCacheUtil;
import com.shuoyi.user.po.User;
import com.shuoyi.user.po.UserBaseInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaosy-c on 2017/8/30.
 */
@Service("userService")
@Transactional("userTransactionManager")
public class UserService implements IUserService {

    @Autowired
    IUSerTempService userTempService;


    @Override
    public User getUserById(Integer userID) {
        return userTempService.getUserById(userID);
    }

    @Override
    public void removeUser(User user) {
        if(null != user)
            userTempService.delete(user);
    }

    public void update(User user) {
       if(null != user)
           userTempService.update(user);
    }

    public void save(User oUser){
        if(null != oUser)
            userTempService.save(oUser);
    }
}
