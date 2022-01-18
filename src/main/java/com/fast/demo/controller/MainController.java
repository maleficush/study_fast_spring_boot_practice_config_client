package com.fast.demo.controller;

import com.fast.demo.properties.MyProperties;
import com.fast.demo.properties.MyPropertiesUsingConfigServer;
import com.fast.demo.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final MyProperties myProperties;
    private final MyPropertiesUsingConfigServer myPropertiesUsingConfigServer;
    private final MyService myService;

    // actuator가 없이 빈의 refreshScope를 적용하기 위한 설정
    private final ContextRefresher contextRefresher;



    @GetMapping("/my-value")
    public Map<String, String> myHeight(){
        return Map.of(
                // @ConfigurationProperties을 이용한 프로퍼티 값 읽기: 최초에만 config-server로부터 값을 읽음
                "test.value (@Configuration Property using ConstructorBinding)", myProperties.getValue(),
                // @ConfigurationProperties을 사용하지않고 프로퍼티 값 읽기: runtime상태에서도
                // config-server로부터 값을 읽어 업데이트 가능
                // actuator의 refresh를 이용하거나
                // contextRefresher.refresh()를 이용하여 @RefreshScope가 적용된
                // 빈들이 refresh된 후에 값을 새로 읽을 수 있다.
                "test.value (@Configuration Property not using ConstructorBinding)", myPropertiesUsingConfigServer.getValue(),

                // @Value(롬복)을 이용한 로컬 프로퍼티 값 읽기
                "test.value (@Value)", myService.getMyValue()
        );
    }

    // actuator의 빈 refresh 기능을 actuator없이 사용하기 위해서 API를 만들어준다.
    @GetMapping("/refresh")
    public Map<String, Set<String>> refresh(){
        return Map.of("Done.", contextRefresher.refresh());
    }



}
