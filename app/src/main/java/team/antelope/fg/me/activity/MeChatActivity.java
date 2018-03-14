package team.antelope.fg.me.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.PrivateMessageDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.PrivateMessage;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.adapter.ChatListAdapter;
import team.antelope.fg.ui.base.BaseActivity;


public class MeChatActivity extends BaseActivity {

    long sendId;
    String texttitle,title;
    Toolbar mToolbar;
    TextView textView;
    ListView listView;
    EditText inputText;
    Button  btn_send;
    String content;
    PrivateMessage privateMessage;
    ChatListAdapter chatListAdapter;
    PrivateMessageDaoImpl privateMessageDao;
    List<PrivateMessage> privateMessageList;
    List<ImagePicture> imagePictureList =new ArrayList<>();


    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        sendId = getIntent().getLongExtra("sendId", 0l);
        listView = findViewById(R.id.me_chat_list);
        inputText =findViewById(R.id.et_inputText);
        btn_send=findViewById(R.id.bt_send);
        privateMessageDao = new PrivateMessageDaoImpl(this);
        UserDaoImpl dao = new UserDaoImpl(MeChatActivity.this);
        User user = dao.queryAllUser().get(0);
        long userId = user.getId();
        privateMessageList = privateMessageDao.queryAllChatMessage(userId, sendId);

        privateMessage = privateMessageList.get(0);
        mToolbar.setTitle("");
        title = privateMessage.getSenderName();
        textView= (TextView) findViewById(R.id.sender_name);
        textView.setText(title);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initEvent();
        initImagePicture();
        initListView();

    }

    private void initImagePicture() {
//        for (int i = 0; i < 2; i++) {
//            if (sendId==1999l){
//                ImagePicture head1 = new ImagePicture("j",R.drawable.me_user_head2);
//                imagePictureList.add(head1);
//                ImagePicture head2 = new ImagePicture("j",R.drawable.me_user_head2);
//                imagePictureList.add(head2);
//                ImagePicture head3 = new ImagePicture("j",R.drawable.me_user_head2);
//                imagePictureList.add(head3);
//                ImagePicture head4 = new ImagePicture("j",R.drawable.me_user_head2);
//                imagePictureList.add(head4);
//                ImagePicture head5 = new ImagePicture("j",R.drawable.me_user_head2);
//                imagePictureList.add(head5);
//            }
//            if (sendId==2000l){
//                ImagePicture head1 = new ImagePicture("j",R.drawable.me_user_head3);
//                imagePictureList.add(head1);
//                ImagePicture head2 = new ImagePicture("j",R.drawable.me_user_head3);
//                imagePictureList.add(head2);
//                ImagePicture head3 = new ImagePicture("j",R.drawable.me_user_head3);
//                imagePictureList.add(head3);
//                ImagePicture head4 = new ImagePicture("j",R.drawable.me_user_head3);
//                imagePictureList.add(head4);
//                ImagePicture head5 = new ImagePicture("j",R.drawable.me_user_head3);
//                imagePictureList.add(head5);
//            }
//            UserDaoImpl userDao =new UserDaoImpl(MeChatActivity.this);
//            User user = userDao.queryAllUser().get(0);
//        if (sendId==user.getId()){
//            ImagePicture head1 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head1);
//            ImagePicture head2 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head2);
//            ImagePicture head3 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head3);
//            ImagePicture head4 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head4);
//            ImagePicture head5 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head5);
//            ImagePicture head6 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head6);
//            ImagePicture head7 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head7);
//            ImagePicture head8 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head8);
//            ImagePicture head9 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head9);
//            ImagePicture head10 = new ImagePicture("j",R.drawable.me_user_head1);
//            imagePictureList.add(head10);
//        }
//
//        }
    }

    private void initEvent() {

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               content = inputText.getText().toString();

               if(!"".equals(content)){
                   PrivateMessageDaoImpl privateMessageDao = new PrivateMessageDaoImpl(MeChatActivity.this);

                   privateMessage = new PrivateMessage(1050L, 1001L, title, sendId, title,
                               new Date(), content, false);

                       privateMessageDao.insert(privateMessage);
                       privateMessageList.add(privateMessage);

                   chatListAdapter.notifyDataSetChanged();
                   listView.setSelection(privateMessageList.size()-1);
                   inputText.setText("");
               }

            }
        });


    }

    private void initListView() {

        Log.i("debug","666666666");
        chatListAdapter = new ChatListAdapter(this, privateMessageList);
        Log.i("debug","77777777777");
        listView.setAdapter(chatListAdapter);
    }

    @Override
    public int getLayout() {
        return R.layout.me_chat_activity;
    }
}
