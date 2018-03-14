package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.common.GlideApp;
import team.antelope.fg.customized.adapter.DzRecyclerAdapter;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.PublishSkillDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.SetRoundImageViewUtil;

/**
 * Created by Kyrene on 2017/12/18.
 */

public class PersonDetails extends BaseActivity{

    Toolbar mToolbar;
    Bitmap bitmap1,bitmap2;
    TextView personName;
    TextView personSex;
    TextView personAge;
    TextView personEmail;
    TextView PersonDealNum;
    TextView PersonFansNum;
    SetRoundImageViewUtil setRoundImageViewUtil;

    private RecyclerView mRecyclerView;
    private DzRecyclerAdapter adapter;
    private List<String> lists; //标题集合
    private List<Integer>  resids;  //图片集合
    private List<String> contents;  //内容介绍集合
    private List<String> type;  //技能类型集合
    private List<String> startdate; //开始时间集合
    private List<String> stopdate;  //结束时间集合
    private List<Long> userid;    //用户ID集合
    private PublishSkillDaoImpl publishSkillDao;

    @Override
    protected void initView(Bundle savedInstanceState) {

        mToolbar = (Toolbar) findViewById(R.id.toolbarPerson);
        setRoundImageViewUtil=findViewById(R.id.iv_user_head);

        personName =findViewById(R.id.tv_name);
        personSex =findViewById(R.id.tv_sex);
        personAge =findViewById(R.id.tv_age);
        personEmail =findViewById(R.id.tv_email);
        PersonDealNum=findViewById(R.id.tv_dealNum);
        PersonFansNum =findViewById(R.id.tv_fanNum);
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

        initLayoutView();
        initRecyclerView();
    }

    /**
    * @说明 人物信息
    * @创建日期 2018/1/8 上午6:01
    */
    private void initLayoutView() {

        long personId = getIntent().getLongExtra("person_id",0l);
        PersonDaoImpl personDao = new PersonDaoImpl(this);
        Person person =personDao.queryById(personId);

        mToolbar.setTitle(person.getName());
        personName.setText(person.getName());
        personSex.setText(person.getSex());
        personAge.setText(String.valueOf(person.getAge()) );
        personEmail.setText(person.getEmail());
        PersonDealNum.setText(String.valueOf(person.getDealnum()));
        PersonFansNum.setText(String.valueOf(person.getFansnum()) );

        RequestOptions options = new RequestOptions();
        GlideApp.with(PersonDetails.this)
                .load(person.getHeadImg())
                .placeholder(R.mipmap.default_avatar400)
                .error(R.drawable.ic_launcher_round)
                .apply(options)
                .into(setRoundImageViewUtil);

    }

    /**
    * @说明 利用recyclerView显示与人物相关的信息
    * @创建日期 2018/1/8 上午6:04
    */
    private void initRecyclerView(){

        long personId = getIntent().getLongExtra("person_id",0l);
        mRecyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        publishSkillDao=new PublishSkillDaoImpl(this);
        final List<PublishSkill> publishSkills=publishSkillDao.queryAllPublishSkill();

        //文字信息集合
        lists = new ArrayList();
        //内容介绍信息集合
        contents = new ArrayList();
        //技能类型信息集合
        type = new ArrayList();
        //开始时间信息集合
        startdate = new ArrayList();
        //结束时间信息集合
        stopdate = new ArrayList();
        //用户ID集合
        userid = new ArrayList();
        //资源id集合
        resids = new ArrayList();

        for (int i=0; i<publishSkills.size(); i++) {

            String transmitUserid=String.valueOf(personId); //long转String
            String dbUserid=String.valueOf(publishSkills.get(i).getuId());  //long转String

            if ((dbUserid.equals(transmitUserid))&&!publishSkills.get(i).isOnline()&&
                    !publishSkills.get(i).isComplete()
                    ){
                lists.add(publishSkills.get(i).getTitle());
                contents.add(publishSkills.get(i).getContent());
                type.add(publishSkills.get(i).getSkillType());
                startdate.add(DateUtil.formatDate(publishSkills.get(i).getPublishDate().getTime()));
                stopdate.add(DateUtil.formatDate(publishSkills.get(i).getStopDate().getTime()));
                userid.add(publishSkills.get(i).getuId());
            }
        }

        resids.add(R.drawable.lx_fgpic1);
        resids.add(R.drawable.lx_fgpic2);
        resids.add(R.drawable.lx_fgpic3);
        resids.add(R.drawable.lx_fgpic4);
        resids.add(R.drawable.lx_fgpic5);
        resids.add(R.drawable.lx_fgpic1);
        resids.add(R.drawable.lx_fgpic2);
        resids.add(R.drawable.lx_fgpic3);
        resids.add(R.drawable.lx_fgpic4);
        resids.add(R.drawable.lx_fgpic5);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置RecyclerView布局管理器为2列垂直排布
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        adapter = new DzRecyclerAdapter(this,lists,resids);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new DzRecyclerAdapter.OnItemClickListener() {
            //单击事件
            @Override
            public void ItemClickListener(View view, int postion) {
                Intent intent=new Intent();
                intent.putExtra("title",lists.get(postion));
                intent.putExtra("contents",contents.get(postion));
                intent.putExtra("skilltype",type.get(postion));
                intent.putExtra("startdate",startdate.get(postion));
                intent.putExtra("stopdate",stopdate.get(postion));
                intent.putExtra("userid",userid.get(postion));
                intent.setClass(PersonDetails.this,SkillDetails.class);  //指定传递对象
                startActivity(intent);
//                ToastUtil.showCustom(getActivity().getApplicationContext(),"点击了："+postion, 2000);
            }
            //长按事件
            @Override
            public void ItemLongClickListener(View view, int postion) {
                //长按删除
//                lists.remove(postion);
//                adapter.notifyItemRemoved(postion);
            }
        });

    }

    
    @Override
    public int getLayout() {
        return R.layout.lx_persondetails;
    }
}
