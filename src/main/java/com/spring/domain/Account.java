package com.spring.domain;

import com.spring.annotation.Autowired;
import com.spring.annotation.Component;
import com.spring.annotation.Qualifier;
import com.spring.annotation.Value;

@Component
public class Account {
    @Value("小明")
    private String name;
    @Value("23")
    private Integer age;
    @Autowired
    @Qualifier("school")
    private School school;

    public Account() {
    }

    public Account(String name, Integer age, School school) {
        this.name = name;
        this.age = age;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school=" + school +
                '}';
    }
}
