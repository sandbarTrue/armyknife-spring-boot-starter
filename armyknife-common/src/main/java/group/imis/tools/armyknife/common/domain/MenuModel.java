package group.imis.tools.armyknife.common.domain;

import java.util.List;

/**
 *
 * @author zhoujun5
 * @date 27/03/2018
 */

public class MenuModel {
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单链接
     */
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private List<MenuModel> subMenuModelList;

    public List<MenuModel> getSubMenuModelList() {
        return subMenuModelList;
    }

    public void setSubMenuModelList(List<MenuModel> subMenuModelList) {
        this.subMenuModelList = subMenuModelList;
    }

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
