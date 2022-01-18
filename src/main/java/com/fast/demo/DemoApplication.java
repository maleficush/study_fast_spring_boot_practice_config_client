package com.fast.demo;

import com.fast.demo.domain.Student;
import com.fast.demo.properties.MyProperties;
import com.fast.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;


@ConfigurationPropertiesScan("com.fast.demo.properties")
@SpringBootApplication
public class DemoApplication {
    // [4] @Bean의 configuration properties를 이용
    private final MyProperties properties;
    private final StudentService studentService;

    private final String username;
    private final String password;

    // [1] final로 선언
//    private final String finalValue;
//    // [1] 생성자에서 property를 읽어와서 final 변수를 초기화
//    public DemoApplication(@Value("${test.finalvalue}") String finalValue) {
//        this.finalValue = finalValue;
//    }

    // [1] 프로퍼티를 바로 읽어옴 - final은 불가
    @Value("${test.value}")
    private String value;

    // [2]
    private final String finalValue;
    private final Environment environment;
    private final ApplicationContext context;

    // [3] configuration properties를 이용
//    private final MyProperties properties;


    public DemoApplication(@Value("${test.finalvalue}") String finalValue,
                           Environment environment,
                           ApplicationContext context,
                           MyProperties properties,
                           StudentService studentService,
                           @Value("${spring.datasource.username}") String username,
                           @Value("${spring.datasource.password}") String password
                           ) {
        this.finalValue = finalValue;
        this.environment = environment;
        this.context = context;
        this.properties = properties;
        this.studentService = studentService;
        this.username = username;
        this.password = password;
    }

    // PostConstruct를 이용하여 value 가지고 옴
    // PostConstruct : 이 클래스의 빈의 주입이 완료되는 순간 호출
    @PostConstruct
    public void init(){
        System.out.println("[@Value]" + value);
        System.out.println("[@Value]" + finalValue);
        System.out.println("[Environment]" + environment.getProperty("test.finalvalue"));
        System.out.println("[ApplicationContext]" + context.getEnvironment().getProperty("test.finalvalue"));
        System.out.println("[Configuration]" + properties.getValue() + properties.getFinalvalue());
        System.out.println("[Vault username]" + username);
        System.out.println("[Vault password]" + password);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady(){
        studentService.printStudent("jack");
        studentService.printStudent("jack");
        studentService.printStudent("jack");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("test : finalValue");
    }
}
