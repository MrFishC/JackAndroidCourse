package cn.jack.commondialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-8
 * describe:构建者
 */
class AlertJackController {

    private AlertJackDialog mDialog;
    private Window mWindow;
    private DialogJackViewHelper mViewHelper;

    public AlertJackController(AlertJackDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    /**
     * 获取dialog
     * @return AlertJackDialog
     */
    public AlertJackDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取dialog的window
     * @return Window
     */
    public Window getWindow() {
        return mWindow;
    }

    /**
     * 获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     * 给mViewHelper赋值
     * @param viewHelper
     */
    public void setViewHelper(DialogJackViewHelper viewHelper) {
        mViewHelper = viewHelper;
    }

    /**
     * 设置监听
     * @param viewId    控件的id  更改位置  使更进一步接近源码
     * @param listener  点击监听
     */
    private void setOnclickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnclickListener(viewId, listener);
    }

    /**
     * 设置文本
     *
     * @param viewId 控件的id
     * @param text   文本内容
     */
    private void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    /**
     * 放置一系列需要设置的参数
     * 通过AlertDialog中的builder对象为AlertParams中的参数设置值
     */
    public static class AlertParams {

        /**
         * 上下文
         */
        public Context mContext;

        /**
         * 主题
         */
        public int mThemeResId;

        /**
         * 点击对话框以外的区域是否可以取消
         */
        public boolean mCancelable;

        /**
         * 取消监听
         */
        public DialogInterface.OnCancelListener mOnCancelListener;

        /**
         * 对话框消失监听
         */
        public DialogInterface.OnDismissListener mOnDismissListener;

        /**
         * 按键监听
         */
        public DialogInterface.OnKeyListener mOnKeyListener;

        /**
         * 布局View
         */
        public View mView;

        /**
         * 布局的layout id
         */
        public int mViewLayoutResId;

        /**
         * 存放字体的修改
         */
        public SparseArray<CharSequence> mTextArray ;

        /**
         * 存放点击事件
         */
        public SparseArray<View.OnClickListener> mClickArray ;

        public AlertParams(Context context, int themeResId) {
            mTextArray = new SparseArray<>();
            mClickArray = new SparseArray<>();
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        // 动画
        public int mAnimations = 0;
        // 位置
        public int mGravity = Gravity.BOTTOM;
        // 宽度
        public int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        // 高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        /**
         * 绑定和设置参数
         */
        public void apply(AlertJackController alert) {

            DialogJackViewHelper viewHelper = null;

            //设置布局
            if (mView != null) {
                viewHelper = new DialogJackViewHelper();
                viewHelper.setContentView(mView);
            }

            if (mViewLayoutResId != 0) {
                viewHelper = new DialogJackViewHelper(mContext, mViewLayoutResId);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setView()");
            }

            // 给Dialog 设置布局
            alert.getDialog().setContentView(viewHelper.getContentView());

            // 设置 Controller的辅助类  在设置监听之前设置即可
            alert.setViewHelper(viewHelper);

            //设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                alert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            //设置监听
            int clickArraySize = mClickArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                alert.setOnclickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            //设置其它效果
            //配置自定义的效果  全屏  从底部弹出    默认动画
            Window window = alert.getWindow();

            // 设置位置
            if(mGravity != 0) {
                window.setGravity(mGravity);
            }

            // 设置动画
            if(mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }

            // 设置宽高
            WindowManager.LayoutParams params = window.getAttributes();

            params.width = mWidth;
            params.height = mHeight;

            window.setAttributes(params);
        }
    }
}
