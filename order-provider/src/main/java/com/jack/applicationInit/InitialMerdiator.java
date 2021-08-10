package com.jack.applicationInit;


import com.jack.annotation.JackRemoteService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author jackxu
 * bean加载以后将bean的信息保存到哈希表
 */
@Component
public class InitialMerdiator implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().isAnnotationPresent(JackRemoteService.class)) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                //接口名.方法名
                String key = bean.getClass().getInterfaces()[0].getName() + "." + method.getName();
                BeanInfo beanInfo = new BeanInfo();
                beanInfo.setBean(bean);
                beanInfo.setMethod(method);
                Mediator.map.put(key, beanInfo);
            }
        }
        return bean;
    }

}