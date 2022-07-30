package com.spring.config;

import com.spring.annotation.Component;
import com.spring.annotation.Value;

@Component
public class DataConfig {
    @Value("root")
    private String name;
    @Value("123")
    private String password;
    @Value("jdbc")
    private String driver;
    @Value("localhost:8080")
    private String url;

    public DataConfig() {
    }

    public DataConfig(String name, String password, String driver, String url) {
        this.name = name;
        this.password = password;
        this.driver = driver;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DataConfig{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
