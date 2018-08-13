package com.intretech.app.base.service;

import com.alibaba.android.arouter.facade.template.IProvider;


public interface HelloService extends IProvider {
    void sayHello(String name);
}
