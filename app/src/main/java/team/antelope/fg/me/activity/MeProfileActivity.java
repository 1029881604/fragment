package team.antelope.fg.me.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseActivity;

public class MeProfileActivity extends BaseActivity implements View.OnClickListener {

    Toolbar mToolbar;
     ImageView iv_chang;
    TextView tv_set_name, tv_age, tv_sex, tv_email, tv_dealNum, tv_fanNum;
    SharedPreferences sharedPreferences;
    String user_name;
    String user_age,user_sex,user_email;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_sex = findViewById(R.id.tv_sex);
        tv_age = findViewById(R.id.tv_age);
        tv_email = findViewById(R.id.tv_email);
        tv_dealNum = findViewById(R.id.tv_dealNum);
        tv_fanNum = findViewById(R.id.tv_fanNum);
        iv_chang =findViewById(R.id.iv_chang);

        mToolbar.setTitle("个人资料");
        tv_set_name = findViewById(R.id.tv_set_name);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("get_name");
        tv_set_name.setText(user_name);

        UserDaoImpl userDao = new UserDaoImpl(MeProfileActivity.this);
        User user = userDao.queryAllUser().get(0);
        PersonDaoImpl personDao = new PersonDaoImpl(MeProfileActivity.this);
        Person person = personDao.queryById(user.getId());
        tv_dealNum.setText(String.valueOf(person.getDealnum()));
        tv_fanNum.setText(String.valueOf(person.getFansnum()));
        tv_age.setText(String.valueOf(person.getAge()));
        tv_sex.setText(person.getSex());
        tv_email.setText(person.getEmail());
        user_age =tv_age.getText().toString();
        user_sex =tv_sex.getText().toString();
        user_email =tv_email.getText().toString();

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String user_name = tv_set_name.getText().toString();
                intent.putExtra("set_name", user_name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        iv_chang.setOnClickListener(this);

    }

    @Override
    public int getLayout() {
        return R.layout.me_profile_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_chang:{
                Intent intent = new Intent(MeProfileActivity.this, MeChangeProfileActivity.class);
                user_age =tv_age.getText().toString();
                user_sex =tv_sex.getText().toString();
                user_email =tv_email.getText().toString();
                user_name =tv_set_name.getText().toString();
                intent.putExtra("name", user_name);
                intent.putExtra("sex",user_sex);
                intent.putExtra("age", user_age);
                intent.putExtra("email",user_email);
                startActivityForResult(intent, 1);
                break;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnName = data.getStringExtra("returnName");
                    String returnAge =data.getStringExtra("returnAge");
                    String returnSex =data.getStringExtra("returnSex");
                    String returnEmail =data.getStringExtra("returnEmail");

                    tv_set_name.setText(returnName);
                    tv_sex.setText(returnSex);
                    tv_age.setText(returnAge);
                    tv_email.setText(returnEmail);
                    user_age =tv_age.getText().toString();
                    user_sex =tv_sex.getText().toString();
                    user_email =tv_email.getText().toString();
                    user_name =tv_set_name.getText().toString();
                    break;
                }
        }
    }
}
