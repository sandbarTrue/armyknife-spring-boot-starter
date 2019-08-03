package group.imis.tools.armyknife.controller.domain;

import lombok.Data;

import java.util.List;

/**
 *方法模型
 * @author zhoujun5
 * @date 28/03/2018
 */
@Data
public class MethodModel {
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 返回值
     */
    private String returnType;
    /**
     * 参数模型列表
     */
    private List<ArumentModel> arumentModelList;

}
