package org.documentoviscode.splashyapi.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running bootstrap...");


        System.out.println("Bootstrap has finished");
    }
}
