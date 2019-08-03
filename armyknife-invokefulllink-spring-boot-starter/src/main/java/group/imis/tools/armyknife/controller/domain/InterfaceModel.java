package group.imis.tools.armyknife.controller.domain;

import lombok.Data;

import java.util.List;

/**
 *接口模型
 * @author zhoujun5
 * @date 28/03/2018
 */
@Data
public class InterfaceModel {
    /**
     * java bean的名称
     */
    private String beanName;

    /**
     * 方法列表
     */
    private List<MethodModel> methodModelList;

}
