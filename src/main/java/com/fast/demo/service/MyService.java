package com.fast.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class MyService {
    private final String myValue;

    // @Value를 사용하기 위한 생성자 주입
    public MyService(@Value("${test.value}")String myValue){
        this.myValue = myValue;
    }

    public String getMyValue(){
        return myValue;
    }
}
