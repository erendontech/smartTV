package com.stvc.config;

import org.springframework.context.ConfigurableApplicationContext;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {

    @Inject
    ConfigurableApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FlywayStarter flywayStarter = (FlywayStarter) context.getBean("FlywayStarter");
        flywayStarter.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}