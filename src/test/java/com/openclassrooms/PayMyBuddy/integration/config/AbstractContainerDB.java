package com.openclassrooms.PayMyBuddy.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractContainerDB {

    @Autowired
    protected MockMvc mockMvc;

    @SuppressWarnings("resource")
    protected static final MySQLContainer<?> MY_SQL_CONTAINER =
            new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                    .withDatabaseName("integration-tests-db")
                    .withInitScript("init.sql");

    static {
        MY_SQL_CONTAINER.start();
    }
}