package team.antelope.fg.me.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.AttentionDaoImpl;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.PrivateMessageDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.adapter.MeFollowListAdapter;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.SetRoundImageViewUtil;


public class MeFollowActivity extends BaseActivity {
    Toolbar mToolbar;
    private HashMap<String, Integer> selector;// 存放含有索引字母的位置
    TextView indexTv, tv_follow_name, tv_show;
    ImageView iv_follow_user_head;
    ImageView iv_send_message;
    ListView listView;
    LinearLayout layoutIndex;
    MeFollowListAdapter meFollowListAdapter;
    List<Person> personList;
    List<ImagePicture> imagePictureList = new ArrayList<>();
    private String[] indexStr = {"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private int height;// 字体高度
    private boolean flag = false;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("关注");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        indexTv = findViewById(R.id.indexTv);
        tv_follow_name = findViewById(R.id.tv_follow_name);
        iv_follow_user_head = findViewById(R.id.iv_follow_user_head);
        listView = findViewById(R.id.listView);
        layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
        layoutIndex.setBackgroundColor(Color.parseColor("#00ffffff"));
        tv_show = (TextView) findViewById(R.id.tv);
        tv_show.setVisibility(View.GONE);
        initListView();
        initListEvent();
        meFollowListAdapter = new MeFollowListAdapter(this, personList, imagePictureList);
        listView.setAdapter(meFollowListAdapter);


    }

    private void initListEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_personId = view.findViewById(R.id.tv_personId);
                String personId = tv_personId.getText().toString();
                Intent intent =new Intent(MeFollowActivity.this,MePersonActivity.class);
                intent.putExtra("person_id",Long.parseLong(personId));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!flag) {
            height = layoutIndex.getMeasuredHeight() / indexStr.length;
            getIndexView();
            flag = true;
        }
    }

    private void initListView() {
        User user = new UserDaoImpl(this).queryAllUser().get(0);
        AttentionDaoImpl attentionDao = new AttentionDaoImpl(this);
        personList = attentionDao.findFriends(user.getId());



        selector = new HashMap<String, Integer>();
        for (int j = 0; j < indexStr.length; j++) {// 循环字母表，找出newPersons中对应字母的位置
            for (int i = 0; i < personList.size(); i++) {
                if (personList.get(i).getName().equals(indexStr[j])) {
                    selector.put(indexStr[j], i);
                }
            }

        }
        for (int i = 0; i < 2; i++) {
            ImagePicture head1 = new ImagePicture("j",R.drawable.me_user_head5);
            imagePictureList.add(head1);
            ImagePicture head2 = new ImagePicture("j",R.drawable.me_user_head3);
            imagePictureList.add(head2);
            ImagePicture head3 = new ImagePicture("j",R.drawable.me_user_head4);
            imagePictureList.add(head3);
            ImagePicture head4 = new ImagePicture("j",R.drawable.me_user_head1);
            imagePictureList.add(head4);

        }
    }


    @Override
    public int getLayout() {
        return R.layout.me_follow_activity;
    }

    /**
     * 绘制索引列表
     */
    public void getIndexView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.length; i++) {
            final TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(indexStr[i]);
            tv.setPadding(10, 0, 10, 0);
            layoutIndex.addView(tv);
            layoutIndex.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event)

                {
                    float y = event.getY();
                    int index = (int) (y / height);
                    if (index > -1 && index < indexStr.length) {// 防止越界
                        String key = indexStr[index];
                        if (selector.containsKey(key)) {
                            int pos = selector.get(key);
                            if (listView.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
                                listView.setSelectionFromTop(
                                        pos + listView.getHeaderViewsCount(), 0);
                            } else {
                                listView.setSelectionFromTop(pos, 0);// 滑动到第一项
                            }
                            tv_show.setVisibility(View.VISIBLE);
                            tv_show.setText(indexStr[index]);
                        }
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layoutIndex.setBackgroundColor(Color
                                    .parseColor("#606060"));
                            break;

                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:
                            layoutIndex.setBackgroundColor(Color
                                    .parseColor("#00ffffff"));
                            tv_show.setVisibility(View.GONE);
                            break;
                    }
                    return true;
                }
            });
        }
    }

}
