package com.rafaelbaetapena;

import com.rafaelbaetapena.auth.persistence.UserEntity;
import com.rafaelbaetapena.auth.persistence.UserRepository;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;

import javax.inject.Singleton;

@Singleton
public class TestDataProvider {

    private final UserRepository users;

    public TestDataProvider(UserRepository users) {
        this.users = users;
    }

    @EventListener
    public void init(StartupEvent event) {
        final String email = "alice@example.com";
        if(users.findByEmail(email).isEmpty()) {
            final UserEntity alice = new UserEntity();
            alice.setEmail(email);
            alice.setPassword("secret");
            users.save(alice);
        }
    }
}
