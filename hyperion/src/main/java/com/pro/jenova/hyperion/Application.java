package com.pro.jenova.hyperion;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import static org.springframework.boot.WebApplicationType.SERVLET;

@EnableAdminServer
@EnableEurekaClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(SERVLET).run(args);
    }

}
