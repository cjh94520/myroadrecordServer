package com.smartman.myroadrecord.service.impl;


import com.alibaba.fastjson.JSON;
import com.smartman.myroadrecord.bean.BaseBean;
import com.smartman.myroadrecord.bean.account.AccountReturnBean;
import com.smartman.myroadrecord.dao.UserMapper;
import com.smartman.myroadrecord.model.User;
import com.smartman.myroadrecord.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByPrimaryKey(username);
    }

    @Override
    public Boolean registerUser(User user) {
        int result = userMapper.insert(user);
        if (result < 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String loginByNamePwd(User user) {
        AccountReturnBean bean = new AccountReturnBean();
        User usr = selectByUsername(user.getId());
        if (usr != null) {
            if (user.getPassword().equals(usr.getPassword())) {
                bean.setCode(BaseBean.SUCCEED);
                bean.setData(usr);
                return JSON.toJSONString(bean);
            }
        }
        bean.setCode(BaseBean.FAILED);
        return JSON.toJSONString(bean);
    }

    @Override
    public Boolean checkUser(String id) {
        User user = selectByUsername(id);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean updatePassword(String id, String password) {
        User user = selectByUsername(id);
        if (user == null) {
            return false;
        }
        user.setPassword(password);
        int result = userMapper.updateByPrimaryKey(user);
        if (result < 0) {
            return false;
        }
        return true;
    }

}
