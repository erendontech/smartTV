package com.stvc.config;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayStarter {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void migrate() {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        flyway.migrate();
    }
}
