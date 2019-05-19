package com.cqupt.imis.tools.armyknife.controller.domain;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 *
 * @author zhoujun
 */
public class ViewResult {
    private Integer code=200;
    private HashMap<String,Object> data=new HashMap<>();
    private String msg;
    public void put(String key,Object value){
        data.put(key,value);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ViewResult buildError(String msg){
        ViewResult viewResult=new ViewResult();
        viewResult.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        viewResult.setMsg(msg);
        return viewResult;
    }

    public  ViewResult buildSuccess(HashMap<String,Object> map){
      ViewResult viewResult=new ViewResult();
      viewResult.setCode(HttpStatus.OK.value());
      viewResult.setData(map);
      return viewResult;
    }
}
