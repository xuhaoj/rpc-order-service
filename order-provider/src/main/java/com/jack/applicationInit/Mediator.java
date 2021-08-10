package com.jack.applicationInit;

import com.jack.RpcRequest;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Mediator {

    public static Map<String, BeanInfo> map = new ConcurrentHashMap<>();

    private Mediator() {
    }


    private static volatile Mediator instance;


    public static Mediator getInstance() {
        if (instance == null) {
            synchronized (Mediator.class) {
                if (instance == null) {
                    instance = new Mediator();
                }
            }
        }
        return instance;
    }

    public Object processor(RpcRequest request) {
        try {
            //接口名.方法名
            String key = request.getClassName() + "." + request.getMethodName();
            //取出方法
            BeanInfo beanInfo = map.get(key);
            if (beanInfo == null) {
                return null;
            }
            //bean对象
            Object bean = beanInfo.getBean();
            //方法
            Method method = beanInfo.getMethod();
            //反射
            return method.invoke(bean, request.getArgs());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
