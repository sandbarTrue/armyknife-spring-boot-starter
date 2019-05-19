package com.cqupt.imis.tools.armyknife.util;

import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhoujun5 on 29/03/2018.
 */
public class JavaTypeUtils {

    private static  final org.slf4j.Logger logger= LoggerFactory.getLogger(JavaTypeUtils.class);

    public static  Object getBaseObjectInvitialValue(Class arguType) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        switch (arguType.getName()){
            case "long":
                return 1;
            case "int":
                return 1;
            case "short":
                return 1;
            case "float":
                return 1;
            case "double":
                return 1;
            case "byte":
                return 1;
            case "boolean":
                return true;
            case "char":
                return 'c';
            case "java.lang.Long":
                return 1;
            case "java.lang.String":
                return "arguments";
            case "java.lang.Double":
                return 1;
            case "java.lang.Float":
                return "1";
            case "java.lang.Boolean":
                return true;
            case"java.lang.Byte":
                return 1;
            case "java.lang.Short":
                return 1;
            case "java.lang.Character":
                return 'n';
            case "java.lang.Integer":
                return  1;
            case "java.util.Date":
                return new Date();
            case "java.math.BigDecimal":
                return 1;
            default:
        }
        Object o=getObjectInvitialValue(arguType);
        return o;
    }
    public static  Object getObjectInvitialValue(Class object) throws InvocationTargetException, IllegalAccessException {
        Object o=null;
        try{
            o=object.newInstance();
        }
        catch (Exception e){
            if(List.class.isAssignableFrom(object)){
                return new ArrayList<>();
            }
            else if(Queue.class.isAssignableFrom(object)){
                return new ArrayDeque<>();
            }
            else if(Set.class.isAssignableFrom(object)){
                return new HashSet<>();
            }
            else if(Map.class.isAssignableFrom(object)){
                return new HashMap<>();
            }
            else{
                return null;
            }
        }
        boolean isCollect=isCollection(o);
        if(isCollect){
            return o;
        }

        Method[] methods=object.getDeclaredMethods();
        for(Method method:methods){
            String methodName=method.getName();
            if(methodName.startsWith("set")){
                logger.error("method :{}",method.getName());
                if(methodName=="setAttribute"){
                    System.out.println("qqq");
                }
                if(method.getParameterTypes().length>1){
                    continue;
                }
                String className=method.getParameterTypes()[0].getName();
                switch (className){
                    case "long":
                        method.invoke(o,Long.parseLong("1"));
                        break;
                    case "int":
                        method.invoke(o,Integer.parseInt("1"));
                        break;
                    case "short":
                        method.invoke(o,Short.parseShort("1"));
                        break;
                    case "float":
                        method.invoke(o,Float.parseFloat("1"));
                        break;
                    case "double":
                        method.invoke(o,Double.parseDouble("1"));
                        break;
                    case "byte":
                        method.invoke(o,Byte.parseByte("1"));
                        break;
                    case "boolean":
                        method.invoke(o,true);
                        break;
                    case "char":
                        method.invoke(o,'c');
                        break;
                    case "java.lang.Long":
                        method.invoke(o,Long.valueOf("1"));
                        break;
                    case "java.lang.String":
                        method.invoke(o,"arguments");
                        break;
                    case "java.lang.Double":
                        method.invoke(o,Double.valueOf("1"));
                        break;
                    case "java.lang.Float":
                        method.invoke(o,Float.valueOf("1"));
                        break;
                    case "java.lang.Boolean":
                        method.invoke(o,true);
                        break;
                    case"java.lang.Byte":
                        method.invoke(o,Byte.valueOf("1"));
                        break;
                    case "java.lang.Short":
                        method.invoke(o,Short.valueOf("1"));
                        break;
                    case "java.lang.Character":
                        method.invoke(o,'c');
                        break;
                    case "java.lang.Integer":
                        method.invoke(o,Integer.valueOf("1"));
                        break;
                    case "java.util.Date":
                        method.invoke(o,new Date());
                        break;
                    case "java.math.BigDecimal":
                        method.invoke(o,BigDecimal.ZERO);
                        break;
                    default:
                        try{
                            boolean isAbs = Modifier.isAbstract(method.getParameterTypes()[0].getModifiers()) ;
                            if(isAbs){
                                break;
                            }
                            Object o1=getObjectInvitialValue(method.getParameterTypes()[0]);
                            method.invoke(o,o1);
                        }catch (Exception e){
                        }
                }

            }
        }
        return o;
    }
    public static Class getBaseTypeClass(String className)  {
        switch (className) {
            case "long":
                return long.class;
            case "int":
                return int.class;
            case "short":
                return short.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "byte":
                return byte.class;
            case "boolean":
                return boolean.class;
            case "char":
                return char.class;
            default:
        }
        return null;
    }
    public static Object getBaseTypeObject(String className,String value)  {
        switch (className) {
            case "long":
                return Long.parseLong(value);
            case "int":
                return Integer.parseInt(value);
            case "short":
                return Short.parseShort(value);
            case "float":
                return Float.parseFloat(value);
            case "double":
                return Double.parseDouble(value);
            case "byte":
                return Byte.parseByte(value);
            case "boolean":
                return Boolean.parseBoolean(value);
            case "char":
                return value.charAt(0);
            default:
        }
        return null;
    }
    public static  boolean isBaseType(String className){
        switch (className) {
            case "long":
                return true;
            case "int":
                return true;
            case "short":
                return true;
            case "float":
                return true;
            case "double":
                return true;
            case "byte":
                return true;
            case "boolean":
                return true;
            case "char":
                return true;
                default:
                    return false;
        }
    }
    public static boolean isClassCollection(Class c) {
        return Collection.class.isAssignableFrom(c) || Map.class.isAssignableFrom(c);
    }

    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isCollection(Object ob) {
        return ob instanceof Collection || ob instanceof Map;
    }

    private boolean validSql(String sql){
        if(sql.indexOf("where")==-1) {
            return false;
        }
        return true;
    }
}
