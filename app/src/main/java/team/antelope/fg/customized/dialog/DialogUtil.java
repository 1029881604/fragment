package team.antelope.fg.customized.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import team.antelope.fg.R;
import team.antelope.fg.customized.dialog.DialogButtonListener;

/**
 * Create by Kyrene
* @说明 确认操作对话框
* @创建日期 2018/5/16 上午8:44
*/
public class DialogUtil {

    private AlertDialog dlg;
    private ImageView ivIcon;
    private TextView tvText;
    private Button btnCancel,btnSure;

    private Context context;
    private int imgResId = 0;
    private String text;
    private DialogButtonListener listener;

    public void show(String text, final DialogButtonListener listener) {
        this.context = BaseActivity.getInstance();
        this.text = text;
        this.listener = listener;
        createDialog();
        setValue();
    }

    public void show( int imgResId, String text, final DialogButtonListener listener) {
        this.context = BaseActivity.getInstance();
        this.text = text;
        this.listener = listener;
        this.imgResId = imgResId;
        createDialog();
        setValue();
    }

    public void show(Context context, String text, final DialogButtonListener listener) {
        this.context = context;
        this.text = text;
        this.listener = listener;
        createDialog();
        setValue();
    }

    public void show(Context context, int imgResId, String text, final DialogButtonListener listener) {
        this.context = context;
        this.text = text;
        this.listener = listener;
        this.imgResId = imgResId;
        createDialog();
        setValue();
    }

    //创建Dialog、初始化控件  
    private void createDialog() {
        dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.lx_dialog);
        window.setGravity(Gravity.CENTER);//居中  
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//背景透明  
        ivIcon = (ImageView) window.findViewById(R.id.ivIcon);
        tvText = (TextView) window.findViewById(R.id.tvText);
        btnCancel = (Button) window.findViewById(R.id.btnCancel);
        btnSure = (Button) window.findViewById(R.id.btnSure);
    }

    //设置控件值  
    private void setValue() {
        if (imgResId != 0) {
            ivIcon.setImageResource(imgResId);
        } else {
            ivIcon.setVisibility(View.GONE);
        }
        tvText.setText(text);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancel();
                dlg.dismiss();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sure();
                dlg.dismiss();
            }
        });
    }
}