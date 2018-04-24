package team.antelope.fg.me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.common.GlideApp;
import team.antelope.fg.entity.Person;
import team.antelope.fg.me.activity.ImagePicture;
import team.antelope.fg.me.activity.MePersonActivity;
import team.antelope.fg.me.entity.PersonPinyin;
import team.antelope.fg.util.SetRoundImageViewUtil;

/**
 * Created by Carlos on 2018/1/1.
 */

public class MeFollowListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PersonPinyin> personpinyin;
    private Bitmap bitmap;

    public MeFollowListAdapter(Context mContext, ArrayList<PersonPinyin> personpinyin,Bitmap bitmap) {
        this.mContext = mContext;
        this.personpinyin = personpinyin;
        this.bitmap=bitmap;
    }
    public MeFollowListAdapter(Context mContext, ArrayList<PersonPinyin> personpinyin) {
        this.mContext = mContext;
        this.personpinyin = personpinyin;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return personpinyin.size();
    }

    @Override
    public Object getItem(int position) {
        return personpinyin.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(convertView == null){
            view = view.inflate(mContext, R.layout.me_follow_items, null);
        }
        ViewHolder mViewHolder = ViewHolder.getHolder(view);

        PersonPinyin p = personpinyin.get(position);

        String str = null;
        String currentLetter = p.getPinyin().charAt(0) + "";
        String currentEng=p.getName().charAt(0)+"";
        // 根据上一个首字母,决定当前是否显示字母
        if(position == 0){
            str = currentLetter;
        }else {
            // 上一个人的拼音的首字母
            String preLetter = personpinyin.get(position - 1).getPinyin().charAt(0) + "";
            if(!TextUtils.equals(preLetter, currentLetter)){
                str = currentLetter;
            }
        }

        // 根据str是否为空,决定是否显示索引栏
        mViewHolder.mIndex.setVisibility(str == null ? View.GONE : View.VISIBLE);
        mViewHolder.mIndex.setText(currentLetter);
        mViewHolder.mName.setText(p.getName());
        mViewHolder.mPersonId.setText(String.valueOf(p.getPersonId()));
//        mViewHolder.mHead.setImageBitmap(bitmap);
        RequestOptions options = new RequestOptions();
        GlideApp.with(mContext)
                .load(p.getHeadImg())
                .placeholder(R.mipmap.default_avatar400)
                .error(R.mipmap.error400)
                .apply(options)
                .into(mViewHolder.mHead);

        return view;
    }

    static class ViewHolder {
        TextView mIndex;
        TextView mName;
        ImageView mHead;
        TextView mPersonId;

        public static ViewHolder getHolder(View view) {
            Object tag = view.getTag();
            if(tag != null){
                return (ViewHolder)tag;
            }else {
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.mIndex = (TextView) view.findViewById(R.id.tv_index);
                viewHolder.mName = (TextView) view.findViewById(R.id.tv_follow_name);
                viewHolder.mHead = (ImageView) view.findViewById(R.id.iv_follow_user_head);
                viewHolder.mPersonId = (TextView) view.findViewById(R.id.tv_personId);
                view.setTag(viewHolder);
                return viewHolder;
            }
        }

    }

}
