package group.imis.tools.armyknife;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

/**
 * @author zhoujun
 */
/*@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableMysql
@EnableInvokeInterface*/
public class ArmyknifeSpringBootStarterApplication implements CommandLineRunner{
    public static void main(String[] args) {

        SpringApplication.run(ArmyknifeSpringBootStarterApplication.class, args);

    }

    @Override
    public void run(String... strings) throws Exception {


    }
}
