package team.antelope.fg.me.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.PrivateMessageDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.PrivateMessage;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.adapter.MessageListAdapter;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.L;
import team.antelope.fg.util.SetRoundImageViewUtil;
import team.antelope.fg.util.ToastUtil;

public class MessageListActivity extends BaseActivity implements View.OnClickListener {
    Toolbar mToolbar;
    ListView lv_message;    //消息列表
    MessageListAdapter messageListAdapter;  //列表adapter
    PrivateMessageDaoImpl privateMessageDao;    //消息访问实例
    List <PrivateMessage> list;             // message数据集合
    List<Map<String, Object>> listMap2; // 封装message的数据结婚
    RelativeLayout relativeLayout;
    ImageView me_message_head;
    TextView sys_date;
    TextView sys_content;
    TextView sys_red_point;
    SetRoundImageViewUtil setRoundImageViewUtil;
    List<ImagePicture> imagePictureList = new ArrayList<>();//图片
    private PopupWindow mPopWindow;
    int mPosition;              // 消息在listview里面的位置
    long senderId;



    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("消息");
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        L.i("AAA", "111111111");
        lv_message = (ListView) findViewById(R.id.me_message_list);
        relativeLayout = (RelativeLayout)findViewById(R.id.rl_layout_sys_message);
        sys_date =(TextView)findViewById(R.id.tv_me_date);
        sys_content=(TextView)findViewById(R.id.tv_me_message);
        sys_red_point=(TextView)findViewById(R.id.tv_me_msgnum);
        me_message_head=(ImageView) findViewById(R.id.me_message_head);
        privateMessageDao = new PrivateMessageDaoImpl(this);

        initListView();
        initsystemEvent();
        initEvent();
    }

    private void initsystemEvent() {

        sys_date.setText("2015年10月1日");
        sys_content.setText("有新的用户关注你");
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sys_red_point.setVisibility(View.GONE);

            }
        });



    }

    private void initEvent() {
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView tv_senderid_hidden_me = view.findViewById(R.id.tv_senderid_hidden_me);
                String sendId = tv_senderid_hidden_me.getText().toString();
                List<PrivateMessage> privateMessages = privateMessageDao.queryBySenderId(Long.parseLong(sendId));
                for (int i = 0; i < privateMessages.size(); i++) {
                    PrivateMessage privateMessage = privateMessages.get(i);
                    privateMessage.setRead(true);
                    privateMessageDao.update(privateMessage);
                }
                Intent intent = new Intent(MessageListActivity.this, MeChatActivity.class);
                intent.putExtra("sendId", Long.parseLong(sendId));
                startActivity(intent);
                initListView();
            }
        });
           lv_message.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
               @Override
               public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                   TextView hide = view.findViewById(R.id.tv_senderid_hidden_me);
                   String hideText = (String) hide.getText();
                   senderId = Integer.parseInt(hideText);
                   mPosition = position;
                   showPopupWindow();
                   return true;
               }
           });
    }
    private void showPopupWindow() {
        View contentView = LayoutInflater.from(MessageListActivity.this).inflate(R.layout.me_popupwindow, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        Button tv1 = (Button)contentView.findViewById(R.id.btn_pop_up);
        Button tv2 = (Button)contentView.findViewById(R.id.btn_pop_is_read);
        Button tv3 = (Button)contentView.findViewById(R.id.btn_pop_delete);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        //外部是否可以点击
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        //各ITEM点击响应
        mPopWindow.showAsDropDown(lv_message);
    }


    private void initListView() {
        List<PrivateMessage> privateMessages = privateMessageDao.queryAllPrivateMessage();
        List<Map<String, Object>> listMap1 = new ArrayList<Map<String, Object>>();
        UserDaoImpl dao = new UserDaoImpl(MessageListActivity.this);
        User user = dao.queryAllUser().get(0);
        long userId = user.getId();
        for (int i = 0; i < privateMessages.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("privateMessage", privateMessages.get(i));
            map.put("msgnum", new Integer(1));
            listMap1.add(map);
        }
        //去掉重复的人的消息，把他发的最后一条消息的content，time，name, num设置
        listMap2 = new ArrayList<Map<String, Object>>();
        boolean same = false;
        L.i(listMap1.size()+"----size");
        for (int i = 0; i < listMap1.size() ; i++) {
            PrivateMessage privateMessage = (PrivateMessage) listMap1.get(i).get("privateMessage");
            long id = privateMessage.getSenderId();
            if (id == userId){
                break;
            }
            for (int j = 0; j < listMap2.size(); j++) {
                PrivateMessage privateMessage1 = (PrivateMessage) listMap2.get(j).get("privateMessage");
                if (privateMessage1 == null){
                    break;
                }
                if(privateMessage1.getSenderId() == id ){
                    same = true;
                    privateMessage1.setContent(privateMessage.getContent());
                    privateMessage1.setSendTime(privateMessage.getSendTime());
                    Integer msgnum = (Integer) listMap2.get(j).get("msgnum");
                    listMap2.get(j).put("msgnum", ++ msgnum);
                }
            }
            if (! same){
                L.i(""+listMap1.get(i).get("privateMessage").toString());
                listMap2.add(listMap1.get(i));
            }
            same = false;
        }
        for (int i = 0; i < 2; i++) {
            ImagePicture head1 = new ImagePicture("j",R.drawable.me_user_head2);
            imagePictureList.add(head1);
            ImagePicture head2 = new ImagePicture("j",R.drawable.me_user_head4);
            imagePictureList.add(head2);
//            ImagePicture head3 = new ImagePicture(R.drawable.me_user_head1);
//            imagePictureList.add(head3);
//            ImagePicture head4 = new ImagePicture(R.drawable.me_user_head1);
//            imagePictureList.add(head4);

        }
        messageListAdapter = new MessageListAdapter(this, listMap2,imagePictureList);
        lv_message.setAdapter(messageListAdapter);
    }
    @Override
    public int getLayout() {
        return R.layout.me_message_activity;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_pop_up:{
                Toast.makeText(this, "clicked computer", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
            case R.id.btn_pop_is_read:{

                mPopWindow.dismiss();
            }
            break;
            case R.id.btn_pop_delete:{
                //选择行的位置
                List<PrivateMessage> messages = privateMessageDao.queryBySenderId(senderId);
                for (int i = 0; i < messages.size(); i++) {
                    PrivateMessage message = privateMessageDao.queryById(messages.get(i).getId());
                    privateMessageDao.delete(message);
                }
                listMap2.remove(mPosition);
                messageListAdapter.notifyDataSetChanged();
                lv_message.invalidate();
                mPopWindow.dismiss();
            }
            break;
        }
    }

}
