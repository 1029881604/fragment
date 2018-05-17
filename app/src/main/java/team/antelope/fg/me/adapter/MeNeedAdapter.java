package team.antelope.fg.me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.Date;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.common.GlideApp;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PersonNeed;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.me.activity.MeNeedActivity;
import team.antelope.fg.util.DateUtil;

/**
 * Created by Carlos on 2018/5/8.
 */

public class MeNeedAdapter extends BaseAdapter {
    private Context mContext;
    private List<PublishNeed> publishNeeds;
    private Bitmap bitmap;

    public MeNeedAdapter(Context mContext, List<PublishNeed> publishNeeds,Bitmap bitmap) {
        this.mContext = mContext;
        this.publishNeeds = publishNeeds;
        this.bitmap=bitmap;
    }
    public MeNeedAdapter(Context mContext, List<PublishNeed> publishNeeds) {
        this.mContext = mContext;
        this.publishNeeds = publishNeeds;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return publishNeeds.size();
    }

    @Override
    public Object getItem(int position) {
        return publishNeeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(convertView == null){
            view = view.inflate(mContext, R.layout.me_need_item, null);
        }
        ViewHolder mViewHolder = ViewHolder.getHolder(view);

        PublishNeed p = publishNeeds.get(position);
        mViewHolder.tv_title.setText(p.getTitle());
        mViewHolder.tv_need_id.setText(String.valueOf(p.getId()));
        mViewHolder.tv_location.setText(p.getAddressDesc());
        mViewHolder.tv_content.setText(p.getContent());
        Date requestTime = p.getRequestDate();
        mViewHolder.tv_time.setText(DateUtil.formatDataTime(requestTime.getTime()));
//        RequestOptions options = new RequestOptions();
//        GlideApp.with(mContext)
//                .load(p.getHeadimg())
//                .placeholder(R.mipmap.default_avatar400)
//                .error(R.mipmap.error400)
//                .apply(options)
//                .into(mViewHolder.iv_head);

        return view;
    }

    static class ViewHolder {
        TextView tv_title;
        TextView tv_location;
        TextView tv_content;
        ImageView iv_head;
        TextView tv_time;
        TextView tv_need_id;

        public static ViewHolder getHolder(View view) {
            Object tag = view.getTag();
            if(tag != null){
                return (ViewHolder)tag;
            }else {
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
                viewHolder.tv_location = (TextView) view.findViewById(R.id.tv_location);
                viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);
                viewHolder.tv_time = (TextView) view.findViewById(R.id.tv_time);
                viewHolder.tv_need_id = (TextView) view.findViewById(R.id.tv_need_id);
                view.setTag(viewHolder);
                return viewHolder;
            }
        }

    }
}
