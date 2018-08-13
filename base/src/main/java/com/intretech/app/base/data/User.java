package com.intretech.app.base.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yq06171 on 2018/7/4.
 */
@Entity
public class User extends BaseData {
    private String userName;
    private String userAge;

    @Generated(hash = 778604750)
    public User(String userName, String userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
}
