package team.antelope.fg.me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.entity.Person;
import team.antelope.fg.me.activity.ImagePicture;
import team.antelope.fg.util.SetRoundImageViewUtil;

/**
 * Created by Carlos on 2018/1/1.
 */

public class MeFollowListAdapter extends BaseAdapter {
    private Context context;
    List<Person> personList;
    List<ImagePicture> imagePictureList;
    private ViewHolder viewHolder;


    public MeFollowListAdapter(Context context, List<Person> plist, List<ImagePicture> ilist) {
        this.context = context;
        personList = plist;
        imagePictureList = ilist;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        if (personList.get(position).getName().length() == 1)// 如果是字母索引
            return false;// 表示不能点击
        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = personList.get(position).getName();
        viewHolder = new ViewHolder();
        if (item.length() == 1) {
            convertView = LayoutInflater.from(context).inflate(R.layout.me_follow_index,
                    null);
            viewHolder.indexTv = (TextView) convertView
                    .findViewById(R.id.indexTv);
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.me_follow_items,
                    null);
            viewHolder.tv_personId =convertView.findViewById(R.id.tv_personId);
            viewHolder.personName = (TextView) convertView
                    .findViewById(R.id.tv_follow_name);
            viewHolder.personHead = (SetRoundImageViewUtil) convertView
                    .findViewById(R.id.iv_follow_user_head);
            viewHolder.iv_send_message = convertView.findViewById(R.id.iv_send_message);
        }
        if (item.length() == 1) {
            viewHolder.indexTv.setText(personList.get(position).getName());
        } else {
            ImagePicture imagePicture = imagePictureList.get(position);
            Person person = (Person) personList.get(position);
            viewHolder.personName.setText(person.getName());
            viewHolder.personHead.setImageResource(imagePicture.getImageId());
            viewHolder.tv_personId.setText(person.getId()+"");
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView indexTv;
        private TextView personName;
        private SetRoundImageViewUtil personHead;
        private ImageView iv_send_message;
        private TextView tv_personId;
    }

}
