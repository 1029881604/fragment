package team.antelope.fg.me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Map;

import team.antelope.fg.R;
import team.antelope.fg.entity.PrivateMessage;
import team.antelope.fg.me.activity.ImagePicture;
import team.antelope.fg.util.DateUtil;

/**
 * Created by Carlos on 2017/12/15.
 */

public class MessageListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Map<String, Object>> mList;
    private List<ImagePicture> imagePictureList;
    public MessageListAdapter(Context context, List<Map<String, Object>> list,List<ImagePicture> ilist) {
        mContext = context;
        mList = list;
        imagePictureList=ilist;
        System.out.println("3friends_size:"+list.size());
    }

    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.me_message_items, null);
            holder = new ViewHolder();
            holder.tv_message = (TextView) convertView.findViewById(R.id.tv_me_message);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_me_name);
            holder.tv_date = convertView.findViewById(R.id.tv_me_date);
            holder.tv_msg_num = convertView.findViewById(R.id.tv_me_msgnum);
            holder.tv_senderid_hidden_me = convertView.findViewById(R.id.tv_senderid_hidden_me);
            holder.iv_user_head =convertView.findViewById(R.id.me_message_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImagePicture imagePicture = imagePictureList.get(position);
        PrivateMessage privateMessage = (PrivateMessage) mList.get(position).get("privateMessage");
        holder.tv_name.setText(privateMessage.getSenderName());
        holder.tv_message.setText(privateMessage.getContent());
        Date sendTime = privateMessage.getSendTime();
        holder.tv_date.setText(DateUtil.formatDataTime(sendTime.getTime()));
        holder.iv_user_head.setImageResource(imagePicture.getImageId());
        Integer num = (Integer) mList.get(position).get("msgnum");
        boolean isRead = privateMessage.isRead();
        if(isRead){
            holder.tv_msg_num.setText( 0 + "");
            holder.tv_msg_num.setVisibility(View.GONE);
        } else {
            holder.tv_msg_num.setText( num + "");
            holder.tv_msg_num.setVisibility(View.VISIBLE);
        }
        long senderId = privateMessage.getSenderId();
        holder.tv_senderid_hidden_me.setText(senderId+"");
        return convertView;
    }

    private final class ViewHolder{
        TextView tv_message, tv_name, tv_date, tv_msg_num, tv_senderid_hidden_me;
        ImageView iv_user_head;
    }

}
