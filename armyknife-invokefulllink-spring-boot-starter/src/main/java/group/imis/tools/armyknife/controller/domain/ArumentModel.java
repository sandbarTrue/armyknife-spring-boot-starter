package group.imis.tools.armyknife.controller.domain;

import lombok.Data;

/**
 *参数模型
 * @author zhoujun5
 * @date 28/03/2018
 */
@Data
public class ArumentModel {
    /**
     *参数名称
     */
    private String arguName;

    /**
     * 参数类型  Long
     */
    private String arguSimpleType;
    /**
     * 参数类型 java.Long
     * */
    private String  arguType;
    /**
     * 初始化值
     */
    private String initialValue;

}
