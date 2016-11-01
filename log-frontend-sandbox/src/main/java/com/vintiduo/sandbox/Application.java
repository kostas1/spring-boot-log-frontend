package com.vintiduo.sandbox;

import com.vintiduo.logfrontend.core.LogsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by Kostas on 2016.10.27.
 */

@ComponentScan
@EnableAutoConfiguration
@Import(LogsConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
