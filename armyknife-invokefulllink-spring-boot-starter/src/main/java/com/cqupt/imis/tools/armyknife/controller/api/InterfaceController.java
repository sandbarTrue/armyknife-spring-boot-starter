package com.cqupt.imis.tools.armyknife.controller.api;


import com.alibaba.fastjson.JSONObject;
import com.cqupt.imis.tools.armyknife.common.path.ApiPath;
import com.cqupt.imis.tools.armyknife.controller.domain.*;
import com.cqupt.imis.tools.armyknife.util.JavaTypeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * @author zhoujun5
 * @date 21/10/2017
 */
@RequestMapping
@RestController
public class InterfaceController {

    @Resource

    private ApplicationContext applicationContext;

    @RequestMapping(ApiPath.INTERFACE_LOAD_API)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getInterfaces() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        Map<String, Object> map = applicationContext.getBeansWithAnnotation(Service.class);
        List<InterfaceModel> interfaceModels = new ArrayList<>();
        if (CollectionUtils.isEmpty(map)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object bean = entry.getValue();
            Class beanClass;
            if (bean.getClass().getSimpleName().contains("CGLIB")) {
                beanClass = bean.getClass().getSuperclass();
            } else {
                beanClass = bean.getClass();
            }
            String beanName = entry.getKey();
            if (beanClass.getInterfaces().length <= 0) {
                continue;
            }
            Class[] c = beanClass.getInterfaces();
            InterfaceModel interfaceModel = new InterfaceModel();
            interfaceModel.setBeanName(beanName);
            Method[] allmethods = beanClass.getMethods();
            Map<String, Method> map1 = new WeakHashMap<>();
            for (Method method : allmethods) {
                map1.put(method.getName(), method);
            }
            for (Class cla : c) {
                Method[] realmethods = cla.getMethods();
                List<MethodModel> methodModels = new ArrayList<>();
                for (Method method : realmethods) {
                    MethodModel methodModel = new MethodModel();
                    method = map1.get(method.getName());
                    if (method == null) {
                        continue;
                    }
                    methodModel.setMethodName(method.getName());
                    methodModel.setReturnType(method.getReturnType().getSimpleName());
                    String[] arguName = parameterNameDiscoverer.getParameterNames(method);
                    Class[] arguType = method.getParameterTypes();
                    List<ArumentModel> arumentModels = new ArrayList<>();
                    if (null != arguName) {
                        for (int i = 0; i < arguName.length; i++) {
                            ArumentModel arumentModel = new ArumentModel();
                            arumentModel.setArguName(arguName[i]);
                            arumentModel.setArguType(arguType[i].getName());
                            arumentModel.setArguSimpleType(arguType[i].getSimpleName());
                            Object o = JavaTypeUtils.getBaseObjectInvitialValue(arguType[i]);
                            try {
                                arumentModel.setInitialValue(JSONObject.toJSONString(o));
                            } catch (Exception e) {
                                arumentModel.setInitialValue("null");
                            }

                            arumentModels.add(arumentModel);
                        }
                    }
                    methodModel.setArumentModelList(arumentModels);
                    methodModels.add(methodModel);
                }
                interfaceModel.setMethodModelList(methodModels);
                interfaceModels.add(interfaceModel);
            }
        }

        return new ResponseEntity<Object>(interfaceModels, HttpStatus.OK);
    }


    @PutMapping(ApiPath.INTERFACE_INVOKW_API)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ViewResult invoke(@RequestBody InvokeModel invokeModel) throws ClassNotFoundException {
        HashMap<String, Object> resultMap = new HashMap<>();
        Object object = applicationContext.getBean(invokeModel.getBeanName());
        String[] arguNames = invokeModel.getArguName();
        String[] arguTypes = invokeModel.getArguType();
        String[] arguValues = invokeModel.getArguValue();
        List<Object> objects = new ArrayList<>();
        List<Class> classes = new ArrayList<>();
        Map<String, String> nameTypeMap = new LinkedHashMap<String, String>();
        Map<String, String> nameValueMap = new HashMap<String, String>();
        for (int j = 0; j < arguTypes.length; j++) {
            nameTypeMap.put(arguNames[j], arguTypes[j]);
            nameValueMap.put(arguNames[j], arguValues[j]);
        }
        for (Map.Entry<String, String> entry : nameTypeMap.entrySet()) {
            String name = entry.getKey();
            String type = entry.getValue();
            String value = nameValueMap.get(name);
            if (JavaTypeUtils.isBaseType(type)) {
                Object o = JavaTypeUtils.getBaseTypeObject(type, value);
                classes.add(JavaTypeUtils.getBaseTypeClass(type));
                objects.add(o);
                continue;
            }
            Class<?> c = Class.forName(type);
            classes.add(c);
            Object o = JSONObject.parseObject(value, c);
            objects.add(o);
        }
        Object[] obj = objects.toArray();
        Class[] cla = new Class[classes.size()];
        Class[] cla1 = classes.toArray(cla);
        Object result;
        try {
            Method method = object.getClass().getMethod(invokeModel.getMethodName(), cla1);
            result = method.invoke(object, obj);
        } catch (Exception e) {
            return new ViewResult().buildError(e.getCause().getMessage());
        }
        resultMap.put("data", JSONObject.toJSONString(result));
        return new ViewResult().buildSuccess(resultMap);
    }

}
