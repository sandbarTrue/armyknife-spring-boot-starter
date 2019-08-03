package group.imis.tools.armyknife.controller.domain;

import lombok.Data;

/**
 *调用模型
 * @author zhoujun5
 * @date 29/03/2018
 */
@Data
public class InvokeModel {
    /**
     * java bean的名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     *参数类型数组
     */
    private String[] arguType;
    /**
     * 参数类型数组
     */
    private String[] arguValue;
    /**
     * 参数名称数组
     */
    private String[] arguName;
}
