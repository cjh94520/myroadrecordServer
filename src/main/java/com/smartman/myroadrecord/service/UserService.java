package com.smartman.myroadrecord.service;


import com.smartman.myroadrecord.model.User;

public interface UserService {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User selectByUsername(String username);


    /**
     * 插入新用户
     * @param user
     * @return
     */
    Boolean registerUser(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    String loginByNamePwd(User user);

    /**
     * 查询用户是否存在
     * @param id
     * @return
     */
    Boolean checkUser(String id);


    /**
     * 修改密码
     * @param id
     * @return
     */
    Boolean updatePassword(String id ,String password);
}
