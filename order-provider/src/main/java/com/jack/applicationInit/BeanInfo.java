package com.jack.applicationInit;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class BeanInfo {

    private Object bean;

    private Method method;


}
