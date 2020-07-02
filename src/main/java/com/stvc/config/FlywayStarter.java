package com.stvc.config;

import org.flywaydb.core.Flyway;

import javax.servlet.*;
import java.io.IOException;

public class FlywayStarter implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://localhost:3306/stvc?serverTimezone=UTC", "root", "admin").load();

        flyway.migrate();
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}