package team.antelope.fg.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import team.antelope.fg.R;
import team.antelope.fg.ui.base.BaseActivity;
/**
 * @Author：yy
 * @Date： 2017/12/12 11:40
 * @Description: 暂时无用
 **/

public class MePublicActivity extends BaseActivity  {
    Toolbar mToolbar;
    private TextView txt_title;
    private ImageView img_back;


    @Override
    protected void initView(Bundle savedInstanceState) {
        txt_title = (TextView) findViewById(R.id.txt_title);
//        String Name = getIntent().getStringExtra(Constants.NAME);
//        txt_title.setText(Name);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }//end initView

    @Override
    public int getLayout() {
        return R.layout.me_web_activity;
    }

/*

protected  void init(){
        setOnListener();
}


    protected void setOnListener() {
        img_back.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }*/
}
