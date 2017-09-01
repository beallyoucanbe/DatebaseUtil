package com.shuoyi.test;

import com.shuoyi.user.po.User;
import com.shuoyi.user.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaosy-c on 2017/8/30.
 */

public class UserServiceTest extends BaseJunit4Test {

    @Autowired
    IUserService userService;

    @Test
    public void insert(){

//        User myUSer = new User();
//        myUSer.setAccount("hello");
//        myUSer.setGLDUserID("123456");
//        myUSer.setPhoneNum("18217562757");
//        userService.save(myUSer);
        User oUser = userService.getUserById(2013571);
        System.out.println(oUser);
        System.out.println(oUser.getAccount());
    }
}
