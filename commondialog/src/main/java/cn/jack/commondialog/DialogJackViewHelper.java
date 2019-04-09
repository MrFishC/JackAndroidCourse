package cn.jack.commondialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-8
 * describe:dialog辅助工具类
 */
class DialogJackViewHelper {

    private View mContentView;

    // 防止内存泄露,同时减少findViewById的次数
    private SparseArray<WeakReference<View>> mViews;

    public DialogJackViewHelper() {
        mViews = new SparseArray<>();
    }

    public DialogJackViewHelper(Context context, int layoutResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }

    public void setContentView(View view) {
        this.mContentView = view;
    }


    public View getContentView() {
        return mContentView;
    }

    /**
     * 设置文本内容
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    /**
     * 设置监听
     * @param viewId
     * @param listener
     */
    public void setOnclickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if(view !=null){
            view.setOnClickListener(listener);
        }
    }

    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewReference = mViews.get(viewId);

        View view = null;
        if (viewReference != null) {
            view = viewReference.get();
        }

        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }
}
