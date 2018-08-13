package com.intretech.app.mvvm.main;

import javax.inject.Inject;

/**
 * Created by yq06171 on 2018/7/31.
 */

public class User {
    private String name;
    private int age;
    private String firstName;
    @Inject
    public User(String name, int age, String firstName) {
        this.name = name;
        this.age = age;
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
