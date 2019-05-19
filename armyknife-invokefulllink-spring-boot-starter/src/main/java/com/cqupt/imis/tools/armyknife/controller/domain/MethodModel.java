package com.cqupt.imis.tools.armyknife.controller.domain;

import lombok.Data;

import java.util.List;

/**
 *
 * @author zhoujun5
 * @date 28/03/2018
 */
@Data
public class MethodModel {
    private String methodName;

    private String returnType;

    private List<ArumentModel> arumentModelList;

}
