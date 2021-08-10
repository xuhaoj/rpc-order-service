package com.jack.service;


import com.jack.IGoodService;
import com.jack.annotation.JackRemoteService;


@JackRemoteService
public class GoodServiceImpl implements IGoodService {

    @Override
    public String getGoodInfoById(Long id) {
        return "this is rpc-order-service getGoodInfoById method,param is " + id;
    }

}