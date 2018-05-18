package team.antelope.fg.publish.activity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.antelope.fg.R;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.L;

/**
 * Created by PC_LRY on 2018/1/5.
 */

public class PublishSkillTypeActivity extends BaseActivity {

    Toolbar mToolbar;
    String[] types={"动画制作","UI设计","网页设计/制作","平面设计","视频/后期制作","App开发","程序语言设计","排版设计/制作"};
    @Override
    public int getLayout() {
        return R.layout.publish_select_skill_type;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<Map<String,Object>> items=new ArrayList<>();
        for(int i=0;i<types.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("type",types[i]);
            items.add(map);
        }
        final SimpleAdapter simpleAdapter=new SimpleAdapter(this,items,R.layout.publish_fb_items_type,
                new String[]{"type"},new int[]{R.id.publish_type_item_tv});
        ListView list=findViewById(R.id.publish_skill_type_lv);
        list.setAdapter(simpleAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv=view.findViewById(R.id.publish_type_item_tv);
                L.i("tag","tvText="+tv.getText().toString());
                Intent intent=getIntent();
                Bundle bundle=new Bundle();  //实例化传递的数据包
                bundle.putString("type",tv.getText().toString());
                intent.putExtras(bundle);  //将数据包保存到Intent中
                setResult(0x11,intent);  //设置返回的结果码，并返回调用该Activity的Activity
                finish();  //关闭当前Activity

            }
        });
    }

}
