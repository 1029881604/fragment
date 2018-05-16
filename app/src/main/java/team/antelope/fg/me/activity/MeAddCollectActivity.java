package team.antelope.fg.me.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import team.antelope.fg.R;
import team.antelope.fg.ui.base.BaseActivity;

/**
 * @Author：Carlos
 * @Date： 2018/5/16 11:39
 * @Description:  暂时无用
 **/
public class MeAddCollectActivity extends BaseActivity implements View.OnClickListener{
      Toolbar mToolbar;
      TextView me_confirm;
      EditText et_heading;//获取标题

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("新建收藏夹");
        me_confirm= (TextView) findViewById(R.id.me_confirm);
        et_heading= (EditText) findViewById(R.id.et_heading);
        setSupportActionBar(mToolbar);
        me_confirm.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.me_add_collect_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_confirm:
                if (et_heading.getText().toString().equals("")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MeAddCollectActivity.this);
                    dialog.setTitle("警告");
                    dialog.setMessage("请输入标题");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
                else
                {
                    String heading = et_heading.getText().toString();
                    Intent returnData = new Intent();
                    returnData.putExtra("heading",heading);
                    setResult(RESULT_OK,returnData);
                    finish();
                }

        }
    }
}
