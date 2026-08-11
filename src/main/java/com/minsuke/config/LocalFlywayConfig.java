package com.minsuke.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalFlywayConfig {

    /**
     * Dev seed (V2) may change during development; repair checksum before migrate.
     */
    @Bean
    FlywayMigrationStrategy localFlywayMigrationStrategy() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }
}
