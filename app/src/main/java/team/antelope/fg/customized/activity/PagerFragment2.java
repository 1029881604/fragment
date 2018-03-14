package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.customized.adapter.DzRecyclerAdapter;
import team.antelope.fg.db.dao.impl.CompleteCustomDaoImpl;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.PublishSkillDaoImpl;
import team.antelope.fg.entity.CompleteCustom;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;
import team.antelope.fg.util.ToastUtil;

/**
 * Created by Kyrene on 2017/12/18.
 */

public class PagerFragment2 extends BaseFragment implements View.OnClickListener {

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
    protected void initView(View view, Bundle savedInstanceState) {
        //列表控件
        mRecyclerView=view.findViewById(R.id.recyclerView);
        publishSkillDao=new PublishSkillDaoImpl(getContext());
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
            if ((publishSkills.get(i).getSkillType().equals("UI设计"))&&!publishSkills.get(i).isOnline()&&
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

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置RecyclerView布局管理器为2列垂直排布
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        adapter = new DzRecyclerAdapter(getActivity(),lists,resids);
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
                intent.setClass(getActivity(),SkillDetails.class);  //指定传递对象
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
    protected int getLayoutId() {
        return R.layout.lx_pager_fragment1;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        initDate();
        initEvent();
//        initRecycle();
    }

    private void initEvent() {

    }

    private void initDate() {

    }

//    private void initRecycle(){
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        //设置RecyclerView布局管理器为2列垂直排布
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//
//        adapter = new DzRecyclerAdapter(getActivity(),lists,resids);
//        mRecyclerView.setAdapter(adapter);
//        adapter.setOnClickListener(new DzRecyclerAdapter.OnItemClickListener() {
//            //单击事件
//            @Override
//            public void ItemClickListener(View view, int postion) {
//                Intent intent=new Intent();
//                intent.putExtra("title",pub);
//                intent.setClass(getActivity(),SkillDetails.class);  //指定传递对象
//                startActivity(intent);
////                ToastUtil.showCustom(getActivity().getApplicationContext(),"点击了："+postion, 2000);
//            }
//            //长按事件
//            @Override
//            public void ItemLongClickListener(View view, int postion) {
//                //长按删除
////                lists.remove(postion);
////                adapter.notifyItemRemoved(postion);
//            }
//        });
//    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
//            case R.id.button:
//                ToastUtil.showCustom(getmActivity().getApplicationContext(), "ttt", 2000);
//                break;
        }
    }
}
