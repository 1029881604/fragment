package team.antelope.fg.me.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.CircleImageViewUtil;

import static android.media.CamcorderProfile.get;

public class MeProfileActivity extends BaseActivity implements View.OnClickListener {

     Toolbar mToolbar;
     ImageView iv_chang;
    TextView tv_set_name, tv_age, tv_sex, tv_email, tv_dealNum, tv_fanNum;
   private String user_name;
   private String user_age,user_sex,user_email;
    private CircleImageViewUtil iv_user_head;
     private final static int CHOOSE_PHOTO= 2;
     private LinearLayout layout_fans;//粉丝
    private LinearLayout layout_moments;//动态
    private Long id;


    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_sex = findViewById(R.id.tv_sex);
        tv_age = findViewById(R.id.tv_age);
        tv_email = findViewById(R.id.tv_email);
        tv_dealNum = findViewById(R.id.tv_dealNum);
        tv_fanNum = findViewById(R.id.tv_fanNum);
        iv_chang =findViewById(R.id.iv_chang);
        iv_user_head = findViewById(R.id.iv_user_head);
        mToolbar.setTitle("个人资料");
        tv_set_name = findViewById(R.id.tv_set_name);
        layout_fans =findViewById(R.id.layout_fans);
        layout_moments = findViewById(R.id.layout_moments);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("get_name");
        tv_set_name.setText(user_name);

        UserDaoImpl userDao = new UserDaoImpl(MeProfileActivity.this);
        User ur = userDao.queryAllUser().get(0);
        id = ur.getId();
        //先判断userList是否为null或者没有元素
        List<User> userList = userDao.queryAllUser();
        User user = null;
        if(userList !=null && !userList.isEmpty()){
            user = userList.get(0);
        } else{
            return;
        }
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
//        RequestOptions options = new RequestOptions();
//        GlideApp.with(MeProfileActivity.this)
//                .load(person.getHeadImg())
//                .placeholder(R.mipmap.default_avatar400)
//                .error(R.mipmap.error400)
//                .apply(options)
//                .into(iv_user_head);

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
        iv_user_head.setOnClickListener(this);
        layout_fans.setOnClickListener(this);

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
            case  R.id.iv_user_head:{
                //同样new一个file用于存放照片
                File imageFile = new File(Environment
                        .getExternalStorageDirectory(), "outputImage.jpg");
                if (imageFile.exists()) {
                    imageFile.delete();
                }
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
                //转换成Uri
                Uri imageUri = Uri.fromFile(imageFile);
                //开启选择呢绒界面
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                //设置可以缩放
                intent.putExtra("scale", true);
                //设置可以裁剪
                intent.putExtra("crop", true);
                intent.setType("image/*");
                //设置输出位置
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //开始选择
                startActivityForResult(intent, CHOOSE_PHOTO);
                break;
            }
            case R.id.layout_fans:
                Intent intent = new Intent(MeProfileActivity.this,MeFansListActivity.class);
                intent.putExtra("userId",id);
                startActivity(intent);
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
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    handleImageOnKitkat(data);
                }
                break;
        }
    }

    private void handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri
                    .getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri
                    .getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果不是document类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        displayImage(imagePath); // 根据图片路径显示图片
        System.err.println(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            
            iv_user_head.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT)
                    .show();
        }

    }


}
