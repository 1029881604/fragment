package team.antelope.fg.publish.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import team.antelope.fg.R;

/**
 * Created by PC_LRY on 2018/1/6.
 */

public class PublishFbPicCancelOrOkDialog extends Dialog {

    public PublishFbPicCancelOrOkDialog(Context context, String title) {
        //使用自定义Dialog样式
        super(context, R.style.custom_dialog);
        //指定布局
        setContentView(R.layout.publish_fb_pic_dialog_cancel_or_ok);
        //点击外部不可消失
        setCancelable(false);

        //设置标题
        TextView titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        titleTv.setText(title);

        findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                //取消
                cancel();
            }
        });

        findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                ok();
            }
        });
    }

    //确认
    public void ok() {
    }
}
