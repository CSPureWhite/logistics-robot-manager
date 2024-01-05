package com.example.logistics_robot_manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.logistics_robot_manager.mapper")
@SpringBootApplication
public class LogisticsRobotManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsRobotManagerApplication.class, args);
    }

}
