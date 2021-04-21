package com.rafaelbaetapena;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        final ApplicationContext context = Micronaut.run(Application.class, args);
        final HelloWorldService service = context.getBean(HelloWorldService.class);
        LOG.info(service.sayHi());
    }
}
