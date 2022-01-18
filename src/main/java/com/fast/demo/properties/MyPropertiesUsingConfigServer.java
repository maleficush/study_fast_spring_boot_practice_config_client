package com.fast.demo.properties;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.cloud.context.config.annotation.RefreshScope;

//@Configuration - 생략하기 위해서는 main쪽에서 @ConfigurationPropertiesScan을 이용하여
//                 검색할 빈 패키지경로를 명시적으로 알려주어야 한다.
// @ConstructorBinding을 이용한 immutable한 프로퍼티 객체 생성

// @ConstructorBinding // ConsructorBinding의 경우 config-server의 업데이트 되는 내용이 runtime시에는 업데이트가 되지 않는다.
                       // config-server의 업데이트 되는 내용이 runtime시에 반영되기 위해서는
                       // ConsructorBinding을 제거하고 다음처럼 필드의 final을 빼고, setter를 추가하여 필드 주입이 가능하도록
                       // 하여야 한다.
@Setter
@RefreshScope  // actuator의 refresh를 이용하여 빈을 refresh 하기 위한 설정
@ConfigurationProperties("my")
@RequiredArgsConstructor
public class MyPropertiesUsingConfigServer {
    private  String value;
    private  String finalvalue;


    public String getValue() {
        return value;
    }

    public String getFinalvalue() {
        return finalvalue;
    }

}