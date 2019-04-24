package cn.jack.easykid.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.jack.commondialog.AlertJackDialog;
import cn.jack.easykid.R;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-8
 * describe:万能的dialog(装逼的标题,模仿源码构建者模式的思路实现一下对话框)
 */
public class Activtiy_04 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_04);

        AlertJackDialog dialog = new AlertJackDialog.Builder(this)
                .setView(R.layout.detail_comment_dialog)
                .setOnClick(R.id.submit_btn, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Toast.makeText(Activtiy_04.this,
                        "点击事件", Toast.LENGTH_LONG).show();

                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(Activtiy_04.this,
                                "取消", Toast.LENGTH_LONG).show();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(Activtiy_04.this,
                                "关闭", Toast.LENGTH_LONG).show();
                    }
                })
                .setText(R.id.submit_btn,"变更")
                .show();

    }

}
