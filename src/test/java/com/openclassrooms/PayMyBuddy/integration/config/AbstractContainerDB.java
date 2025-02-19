package com.openclassrooms.PayMyBuddy.integration.config;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;


/**
 * Abstract class for integration tests with a MySQL container.
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractContainerDB {

    @Autowired
    protected MockMvc mockMvc;


    /**
     * MySQL container.
     */
    @SuppressWarnings("resource")
    protected static final MySQLContainer<?> MY_SQL_CONTAINER =
            new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                    .withDatabaseName("integration-tests-db")
                    .withInitScript("init.sql");

    static {
        MY_SQL_CONTAINER.start();
    }

    /**
     * Set the properties for the MySQL container.
     */
    @BeforeAll
    static void setProperties() {
        System.setProperty("spring.datasource.url", MY_SQL_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", MY_SQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", MY_SQL_CONTAINER.getPassword());
    }

}