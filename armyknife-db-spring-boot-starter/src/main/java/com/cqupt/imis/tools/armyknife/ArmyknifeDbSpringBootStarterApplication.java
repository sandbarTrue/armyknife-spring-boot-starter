package com.cqupt.imis.tools.armyknife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

//@SpringBootApplication
public class ArmyknifeDbSpringBootStarterApplication implements CommandLineRunner {

    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ArmyknifeDbSpringBootStarterApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        DataSource dataSource = applicationContext.getBean(DataSource.class);
//        dataSource.getConnection();
    }
}
