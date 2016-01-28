package com.smartman.myroadrecord.bean.account;

import com.smartman.myroadrecord.bean.BaseBean;
import com.smartman.myroadrecord.model.User;

/**
 * Created by jiahui.chen on 2016/1/25.
 */
public class AccountReturnBean extends BaseBean{
    public User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AccountReturnBean{" +
                "data=" + data +
                '}';
    }
}
