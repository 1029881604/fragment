package team.antelope.fg.publish.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import team.antelope.fg.R;
import team.antelope.fg.publish.utils.SetImageUtils;
import team.antelope.fg.util.L;

/**
 * Created by PC_LRY on 2017/12/26.
 */

public class PublishItemsAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<HashMap<String,Object>> list;
    private Boolean fbkind;  //若是发布需求为true，发布技能为false
    public PublishItemsAdapter(Context context, ArrayList<HashMap<String,Object>> list, Boolean fbkind){
        this.context = context;
        this.list=list;
        this.fbkind=fbkind;
    }
    @Override
    public int getCount() {
        return list.size();//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.publish_fb_items, null);
            holder = new ViewHolder();
            holder.tv_username=(TextView)convertView.findViewById(R.id.publish_username);
            holder.tv_dingwei=(TextView)convertView.findViewById(R.id.publish_dingwei);
            holder.tv_fbtime=(TextView)convertView.findViewById(R.id.publish_fbtime);
            holder.iv_touxiang=convertView.findViewById(R.id.publish_touxiang);
            holder.tv_detail=convertView.findViewById(R.id.publish_detail);
            holder.iv_isfinished=convertView.findViewById(R.id.publish_isfinished);
            holder.cb_fenxiang=convertView.findViewById(R.id.publish_fenxiang);
            holder.rl_fenxiang=convertView.findViewById(R.id.publish_fb_fenxiang_rl);
            holder.tv_fenxiang_qq=convertView.findViewById(R.id.publish_fenxiang_qq);
            holder.tv_fenxiang_wechat=convertView.findViewById(R.id.publish_fb_fenxiang_wechat);
            convertView.setTag(holder);  //绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();  //取出ViewHolder对象
        }
        SetImageUtils.setBitmap(holder.iv_touxiang,list.get(position).get("head").toString());
        holder.tv_username.setText(list.get(position).get("username").toString());
        holder.tv_fbtime.setText(list.get(position).get("fbtime").toString());
        holder.tv_detail.setText(list.get(position).get("detail").toString());
        //位置显示
        if(list.get(position).get("isonline").toString()=="true"){
            holder.tv_dingwei.setVisibility(View.INVISIBLE);
        }else{
            holder.tv_dingwei.setVisibility(View.VISIBLE);
            holder.tv_dingwei.setText(list.get(position).get("dingwei").toString());
        }
       if (fbkind){
           holder.iv_isfinished.setVisibility(View.VISIBLE);
           //holder.iv_touxiang.setImageResource(R.drawable.publish_touxiang_nan);
           if(list.get(position).get("isfinished").toString()=="true"){
               holder.iv_isfinished.setImageResource(R.drawable.publish_fb_finished);
           }else{
               holder.iv_isfinished.setImageResource(R.drawable.publish_fb_unfinished);
           }
       }else{
           holder.iv_isfinished.setVisibility(View.INVISIBLE);
       }
        //holder.iv_fenxiang.setImageResource(list.get(position).get("touxiang").);

        holder.cb_fenxiang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    holder.rl_fenxiang.setVisibility(View.VISIBLE);
                }else{
                    holder.rl_fenxiang.setVisibility(View.GONE);
                }
            }
        });
       holder.tv_fenxiang_qq.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(context,"成功将"+position+"分享到QQ中",Toast.LENGTH_SHORT).show();
           }
       });
        holder.tv_fenxiang_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"成功将"+position+"分享到微信中",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    private final class ViewHolder{
        TextView tv_username;
        TextView tv_dingwei;
        TextView tv_fbtime;
        ImageView iv_touxiang;
        TextView tv_detail;
        ImageView iv_isfinished;
        CheckBox cb_fenxiang;
        RelativeLayout rl_fenxiang;
        TextView tv_fenxiang_qq;
        TextView tv_fenxiang_wechat;
    }
}
