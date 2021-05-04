package com.rafaelbaetapena;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class TestContainerSetup {

    private final Logger LOG = LoggerFactory.getLogger(TestContainerSetup.class);
    public static final DockerImageName DOCKER_IMAGE_NAME =
            DockerImageName.parse("confluentinc/cp-kafka:5.4.3");

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DOCKER_IMAGE_NAME);

    @Test
    public void setupWorks() {
        kafka.start();
        LOG.debug("Bootstrap Servers: {}", kafka.getBootstrapServers());
        kafka.stop();
    }
}
