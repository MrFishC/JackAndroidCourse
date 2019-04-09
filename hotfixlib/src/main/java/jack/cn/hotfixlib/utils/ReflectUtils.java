package jack.cn.hotfixlib.utils;

import java.lang.reflect.Field;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-1
 * describe:反射工具类
 */
public class ReflectUtils {

    /**
     * 通过反射获取某对象，并设置私有可以访问
     * @param obj   该属性所属类的对象
     * @param clazz 该属性所属类
     * @param field 属性名
     * @return
     */
    private static Object getField(Object obj,Class<?> clazz,String field)
            throws NoSuchFieldException, IllegalAccessException {
        Field localField = clazz.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    /**
     * 通过反射获取某对象，并设置私有可访问
     * @param obj   该属性所属类的对象
     * @param clazz 该属性所属类
     * @param value 值
     */
    public static void setField(Object obj,Class<?> clazz,Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field localField = clazz.getDeclaredField("dexElements");
        localField.setAccessible(true);
        localField.set(obj,value);
    }

    /**
     * 通过反射获取BaseDexClassLoader对象中的PathList对象
     * @param baseDexClassLoader
     * @return PathList对象
     */
    public static Object getPathList(Object baseDexClassLoader) throws
            ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(baseDexClassLoader,
                Class.forName("dalvik.system.BaseDexClassLoader"),
                "pathList");
    }

    /**
     *
     * 通过反射获取BaseDexClassLoader对象的PathList对象，再获取dexElements对象
     * @param paramObject PathList对象
     * @return dexElements对象
     */
    public static Object getDexElements(Object paramObject)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(paramObject,paramObject.getClass(),"dexElements");
    }
}
