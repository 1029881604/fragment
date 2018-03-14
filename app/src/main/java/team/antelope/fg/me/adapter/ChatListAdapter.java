package team.antelope.fg.me.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.PrivateMessage;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.activity.ImagePicture;
import team.antelope.fg.util.DateUtil;

/**
 * Created by Carlos on 2017/12/17.
 */

public class ChatListAdapter extends BaseAdapter {
    private List<PrivateMessage> mList;
    private LayoutInflater mInflater;
    private  Context mcontext;



    public ChatListAdapter(Context context, List<PrivateMessage> list ){
        this.mInflater=LayoutInflater.from(context);
        this.mcontext=context;
        mList = list;
        System.out.println("3friends_size:"+list.size());
    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     ViewHolder holder;
     if (convertView == null){
         convertView =mInflater.inflate(R.layout.me_chat_items,null);
         holder =new ViewHolder();

         Log.i("debug","888888888");
         holder.content_left=(TextView) convertView.findViewById(R.id.tv_me_left_message);
         holder.date=(TextView) convertView.findViewById(R.id.tv_me_date);
         holder.content_right=convertView.findViewById(R.id.tv_me_right_message);
         holder.left_layout=convertView.findViewById(R.id.left_chat_me);
         holder.right_layout=convertView.findViewById(R.id.right_chat_me);
//         holder.iv_left_image =convertView.findViewById(R.id.iv_left_image);
//         holder.iv_right_image=convertView.findViewById(R.id.iv_right_image);
         convertView.setTag(holder);
     }
     else {
         holder =(ViewHolder)convertView.getTag();

     }
        PrivateMessage privateMessage = mList.get(position);
        UserDaoImpl userDao =new UserDaoImpl(mcontext);
        User user = userDao.queryAllUser().get(0);


        Long sendId = privateMessage.getSenderId();
        if (!sendId.equals(user.getId())){

            holder.left_layout.setVisibility(View.VISIBLE);
           holder.right_layout.setVisibility(View.GONE);
            Date sendTime = privateMessage.getSendTime();
            holder.date.setText(DateUtil.formatDataTime(sendTime.getTime()));
            holder.content_left.setText(privateMessage.getContent());
//            holder.iv_left_image.setImageResource(imagePicture.getImageId());
        }
        if(sendId.equals(user.getId()))
        {
            holder.right_layout.setVisibility(View.VISIBLE);
          holder.left_layout.setVisibility(View.GONE);
            Date sendTime = privateMessage.getSendTime();
            holder.date.setText(DateUtil.formatDataTime(sendTime.getTime()));
          holder.content_right.setText(privateMessage.getContent());
//            holder.iv_right_image.setImageResource(imagePicture.getImageId());
        }

        return convertView;
    }
    public final class ViewHolder{
        private TextView content_left,date,content_right;
        private  LinearLayout left_layout;
        private  LinearLayout right_layout;
//        private  ImagePicture me_chat_bg_left;
//        private SetRoundImageViewUtil iv_left_image;
//        private  SetRoundImageViewUtil iv_right_image;
    }
}
