package com.cqupt.imis.tools.armyknife.controller.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by zhoujun5 on 20/03/2018.
 */
@Data
public class MenuModel {
    private String name;
    private List<MenuModel> subMenuModelList;
    private String link;
    public  boolean equels(Object object){
        if(object==null){
            return false;
        }
        if(this.getClass()!=object.getClass()){
            return false;
        }
        return this.name.equals(((MenuModel)object).getName());
    }
    public  int hashcode(){
        return SumStrAscii(this.name);
    }
    public static int SumStrAscii(String str){
        byte[] bytestr = str.getBytes();
        int sum = 0;
        for(int i=0;i<bytestr.length;i++){
            sum += bytestr[i];
        }
        return sum;
    }
}
