package com.rafaelbaetapena.auth;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class SecuredEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(SecuredEndpointTest.class);

    @Inject
    @Client
    JWTClient client;

    @Test
    void returnsStatus() {
        final BearerAccessRefreshToken login = client.login(new UsernamePasswordCredentials(
                "alice@example.com", "secret"));

        final String response = client.status("Bearer " + login.getAccessToken());
        LOG.debug(response);
    }
}