package team.antelope.fg.publish.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
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
*@Date: 2017/12/17 23:13
*@Description: Publish
*/

public class PublishFragmentNeed extends BaseFragment {
    ListView lv_need;
    PublishItemsAdapter needItemsAdapter;
    ArrayList<HashMap<String,Object>> listItem;
    PublishNeedDaoImpl publishNeedDao;
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
        lv_need = (ListView) layout.findViewById(R.id.publish_lv);
        setneeditem();
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
        setneeditem();

    }
    public void setneeditem(){
        publishNeedDao=new PublishNeedDaoImpl(getContext());
        List<PublishNeed> publishNeeds=publishNeedDao.queryAllPublishNeed();
        listItem=new  ArrayList<>();
        for (int i = 0; i < publishNeeds.size(); i++) {
            HashMap<String,Object> map=new HashMap<String,Object>();
            Person person=(new PersonDaoImpl(getContext())).queryById(publishNeeds.get(i).getuId());
            L.i("tag","person:"+person.getName());
            map.put("username", person.getName());
            map.put("isonline",publishNeeds.get(i).isOnline());
            map.put("dingwei",publishNeeds.get(i).getAddressDesc());
            map.put("detail",publishNeeds.get(i).getContent());
            map.put("isfinished",publishNeeds.get(i).isComplete());
            map.put("fbtime", DateUtil.formatDate(publishNeeds.get(i).getCustomDate().getTime()));
            listItem.add(map);
        }
        needItemsAdapter= new PublishItemsAdapter(getContext(),listItem,true);
        //setListAdapter(simpleAdapter);
        lv_need.setAdapter(needItemsAdapter);  //为ListView绑定Adapter
        lv_need.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("MyListViewBase","你点击了ListView条目"+position);  //在LogCat中输出信息
            }
        });
    }
}
