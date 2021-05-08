package com.system.dispatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.system.dispatch.repositories")
//@EntityScan("com.system.dispatch.models")
//@EntityScan(basePackages={"com.system.dispatch.models"})
//@ComponentScans({
//        @ComponentScan("com.system.dispatch.controllers"),
//        @ComponentScan("com.system.dispatch.bootstrap")
//})
//@ComponentScan(basePackages={"com.system.dispatch"})
//@EnableJpaRepositories(basePackages={"com.system.dispatch.repositories"})
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
