package team.antelope.fg.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import team.antelope.fg.R;
import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.util.SetRoundImageViewUtil;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Carlos on 2017/12/5.
 **/

public class MeFragment extends BaseFragment implements View.OnClickListener {
    @Nullable
    private TextView tv_message,tv_follow,tv_collecion,
            tv_myneed,tv_freetime,tv_making,tv_setting,tv_name;
    private LinearLayout layout_user_profile;
    private DBOpenHelper dbOpenHelper;
    SetRoundImageViewUtil setRoundImageViewUtil;

    @Override
    protected void initView(View layout, Bundle savedInstanceState) {
        tv_message = layout.findViewById(R.id.me_message);
        tv_follow = layout.findViewById(R.id.me_follow);
        tv_collecion = layout.findViewById(R.id.me_collection);
        tv_myneed = layout.findViewById(R.id.me_my_need);
        tv_freetime = layout.findViewById(R.id.me_free_time);
        tv_making = layout.findViewById(R.id.me_making);
        tv_setting= layout.findViewById( R.id.me_my_setting);
        tv_name=layout.findViewById(R.id.tv_name);
        layout_user_profile= layout.findViewById(R.id.lay_view_user);
        /*设置圆形头像*/
        setRoundImageViewUtil = layout.findViewById(R.id.me_user_head);
        setRoundImageViewUtil.setImageResource(R.drawable.me_user_head1);
        dbOpenHelper =new DBOpenHelper(getActivity(),"my.db",null,1);
        Log.d("debug","111111111");
        initLayoutView();
    }

    private void initLayoutView() {
        UserDaoImpl userDao = new UserDaoImpl(getmActivity());
        User user = userDao.queryAllUser().get(0);
        Person person = new PersonDaoImpl(getmActivity()).queryById(user.getId());
        tv_name.setText(person.getName());
    }







    @Override
    protected int getLayoutId() {
        return R.layout.mefragment_layout;
    }


    @Override
    protected void init() {
        setOnListener();
    }

    private void setOnListener() {
        layout_user_profile.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        tv_making.setOnClickListener(this);
        tv_collecion.setOnClickListener(this);
        tv_follow.setOnClickListener(this);
        tv_myneed.setOnClickListener(this);
        tv_freetime.setOnClickListener(this);
        tv_setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_view_user:// 个人信息
                String beforename =tv_name.getText().toString();
                Intent intent =new Intent(getActivity(),MeProfileActivity.class);
            intent.putExtra("get_name",beforename);
            startActivityForResult(intent,1);
                break;
            case R.id.me_message:// 消息
            startActivity(new Intent(getActivity(), MessageListActivity.class));
                break;
            case R.id.me_collection://收藏
                startActivity(new Intent(getActivity(), MeCollectionActivity.class));
                break;
            case R.id.me_follow://关注
                startActivity(new Intent(getActivity(), MeFollowActivity.class));
                break;
//            case R.id.me_making://我的定制
//                startActivity(new Intent(getActivity(), MePublicActivity.class));
//                break;
//            case R.id.me_my_need://我的需求
//                startActivity(new Intent(getActivity(), MePublicActivity.class));
//                break;
//
//            case R.id.me_free_time://空闲时间
//                startActivity(new Intent(getActivity(), MePublicActivity.class));
//                break;
            case R.id.me_my_setting://设置
                startActivity(new Intent(getActivity(),MeSettingActivity.class));
                break;

            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode== RESULT_OK){
                    String returnName= data.getStringExtra("set_name");
                    tv_name.setText(returnName);
                }
                break;
                default:
        }
    }
}


