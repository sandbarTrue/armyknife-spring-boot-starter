package group.imis.tools.armyknife.controller.api;


import com.alibaba.fastjson.JSONObject;
import group.imis.tools.armyknife.common.domain.Constants;
import group.imis.tools.armyknife.common.path.ApiPath;
import group.imis.tools.armyknife.controller.domain.*;
import group.imis.tools.armyknife.util.JavaTypeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * 获取所有的打上了@Service的接口
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    @RequestMapping(ApiPath.INTERFACE_LOAD_API)
    public ResponseEntity<?> getInterfaces() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Map<String, Object> beansWithServiceAnnotation = applicationContext.getBeansWithAnnotation(Service.class);
        if (CollectionUtils.isEmpty(beansWithServiceAnnotation)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        List<InterfaceModel> interfaceModels= buildInterfaceModels(beansWithServiceAnnotation);
        return new ResponseEntity<Object>(interfaceModels, HttpStatus.OK);
    }

    private List<InterfaceModel> buildInterfaceModels(Map<String, Object> beansWithServiceAnnotation) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<InterfaceModel> interfaceModels=new ArrayList<>();
        for (Map.Entry<String, Object> entry : beansWithServiceAnnotation.entrySet()) {
            Object bean = entry.getValue();
            Class beanClass;
            if (bean.getClass().getSimpleName().contains(Constants.CGLIB)) {
                beanClass = bean.getClass().getSuperclass();
            } else {
                beanClass = bean.getClass();
            }
            String beanName = entry.getKey();
            if (beanClass.getInterfaces().length <= 0) {
                continue;
            }
            InterfaceModel interfaceModel = new InterfaceModel();
            interfaceModel.setBeanName(beanName);
            Map<String, Method> methodAndNameMap = buildMethodMapFormBean(beanClass);
            interfaceModel.setMethodModelList(buildMethodModels(beanClass, methodAndNameMap));
            interfaceModels.add(interfaceModel);
        }
        return interfaceModels;
    }

    private List<MethodModel> buildMethodModels(Class beanClass,Map<String, Method> methodAndNameMap) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<MethodModel> result=new ArrayList<>();
        Class[] allInterfaces = beanClass.getInterfaces();
        for (Class cla : allInterfaces) {
            Method[] realmethods = cla.getMethods();
            List<MethodModel> methodModels = new ArrayList<>();
            for (Method method : realmethods) {
                MethodModel methodModel = new MethodModel();
                method = methodAndNameMap.get(method.getName());
                if (method == null) {
                    continue;
                }
                methodModel.setMethodName(method.getName());
                methodModel.setReturnType(method.getReturnType().getSimpleName());
                methodModel.setArumentModelList(buildArguModels(method));
                methodModels.add(methodModel);
            }
            result.addAll(methodModels);
        }
        return result;
    }

    private List<ArumentModel> buildArguModels(Method method) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
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
        return arumentModels;
    }

    private Map<String, Method> buildMethodMapFormBean(Class beanClass) {
        Method[] allmethods = beanClass.getMethods();
        Map<String, Method> map1 = new WeakHashMap<>();
        for (Method method : allmethods) {
            map1.put(method.getName(), method);
        }
        return map1;
    }


    @PutMapping(ApiPath.INTERFACE_INVOKW_API)
    public ViewResult invoke(@RequestBody InvokeModel invokeModel) throws ClassNotFoundException {
        HashMap<String, Object> resultMap = new HashMap<>();
        Map<String, String> nameTypeMap = buildArguNameTypeMap(invokeModel);
        Map<String, String> nameValueMap = buildArguNameValueMap(invokeModel);
        // 组装参数的对象和类数组
        List<Object> arguObjectList = new ArrayList<>();
        List<Class> arguClassList = new ArrayList<>();
        for (Map.Entry<String, String> entry : nameTypeMap.entrySet()) {
            String name = entry.getKey();
            String type = entry.getValue();
            String value = nameValueMap.get(name);
            if (JavaTypeUtils.isBaseType(type)) {
                Object o = JavaTypeUtils.getBaseTypeObject(type, value);
                arguClassList.add(JavaTypeUtils.getBaseTypeClass(type));
                arguObjectList.add(o);
                continue;
            }
            Class<?> c = Class.forName(type);
            arguClassList.add(c);
            Object o = JSONObject.parseObject(value, c);
            arguObjectList.add(o);
        }
        //参数 对象数组
        Object[] arguObjectArray = arguObjectList.toArray();
        //参数 class 数组
        Class[] arguClassArray = arguClassList.toArray(new Class[arguClassList.size()]);
        Object result;
        try {
            //bean 对象
            Object object = applicationContext.getBean(invokeModel.getBeanName());
            Method method = object.getClass().getMethod(invokeModel.getMethodName(), arguClassArray);
            result = method.invoke(object, arguObjectArray);
        } catch (Exception e) {
            return new ViewResult().buildError(e.getCause().getMessage());
        }
        resultMap.put("data", JSONObject.toJSONString(result));
        return new ViewResult().buildSuccess(resultMap);
    }
    private Map<String, String> buildArguNameValueMap(@RequestBody InvokeModel invokeModel) {
        Map<String, String> nameValueMap = new HashMap<String, String>();
        for (int j = 0; j < invokeModel.getArguType().length; j++) {
            nameValueMap.put(invokeModel.getArguName()[j], invokeModel.getArguValue()[j]);
        }
        return nameValueMap;
    }

    private Map<String, String> buildArguNameTypeMap(@RequestBody InvokeModel invokeModel) {
        Map<String, String> nameTypeMap = new LinkedHashMap<String, String>();
        for (int j = 0; j < invokeModel.getArguType().length; j++) {
            nameTypeMap.put(invokeModel.getArguName()[j], invokeModel.getArguType()[j]);
        }
        return nameTypeMap;
    }

}
