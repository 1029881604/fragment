package team.antelope.fg.me.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import team.antelope.fg.R;
import team.antelope.fg.common.GlideApp;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.CircleImageViewUtil;
import team.antelope.fg.util.SetRoundImageViewUtil;

public class MePersonActivity extends BaseActivity implements View.OnClickListener{
    Toolbar mToolbar;
    Bitmap bitmap1,bitmap2;
    TextView tv_name,tv_user_name,tv_sex,tv_age,tv_email,tv_dealNum,tv_fanNum;
    long personId;
    List<Person> personList;
    CircleImageViewUtil circleImageViewUtil;
    private LinearLayout layout_fans;
    private LinearLayout layout_follow;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        circleImageViewUtil= (CircleImageViewUtil) findViewById(R.id.iv_user_head);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_dealNum= (TextView) findViewById(R.id.tv_dealNum);
        tv_fanNum = (TextView) findViewById(R.id.tv_fanNum);
        layout_fans = (LinearLayout) findViewById(R.id.layout_fans);
        layout_follow = (LinearLayout) findViewById(R.id.layout_follow);
        initLayoutView();
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//mToolbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

layout_fans.setOnClickListener(this);
        layout_follow.setOnClickListener(this);
    }

    private void initLayoutView() {
        personId = getIntent().getLongExtra("person_id",0l);
        PersonDaoImpl personDao = new PersonDaoImpl(this);
        Person person =personDao.queryById(personId);
        String personName=person.getName();
        mToolbar.setTitle(personName);
        tv_name.setText(person.getName());
        tv_sex.setText(person.getSex());
        tv_age.setText(String.valueOf(person.getAge()) );
        tv_email.setText(person.getEmail());
        tv_dealNum.setText(String.valueOf(person.getDealnum()));
        tv_fanNum.setText(String.valueOf(person.getFansnum()) );
        RequestOptions options = new RequestOptions();
        GlideApp.with(MePersonActivity.this)
                .load(person.getHeadImg())
                .placeholder(R.mipmap.default_avatar400)
                .error(R.mipmap.error400)
                .apply(options)
                .into(circleImageViewUtil);

    }

    @Override
    public int getLayout() {
        return R.layout.me_person_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_fans:
                Intent intent1  = new Intent(MePersonActivity.this,MeFansListActivity.class);
                intent1.putExtra("userId",personId);
                startActivity(intent1);
                break;
            case R.id.layout_follow:
                Intent intent2 = new Intent(MePersonActivity.this,MeFanFollowActivity.class);
                intent2.putExtra("userId",personId);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
