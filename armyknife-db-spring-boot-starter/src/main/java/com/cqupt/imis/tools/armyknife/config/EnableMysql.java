package com.cqupt.imis.tools.armyknife.config;

import com.cqupt.imis.tools.armyknife.common.annotation.ArmyknifeTools;
import com.cqupt.imis.tools.armyknife.common.enums.MenuModelEnum;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by jinyu.meng on 2018/3/27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
@Import({ArmyknifeDbAutoConfiguration.class})
@ArmyknifeTools(menu = MenuModelEnum.DB_MENU,submenu = {MenuModelEnum.DB_MYSQL_MENU})
public @interface EnableMysql {

}
