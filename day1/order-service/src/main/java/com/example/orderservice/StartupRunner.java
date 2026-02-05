package com.example.orderservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final String appName;

    public StartupRunner(String applicationName) {
        this.appName = applicationName;
        System.out.println("[StartupRunner]: Constructor called. App name: " + appName);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("[StartupRunner]: CommandLineRunner.run() executed. Application is now ready to serve!");
        System.out.println("[StartupRunner]: Received command line arguments: " + String.join(", ", args));
    }
}
