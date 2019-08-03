package group.imis.tools.armyknife.common.annotation;

import group.imis.tools.armyknife.common.enums.MenuModelEnum;

import java.lang.annotation.*;

/**
 * Created by zhoujun5 on 20/03/2018.
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ArmyknifeTools {

    MenuModelEnum menu();

    MenuModelEnum[] submenu();
}
