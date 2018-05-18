package team.antelope.fg.me.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;
import team.antelope.fg.R;
import team.antelope.fg.common.GlideApp;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.me.fragment.MeMomentFragment;
import team.antelope.fg.me.fragment.MeProfileFragment;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.CircleImageViewUtil;
import team.antelope.fg.util.SetRoundImageViewUtil;

/**
 * @Author：Carlos
 * @Date： 2018/5/16 11:45
 * @Description: 其他用户的资料
 **/
public class MePersonActivity extends BaseActivity implements View.OnClickListener{
    Toolbar mToolbar;
    TextView tv_name,tv_sex,tv_age,tv_email,tv_dealNum,tv_fanNum;
    long personId;
    List<Person> personList;
    CircleImageViewUtil circleImageViewUtil;
    TextView tv_fan;//粉丝列表按钮
    TextView tv_follow;//关注列表按钮
    FragmentPagerItemAdapter adapter;
    SmartTabLayout viewPagerTab; //Fragment的View加载完毕的标记
    ViewPager viewPager;
    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        circleImageViewUtil= (CircleImageViewUtil) findViewById(R.id.iv_user_head);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_email = (TextView) findViewById(R.id.tv_email);
        viewPager =  findViewById(R.id.vp_profile);
        viewPagerTab = findViewById(R.id.ly_vp_tab);
        tv_fan = (TextView) findViewById(R.id.tv_fan);
        tv_follow = (TextView) findViewById(R.id.tv_follow);
//        layout_fans = (LinearLayout) findViewById(R.id.layout_fans);
//        layout_follow = (LinearLayout) findViewById(R.id.layout_follow);
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

        tv_fan.setOnClickListener(this);
        tv_follow.setOnClickListener(this);
        viewPagerEvent();

    }
    private void viewPagerEvent() {
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Fragment page = adapter.getPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
//        adapter = new ViewPagerItemAdapter(
//                ViewPagerItems.with(this)
//                .add("动态", R.layout.me_moment_fragment)
//                .add("关于TA",R.layout.me_profile_fragment)
//                .create());
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("动态", MeMomentFragment.class)
                .add("关于TA", MeProfileFragment.class)
                .create());
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
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
            case R.id.tv_fan: {
                Intent intent1 = new Intent(MePersonActivity.this, MeFansListActivity.class);
                intent1.putExtra("userId", personId);
                startActivity(intent1);
                break;
            }
            case R.id.tv_follow: {
                Intent intent2 = new Intent(MePersonActivity.this, MeFanFollowActivity.class);
                intent2.putExtra("userId", personId);
                startActivity(intent2);
                break;
            }
            default:
                break;
        }
    }
}
