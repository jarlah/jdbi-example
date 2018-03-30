package com.example.demo.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class Database {

    private static final Logger log = LoggerFactory.getLogger(Database.class);

    @PostConstruct
    public void init() {
        log.info("Database initialized");
    }

    @Bean
    @Autowired
    public Jdbi dbiFactory(DataSource dataSource) {
        return Jdbi.create(dataSource).installPlugins();
    }

    @Bean
    @Primary
    @Profile({"embedded", "integration"})
    public DataSource dataSource(@Value("${embedded.datasource.port}") Integer port) {
        return getHikariDataSource(getPostgresDatabase(port));
    }

    private static DataSource getHikariDataSource(DataSource dataSource) {
        final HikariConfig config = new HikariConfig();
        config.setConnectionTestQuery("select 1");
        config.setMaximumPoolSize(80);
        config.setIdleTimeout(10000);
        config.setMinimumIdle(2);
        config.setDataSource(dataSource);
        return new HikariDataSource(config);
    }

    private static DataSource getPostgresDatabase(final int port) {
        return getPostgresDatabase(port, port);
    }

    private static DataSource getPostgresDatabase(final int originalPort, final int nextPort) {
        log.info("Trying to create a postgres instance on port " + nextPort);
        if (nextPort > originalPort + 5) {
            throw new IllegalStateException("Could not start postgres. Last port attempted: " + (nextPort - 1));
        }
        DataSource postgresDatabase;
        try {
            postgresDatabase = EmbeddedPostgres
                    .builder()
                    .setCleanDataDirectory(true)
                    .setPort(nextPort)
                    .start()
                    .getPostgresDatabase();
            log.info("Successfully created a postgres instance on port " + nextPort);
        } catch (Throwable e) {
            log.warn("Failed to create postgres instance on port " + nextPort + ". Trying another port");
            return getPostgresDatabase(originalPort, nextPort + 1);
        }
        return postgresDatabase;
    }
}
