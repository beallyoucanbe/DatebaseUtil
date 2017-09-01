package com.shuoyi.user.service;

import com.shuoyi.user.po.User;

/**
 * Created by zhaosy-c on 2017/8/30.
 */
public interface IUserService {
    // 获取用户
    public User getUserById(Integer userId);

    // 删除用户
    public void removeUser(User user);

    //保存用户
    public void save(User user);

    //更新用户
    public void update(User user);
}
