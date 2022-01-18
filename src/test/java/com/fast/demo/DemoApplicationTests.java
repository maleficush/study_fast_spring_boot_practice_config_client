package com.fast.demo;

import com.fast.demo.domain.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;

@Testcontainers
@SpringBootTest
class DemoApplicationTests {
    private static Logger logger =
            LoggerFactory.getLogger(DemoApplicationTests.class);

    @Autowired
    private ObjectMapper mapper;

    @Container
    private static final GenericContainer<?> redisContainer =
        new GenericContainer<>(DockerImageName.parse("redis:latest"));

    // 로거를 주입하여 컨테이너의 로그를 볼 수 있도록 설정
    @BeforeAll
    static void setup(){
        redisContainer.followOutput(new Slf4jLogConsumer( logger ));
    }

    // 프로퍼티 값을 기동후 나중에 셋업해줄 수 있는 기능
    // 레디스 컨테이너의 외부 접속포트가 랜덤하게 설정되어 애플리케이션 기동중에 해당 랜덤포트를 스프링에서 가져와
    // 접속포트로 사용할 수 있도록 셋팅하기 위함
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.cache.type",  () -> "redis");

        // 레디스 컨테이너의 외부 접속  포트가 랜덤하게 설정되어서, 컨테이너의 내부 서비스 포트 6379에 해당하는
        // 외부 포트를 가져와서, 스프링에서 접속하도록 설정
        registry.add("port",  () -> redisContainer.getMappedPort(6379));
    }

    @Test
    void contextLoads() throws Exception{
        // Given

        // When

        // Then
        org.testcontainers.containers.Container.ExecResult execResult =
                redisContainer.execInContainer("redis-cli", "get", "student:jack");

//        Student actual = mapper.readValue(execResult.getStdout(), Student.class);

        assertThat(redisContainer.isRunning()).isTrue();
        System.out.println("111111 : " + execResult.getStdout());
//        logger.info("222222 : ", execResult.getStdout());
    }
}
