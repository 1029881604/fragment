package team.antelope.fg.me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
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
import team.antelope.fg.me.entity.PersonPinyin;

/**
 * Created by Carlos on 2018/4/24.
 */

public class MeFansListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Person> person;
    private Bitmap bitmap;

    public MeFansListAdapter(Context mContext, List<Person> person,Bitmap bitmap) {
        this.mContext = mContext;
        this.person = person;
        this.bitmap=bitmap;
    }
    public MeFansListAdapter(Context mContext, List<Person> person) {
        this.mContext = mContext;
        this.person = person;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return person.size();
    }

    @Override
    public Object getItem(int position) {
        return person.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(convertView == null){
            view = view.inflate(mContext, R.layout.me_fanslist_item, null);
        }
        ViewHolder mViewHolder = ViewHolder.getHolder(view);

        Person p = person.get(position);

        mViewHolder.mName.setText(p.getName());
        mViewHolder.mFansId.setText(String.valueOf(p.getId()));
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
        TextView mName;
        ImageView mHead;
        TextView mFansId;

        public static ViewHolder getHolder(View view) {
            Object tag = view.getTag();
            if(tag != null){
                return (ViewHolder)tag;
            }else {
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.mName = (TextView) view.findViewById(R.id.fans_name);
                viewHolder.mHead = (ImageView) view.findViewById(R.id.fans_head);
                viewHolder.mFansId = (TextView) view.findViewById(R.id.fans_id);
                view.setTag(viewHolder);
                return viewHolder;
            }
        }

    }
}
