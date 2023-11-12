package org.documentoviscode.splashyapi.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final ApplicationContext applicationContext;

    public Bootstrap(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running bootstrap...");


        System.out.println("Bootstrap has finished");
    }
}
