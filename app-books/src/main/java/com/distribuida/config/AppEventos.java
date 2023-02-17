package com.distribuida.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

@ApplicationScoped
public class AppEventos {

    @Inject
    DataSource dataSource;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object event ) {
        System.out.println("Iniciando migración de base de datos");

        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .load();

        flyway.migrate();

        System.out.println("Migración de base de datos completada");

    }
}
