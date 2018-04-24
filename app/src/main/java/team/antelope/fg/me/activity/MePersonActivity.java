package team.antelope.fg.me.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class MePersonActivity extends BaseActivity{
    Toolbar mToolbar;
    Bitmap bitmap1,bitmap2;
    TextView tv_name,tv_user_name,tv_sex,tv_age,tv_email,tv_dealNum,tv_fanNum;
    long personId;
    List<Person> personList;
    CircleImageViewUtil circleImageViewUtil;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        circleImageViewUtil=findViewById(R.id.iv_user_head);
        tv_name =findViewById(R.id.tv_name);
        tv_sex =findViewById(R.id.tv_sex);
        tv_age =findViewById(R.id.tv_age);
        tv_email =findViewById(R.id.tv_email);
        tv_dealNum=findViewById(R.id.tv_dealNum);
        tv_fanNum =findViewById(R.id.tv_fanNum);
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
}
