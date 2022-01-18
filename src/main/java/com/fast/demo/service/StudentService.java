package com.fast.demo.service;

import com.fast.demo.domain.Student;
import com.fast.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    public void printStudent(String name){
        Student student = studentRepository.getStudent(name);
        System.out.println("찾는학생 : " + student);
    }

    // 이 클래스의 빈 주입이 완료되었을 경우 호출
    @PostConstruct
    public void init(){
        log.info("redis test data jack 생성");
        studentRepository.enroll("jack", 15, Student.Grade.B);
        studentRepository.enroll("cassie", 18, Student.Grade.A);
        studentRepository.enroll("fred", 14, Student.Grade.C);
    }
}
