package group.imis.tools.armyknife.config;


import group.imis.tools.armyknife.controller.api.InterfaceController;
import group.imis.tools.armyknife.controller.view.InterfaceViewController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author zhoujun5
 * @date 15/03/2018
 */
@Configuration
public class ArmyknifeInterfaceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(InterfaceController.class)
    public InterfaceController interfaceController() {
        InterfaceController interfaceController = new InterfaceController();
        return interfaceController;
    }

    @Bean
    @ConditionalOnMissingBean(InterfaceViewController.class)
    public InterfaceViewController interfaceViewController() {
        InterfaceViewController interfaceViewController = new InterfaceViewController();
        return interfaceViewController;
    }
}
