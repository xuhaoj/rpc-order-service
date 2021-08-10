package com.jack.applicationInit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Mediator {

    public Map<String, BeanInfo> map = new ConcurrentHashMap<>();

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

    public Map<String, BeanInfo> getMap() {
        return map;
    }

    public void put(String key, BeanInfo beanInfo) {
        map.put(key, beanInfo);
    }

}