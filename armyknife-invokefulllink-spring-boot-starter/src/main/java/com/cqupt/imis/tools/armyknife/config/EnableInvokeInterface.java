package com.cqupt.imis.tools.armyknife.config;

import com.cqupt.imis.tools.armyknife.common.annotation.ArmyknifeTools;
import com.cqupt.imis.tools.armyknife.common.enums.MenuModelEnum;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by zhoujun5 on 20/03/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
@Import({ArmyknifeInterfaceAutoConfiguration.class})
@ArmyknifeTools(menu = MenuModelEnum.INTERFACEINVOKE,submenu = {MenuModelEnum.NULL})
public @interface EnableInvokeInterface {
}
