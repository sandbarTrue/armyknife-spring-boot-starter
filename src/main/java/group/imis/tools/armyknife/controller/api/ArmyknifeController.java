package group.imis.tools.armyknife.controller.api;


import group.imis.tools.armyknife.common.annotation.ArmyknifeTools;
import group.imis.tools.armyknife.common.domain.MenuModel;
import group.imis.tools.armyknife.common.enums.MenuModelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by zhoujun5 on 21/10/2017.
 */
@RestController
@Configuration
@RequestMapping("/api/armyknife/tools/")
public class ArmyknifeController {

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("loadMenuData")
    public ResponseEntity<?> loadMenuData(){
        Map<String, Object> map=applicationContext.getBeansWithAnnotation(ArmyknifeTools.class);
        if (CollectionUtils.isEmpty(map)){
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<MenuModel> menuModels=new ArrayList<MenuModel>();
        Map<MenuModel,List<MenuModel>> map1=new WeakHashMap<>();
        for (Object value : map.values()) {
            Annotation[] annotations= value.getClass().getAnnotations();
            for(Annotation annotation:annotations){
                ArmyknifeTools armyknifeTools=(ArmyknifeTools)annotation.annotationType().getAnnotation(ArmyknifeTools.class);
                if(armyknifeTools==null){
                    continue;
                }
                MenuModel menuModel=new MenuModel();
                menuModel.setName(armyknifeTools.menu().getName());
                menuModel.setLink(armyknifeTools.menu().getLink());
                if(armyknifeTools.submenu()[0].equals(MenuModelEnum.NULL)){
                    if(!map1.containsKey(menuModel)){
                        map1.put(menuModel,null);
                    }
                    continue;
                    }
                    List<MenuModel> subMenuModelList=new ArrayList<MenuModel>();
                    for(MenuModelEnum menuModelEnum:armyknifeTools.submenu()){
                        MenuModel subMenuModel=new MenuModel();
                        subMenuModel.setName(menuModelEnum.getName());
                        subMenuModel.setLink(menuModelEnum.getLink());
                        subMenuModelList.add(subMenuModel);
                    }
                if(!map1.containsKey(menuModel)){
                    map1.put(menuModel,subMenuModelList);
                    continue;
                }
                List<MenuModel> menuModel1=map1.get(menuModel);
                menuModel1.addAll(subMenuModelList);
                }
        }
        for(Map.Entry entry:map1.entrySet()){
            MenuModel menuModel=(MenuModel) entry.getKey();
            menuModel.setSubMenuModelList((List<MenuModel>)entry.getValue());
            menuModels.add(menuModel);
        }
        return new ResponseEntity<Object>(menuModels,HttpStatus.OK);
    }
}
