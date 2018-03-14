package team.antelope.fg.me.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseActivity;

public class MeChangeProfileActivity extends BaseActivity {
   Toolbar mToolbar;
   EditText et_change_name,et_change_age,et_change_sex,et_change_email;
    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        et_change_name=findViewById(R.id.et_change_name);
        et_change_age =findViewById(R.id.et_age);
        et_change_sex=findViewById(R.id.et_sex);
        et_change_email=findViewById(R.id.et_email);
        setSupportActionBar(mToolbar);
         Intent intent =getIntent();
        String user_name = intent.getStringExtra("name");
        String user_sex =intent.getStringExtra("sex");
        String user_age =intent.getStringExtra("age");
        String user_email =intent.getStringExtra("email");
//        UserDaoImpl userDao = new UserDaoImpl(MeChangeProfileActivity.this);
//        User user = userDao.queryAllUser().get(0);
//        PersonDaoImpl personDao = new PersonDaoImpl(MeChangeProfileActivity.this);
//        Person person = personDao.queryById(user.getId());
//        et_change_name.setText(person.getName());
//        et_change_age.setText(String.valueOf(person.getAge()));
//        et_change_sex.setText(person.getSex());
//        et_change_email.setText(person.getEmail());
        et_change_name.setText(user_name);
        et_change_age.setText(user_age);
        et_change_sex.setText(user_sex);
        et_change_email.setText(user_email);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MeChangeProfileActivity.this);
                dialog.setTitle("警告");
                dialog.setMessage("您确认要修改并保存吗");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnData = new Intent();
                        String after_name=  et_change_name.getText().toString();
                        String after_sex=et_change_sex.getText().toString();
                        String after_age= et_change_age.getText().toString();
                        String after_email=et_change_email.getText().toString();

                        UserDaoImpl userDao = new UserDaoImpl(MeChangeProfileActivity.this);
                        User user = userDao.queryAllUser().get(0);
                        PersonDaoImpl personDao = new PersonDaoImpl(MeChangeProfileActivity.this);
                        Person person = personDao.queryById(user.getId());
                        person.setName(after_name);
                        person.setAge(Integer.parseInt(after_age));
                        person.setSex(after_sex);
                        person.setEmail(after_email);
                        user.setName(after_name);
                        user.setEmail(after_email);
                        userDao.update(user);
                        personDao.update(person);

                        returnData.putExtra("returnName",after_name);
                        returnData.putExtra("returnAge",after_age);
                        returnData.putExtra("returnSex",after_sex);
                        returnData.putExtra("returnEmail",after_email);
                        setResult(RESULT_OK,returnData);
                        finish();
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.me_change_profile_activity;
    }
}
