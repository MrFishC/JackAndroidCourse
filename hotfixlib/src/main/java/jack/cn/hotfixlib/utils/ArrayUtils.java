package jack.cn.hotfixlib.utils;

import java.lang.reflect.Array;

/**
 * created by Jack
 * email:yucrun@163.com
 * date: 2019/4/1
 * describe:反射工具类
 */
public class ArrayUtils {

    /**
     * 合并数组
     * @param arrayLhs 前数组（插队数组）
     * @param arrayRhs 后数组（已有数组）
     * @return 处理后的新数组
     */
    public static Object combineArray(Object arrayLhs,Object arrayRhs){

        //获得一个数组的class对象，通过Array.newInstance()可以反射生成新的数组
        Class<?> loaclClass = arrayLhs.getClass().getComponentType();

        //前数组长度
        int i = Array.getLength(arrayLhs);

        //新数组长度 = 前数组长度 + 后数组长度
        int j = i + Array.getLength(arrayRhs);

        //生成数组对象
        Object result = Array.newInstance(loaclClass,j);

        //从0开始遍历，如果前数组有值，添加到新数组的第一个位置
        for(int k = 0; k < j;k++){
            if(k < i){
                //从0开始遍历，如果前数组有值，添加到新数组的第一个位置
                Array.set(result,k,Array.get(arrayLhs,k));
            }else {
                //添加完前数组，在添加后数组，合并完成
                Array.set(result,k,Array.get(arrayRhs,k - i));
            }
        }
        //添加完前数组，在添加后数组，合并完成
        return result;
    }

}
