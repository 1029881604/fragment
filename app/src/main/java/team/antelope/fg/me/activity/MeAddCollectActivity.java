package team.antelope.fg.me.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import team.antelope.fg.R;
import team.antelope.fg.ui.base.BaseActivity;

public class MeAddCollectActivity extends BaseActivity {
      Toolbar mToolbar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("新建收藏夹");
        setSupportActionBar(mToolbar);
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
}
