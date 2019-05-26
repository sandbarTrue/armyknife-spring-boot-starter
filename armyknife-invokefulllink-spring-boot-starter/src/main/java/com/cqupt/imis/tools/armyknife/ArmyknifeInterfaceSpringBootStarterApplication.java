package com.cqupt.imis.tools.armyknife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author zhoujun
 */
//@SpringBootApplication
public class ArmyknifeInterfaceSpringBootStarterApplication implements CommandLineRunner {

    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ArmyknifeInterfaceSpringBootStarterApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        DataSource dataSource = applicationContext.getBean(DataSource.class);
//        dataSource.getConnection();
    }
}
