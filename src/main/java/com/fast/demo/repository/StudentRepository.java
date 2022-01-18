package com.fast.demo.repository;

import com.fast.demo.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Map;


@RequiredArgsConstructor
@Repository
public class StudentRepository {
    private final Map<String, Student> storage;

    @Cacheable("student")
    public Student getStudent(String name){
        System.out.println("값 변경후 최초 호출 -> cache");
        return storage.get(name);
    }


    public Student enroll(String name, Integer age, Student.Grade grade){
//        return storage.put(name, new Student(name, age, grade));
        return storage.put(name, Student.of(name, age, grade));
    }
}
