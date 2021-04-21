package com.rafaelbaetapena;

import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class HelloWorldControllerTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void testHelloResponse() {
        final String result = client.toBlocking().retrieve("/hello");
        assertEquals("Hello from Service!", result);
    }

    @Test
    void returnsGermanGreeting() {
        final String result = client.toBlocking().retrieve("/hello/de");
        assertEquals("Hallo", result);
    }

    @Test
    void returnsEnglishGreeting() {
        final String result = client.toBlocking().retrieve("/hello/en");
        assertEquals("Hello", result);
    }

}
