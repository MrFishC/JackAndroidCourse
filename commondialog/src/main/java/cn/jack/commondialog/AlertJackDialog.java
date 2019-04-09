package cn.jack.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-8
 * describe:自定义的dialog(需要被创建的对象)
 */
public class AlertJackDialog extends Dialog{

    private AlertJackController mAlert;

    public AlertJackDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertJackController(this, getWindow());
    }

    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }

    /**
     * 规范一系列组装过程
     */
    public static class Builder{

        private final AlertJackController.AlertParams P;

        /**
         * Creates a builder for an alert dialog that uses the default alert
         * dialog theme.
         * @param context the parent context
         */
        public Builder(Context context) {
            //默认的主题
            this(context, R.style.dialog);
        }

        /**
         * Creates a builder for an alert dialog that uses an explicit theme
         * resource.
         */
        public Builder(Context context, int themeResId) {
            P = new AlertJackController.AlertParams(context,themeResId);
        }

        /**
         * 设置宽度
         */
        public AlertJackDialog.Builder setWidth(int width) {
            P.mWidth = width;
            return this;
        }

        /**
         * 设置高度
         * @param height
         * @return
         */
        public AlertJackDialog.Builder setHeight(int height) {
            P.mHeight = height;
            return this;
        }

        /**
         * 设置动画
         * @param animations
         * @return
         */
        public AlertJackDialog.Builder setAnimations(int animations) {
            P.mAnimations = animations;
            return this;
        }

        /**
         * 设置位置
         * @param gravity
         * @return
         */
        public AlertJackDialog.Builder setGravity(int gravity) {
            P.mGravity = gravity;
            return this;
        }

        /**
         * 设置layoutResId
         */
        public AlertJackDialog.Builder setView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * 设置view
         */
        public AlertJackDialog.Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 设置textView内容(需要在create之前调用才会生效)
         * @param viewId
         * @param charSequence
         * @return
         */
        public Builder setText(int viewId,CharSequence charSequence){
            P.mTextArray.put(viewId,charSequence);
            return this;
        }

        /**
         * 设置点击按钮的监听   注意:setOnClick方法是将viewId放在mClickArray集合中,然后再调用create方法时,间接给viewId设置了点击监听
         * @param viewId
         * @param onClickListener
         * @return
         */
        public Builder setOnClick(int viewId,View.OnClickListener onClickListener){
            P.mClickArray.put(viewId,onClickListener);
            return this;
        }

        /**
         * @return
         */
        public AlertJackDialog create(){

            // Context has already been wrapped with the appropriate theme.
            final AlertJackDialog dialog = new AlertJackDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;

        }

        /**
         * copy from源码
         * Creates an {@link AlertJackDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p/>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertJackDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertJackDialog show() {
            final AlertJackDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}

