package cn.jack.easykid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * created by Jack
 * date:19-4-1
 * describe:热修复相关的示例代码
 */
public class ParamsSort {
    public void math(Context context){
        int a = 10;
        int b = 1;
        int c = a/b;
        Toast.makeText(context, c + "  厉害啦，我的锅",Toast.LENGTH_SHORT).show();
    }
}
