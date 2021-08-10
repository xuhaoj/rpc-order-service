package com.jack.service;

import com.jack.IOrderService;
import com.jack.annotation.JackRemoteService;


//该注解bean加载以后会将bean信息保存到哈希表
@JackRemoteService
public class OrderServiceImpl implements IOrderService {

    @Override
    public String queryOrderList() {
        return "this is rpc-order-service queryOrderList method";
    }

    @Override
    public String orderById(String id) {
        return "this is rpc-order-service orderById method,param  is " + id;
    }

}