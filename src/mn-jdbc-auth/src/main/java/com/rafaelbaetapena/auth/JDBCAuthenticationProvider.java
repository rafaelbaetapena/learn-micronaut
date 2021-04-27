package com.rafaelbaetapena.auth;

import com.rafaelbaetapena.auth.persistence.UserEntity;
import com.rafaelbaetapena.auth.persistence.UserRepository;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class JDBCAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCAuthenticationProvider.class);
    private final UserRepository users;

    public JDBCAuthenticationProvider(UserRepository users) {
        this.users = users;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(
            @Nullable HttpRequest<?> httpRequest,
            AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            final String identity = (String) authenticationRequest.getIdentity();
            LOG.debug("User {} tries to login ...", identity);

            final Optional<UserEntity> maybeUser = users.findByEmail(identity);
            if(maybeUser.isPresent()) {
                LOG.debug("Found user: {}", maybeUser.get().getEmail());
                final String secret = (String) authenticationRequest.getSecret();
                if (maybeUser.get().getPassword().equals(secret)) {
                    // pass
                    LOG.debug("User logged in.");
                    final HashMap<String, Object> attributes = new HashMap<>();
                    attributes.put("hair_color", "brown");
                    attributes.put("language", "en");
                    final UserDetails userDetails = new UserDetails(
                            identity,
                            Collections.singletonList("ROLE_USER"),
                            attributes);
                    emitter.onNext(userDetails);
                    emitter.onComplete();
                    return;
                } else {
                    LOG.debug("Wrong password provided for user {}", identity);
                }
            } else {
                LOG.debug("No user found with email: {}", identity);
            }

            emitter.onError(new AuthenticationException(new AuthenticationFailed("Wrong " +
                    "username or password!")));
        }, BackpressureStrategy.ERROR);
    }
}
