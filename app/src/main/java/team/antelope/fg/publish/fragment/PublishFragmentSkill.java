package team.antelope.fg.publish.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.PublishNeedDaoImpl;
import team.antelope.fg.db.dao.impl.PublishSkillDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.publish.adapter.PublishItemsAdapter;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;

/**
*@Author: lry
*@Date: 2017/12/17 22:56
*@Description: 发布技能fragment
*/

public class PublishFragmentSkill extends BaseFragment {
    ListView lv_skill;
    PublishItemsAdapter skillItemsAdapter;
    ArrayList<HashMap<String,Object>> listItem;
    PublishSkillDaoImpl publishSkillDao;
    @Override
    protected int getLayoutId() {
        return R.layout.publish_fragment_listview;
    }
    /**
     *@Description: 初始化界面操作
     *@Date: 2017/12/26 18:25
     */
    @Override
    protected void initView(View layout, Bundle savedInstanceState) {
        lv_skill = (ListView) layout.findViewById(R.id.publish_lv);
        setskillitem();
    }
    /**
     *@Description: 初始化视图处理事件
     *@Date: 2017/12/26 18:26
     */
    @Override
    protected void init() {
    }
    @Override
    public void onResume() {
        super.onResume();
        setskillitem();

    }
    public void setskillitem(){
        publishSkillDao=new PublishSkillDaoImpl(getContext());
        List<PublishSkill> publishSkills=publishSkillDao.queryAllPublishSkill();
        listItem=new  ArrayList<>();
        for (int i = 0; i < publishSkills.size(); i++) {
            HashMap<String,Object> map=new HashMap<String,Object>();
            Person person=(new PersonDaoImpl(getContext())).queryById(publishSkills.get(i).getuId());
            if (person == null){
                break;
            }
            map.put("username", person.getName());
            map.put("isonline",publishSkills.get(i).isOnline());
            map.put("dingwei",publishSkills.get(i).getAddressDesc());
            map.put("detail",publishSkills.get(i).getContent());
            map.put("fbtime", DateUtil.formatDate(publishSkills.get(i).getPublishDate().getTime()));
            listItem.add(map);
        }
        skillItemsAdapter= new PublishItemsAdapter(getContext(),listItem,false);
        //setListAdapter(simpleAdapter);
        lv_skill.setAdapter(skillItemsAdapter);  //为ListView绑定Adapter
        skillItemsAdapter.notifyDataSetChanged();
        lv_skill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Publishskill","你点击了ListView条目"+position);  //在LogCat中输出信息
            }
        });
    }
}
