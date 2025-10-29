package com.wilff.gains_spring.migration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MigrationRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting JSON -> MySQL migration...");
        MigrateToMySQL.importAllJsonToMySQL();
        System.out.println("Migration finished!");
    }
}
