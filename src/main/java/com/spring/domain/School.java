package com.spring.domain;

import com.spring.annotation.Component;
import com.spring.annotation.Value;


@Component
public class School {
    @Value("清华附中")
    private String name;
    @Value("北京市朝阳区和平路125号")
    private String address;
    @Value("2")
    private Integer level;

    public School() {
    }

    public School(String name, String address, Integer level) {
        this.name = name;
        this.address = address;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", level=" + level +
                '}';
    }
}
