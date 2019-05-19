package com.cqupt.imis.tools.armyknife.controller.domain;

import lombok.Data;

import java.util.List;

/**
 *
 * @author zhoujun5
 * @date 28/03/2018
 */
@Data
public class InterfaceModel {

    private String beanName;

    private List<MethodModel> methodModelList;

}
