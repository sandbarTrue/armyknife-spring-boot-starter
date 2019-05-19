package com.cqupt.imis.tools.armyknife.config;

import com.cqupt.imis.tools.armyknife.controller.api.DbController;
import com.cqupt.imis.tools.armyknife.controller.view.DbViewController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author jinyu.meng
 * @date 2018/3/27
 */
@Configuration
public class ArmyknifeDbAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(DbController.class)
    public DbController dbController() {
        DbController dbController = new DbController();

        return dbController;
    }

    @Bean
    @ConditionalOnMissingBean(DbViewController.class)
    public DbViewController dbViewController() {
        DbViewController dbViewController = new DbViewController();
        return dbViewController;
    }

}
