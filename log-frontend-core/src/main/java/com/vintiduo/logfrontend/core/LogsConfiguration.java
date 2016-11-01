package com.vintiduo.logfrontend.core;

/**
 * Created by Kostas on 2016.10.27.
 */

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan
@Configuration
@EnableWebMvc
@EnableScheduling
public class LogsConfiguration extends WebMvcConfigurerAdapter  {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/", "classpath:/static/");
    }

    static {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
        if(fileAppender != null) {
            fileAppender.stop();
            fileAppender.setFile("new.log");
            PatternLayout pl = new PatternLayout();
            pl.setPattern("%d %5p %t [%c:%L] %m%n)");
            pl.setContext(lc);
            pl.start();
            fileAppender.setLayout(pl);
            fileAppender.setContext(lc);
            fileAppender.start();
        }
        fileAppender.setContext(lc);
        logger.addAppender(fileAppender);
    }
}
