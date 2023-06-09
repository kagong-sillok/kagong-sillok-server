package org.prography.kagongsillok.acceptance;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DatabaseCleanupConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public DatabaseCleanup databaseCleanup() {
        return new DatabaseCleanup(entityManager);
    }
}
