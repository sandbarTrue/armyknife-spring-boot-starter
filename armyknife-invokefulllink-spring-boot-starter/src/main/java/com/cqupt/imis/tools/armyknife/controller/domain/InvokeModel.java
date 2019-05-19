package com.cqupt.imis.tools.armyknife.controller.domain;

import lombok.Data;

/**
 *
 * @author zhoujun5
 * @date 29/03/2018
 */
@Data
public class InvokeModel {
    private String beanName;
    private String methodName;
    private String[] arguType;
    private String[] arguValue;
    private String[] arguName;
}
