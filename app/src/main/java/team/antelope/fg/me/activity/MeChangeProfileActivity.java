package team.antelope.fg.me.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Properties;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PrivateMessage;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.PropertiesUtil;

public class MeChangeProfileActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private EditText et_change_name, et_change_email,et_change_age;
    private String after_name;
    private String after_sex;
    private String after_age;
    private String after_email;
    private Long user_id;
    private Properties mProp;
    private TextView  tv_change_sex;
    private PopupWindow sexPopWindow;
    private PopupWindow agePopWindow;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        et_change_name = findViewById(R.id.et_change_name);
        et_change_email = findViewById(R.id.et_email);
        et_change_age = findViewById(R.id.et_age);
        tv_change_sex = findViewById(R.id.tv_sex);
        setSupportActionBar(mToolbar);
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("name");
        String user_sex = intent.getStringExtra("sex");
        String user_age = intent.getStringExtra("age");
        String user_email = intent.getStringExtra("email");
        et_change_name.setText(user_name);
        et_change_age.setText(user_age);
        tv_change_sex.setText(user_sex);
        et_change_email.setText(user_email);
        tv_change_sex.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MeChangeProfileActivity.this);
                dialog.setTitle("警告");
                dialog.setMessage("您确认要修改并保存吗");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    /**
                     * @Author：Carlos
                     * @Date: 2018/4/7 9:33
                     * @Description:提示窗
                     **/
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnData = new Intent();
                        after_name = et_change_name.getText().toString();
                        after_sex = tv_change_sex.getText().toString();
                        after_age = et_change_age.getText().toString();
                        after_email = et_change_email.getText().toString();
                        mProp = PropertiesUtil.getInstance();
                        doDatabase();
                        sendRequestWithOkHttp();
                        returnData.putExtra("returnName", after_name);
                        returnData.putExtra("returnAge", after_age);
                        returnData.putExtra("returnSex", after_sex);
                        returnData.putExtra("returnEmail", after_email);
                        setResult(RESULT_OK, returnData);
                        finish();
                    }

                    /**
                     * @Author：Carlos
                     * @Date: 2018/4/7 18:20
                     * @Description: 数据库处理
                     **/
                    private void doDatabase() {
                        UserDaoImpl userDao = new UserDaoImpl(MeChangeProfileActivity.this);
                        User user = userDao.queryAllUser().get(0);
                        PersonDaoImpl personDao = new PersonDaoImpl(MeChangeProfileActivity.this);
                        user_id = user.getId();
                        Person person = personDao.queryById(user_id);
                        person.setName(after_name);
                        person.setAge(Integer.parseInt(after_age));
                        person.setSex(after_sex);
                        person.setEmail(after_email);
                        user.setName(after_name);
                        user.setEmail(after_email);
                        userDao.update(user);
                        personDao.update(person);
                    }


                    /**
                     * @Author：Carlos
                     * @Date: 2018/4/7 9:32
                     * @Description: 通过OkHttp请求连接
                     **/
                    private void sendRequestWithOkHttp() {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String url = null;
                                try {
                                          url = mProp.getProperty(AccessNetConst.BASEPATH)+
                                                  mProp.getProperty(AccessNetConst.CHANGEPROFILESERVLEENDTPATH);
//                                    url="http://192.168.1.110:8080/ServletPractice/test01";
//                                    url = "http://192.168.1.110:8080/fragment_server/ChangeProfileServlet";
                                    OkHttpClient client = new OkHttpClient();
                                    RequestBody requestBody = new FormBody.Builder()
                                            .add("after_name", after_name)
                                            .add("after_sex", after_sex)
                                            .add("after_age", after_age)
                                            .add("after_email", after_email)
                                            .add("user_id", String.valueOf(user_id))
                                            .build();
                                    Request request = new Request.Builder()
                                            .url(url)
                                            .post(requestBody)
                                            .build();
                                    Response response = client.newCall(request).execute();
                                    String responseData = response.body().string();
                                    showResponse(responseData);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            private void showResponse(String responseData) {
                                if (responseData != null) {
                                    Log.i("666", "成功");
                                } else
                                    Log.i("666", "失败");
                            }
                        }).start();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_age:
                break;
            case R.id.tv_sex:
                showSexPopupWindow();
                break;
            case R.id.btn_pop_male:{
                tv_change_sex.setText("男");
                sexPopWindow.dismiss();
            }
            break;
            case R.id.btn_pop_female:{
                tv_change_sex.setText("女");
                sexPopWindow.dismiss();
            }
            break;
            case R.id.btn_pop_secret:{
                //选择行的位置
                tv_change_sex.setText("保密");
                tv_change_sex.invalidate();
                sexPopWindow.dismiss();
            }
            break;
        }
    }

    private void showSexPopupWindow() {
        View contentView = LayoutInflater.from(MeChangeProfileActivity.this).inflate(R.layout.me_sex_popupwindow, null);
         sexPopWindow = new PopupWindow(contentView);
        sexPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        sexPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btn1 = (Button) contentView.findViewById(R.id.btn_pop_male);
        Button btn2 = (Button) contentView.findViewById(R.id.btn_pop_female);
        Button btn3 = (Button) contentView.findViewById(R.id.btn_pop_secret);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        //外部是否可以点击
        sexPopWindow.setBackgroundDrawable(new BitmapDrawable());
        sexPopWindow.setOutsideTouchable(true);
        //各ITEM点击响应
        sexPopWindow.showAsDropDown(tv_change_sex);


    }
}
