package team.antelope.fg.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.customized.util.SetImageViewUtil;

/**
 * Created by Kyrene on 2017/12/18.
 */

public class MeSubAdapterImgUrl extends RecyclerView.Adapter<MyViewHolderImgUrl> {
    private List<String> lists;
    private Context context;
    //    private List<Integer> heights;
    private OnItemClickListener mListener;
    private List<String> resids;

    public MeSubAdapterImgUrl(Context context, List<String> lists, List<String> resids) {
        this.context = context;
        this.lists = lists;
        this.resids = resids;
//        getRandomHeight(this.lists);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public interface OnItemClickListener{
        void ItemClickListener(View view, int postion);
        void ItemLongClickListener(View view, int postion);
    }

//    private void getRandomHeight(List<String> lists){//得到随机item的高度
//        heights = new ArrayList<>();
//        for (int i = 0; i < lists.size(); i++) {
//            heights.add((int)(500+Math.random()*400));
//        }
//    }

    @Override
    public MyViewHolderImgUrl onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lx_items1,parent,false);
        MyViewHolderImgUrl viewHolder = new MyViewHolderImgUrl(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolderImgUrl holder, int position) {
        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
//        params.height = heights.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);//把params设置给item布局
//        holder.mIv.setBackgroundResource(resids.get(position));//设置图片（本地）
//        holder.mIv.setImageURI(Uri.parse(resids.get(position)));//设置图片（服务器）

        SetImageViewUtil.setImageToImageView(holder.mIv, resids.get(position));
        holder.mTv.setText(lists.get(position));//为控件绑定数据

        if(mListener!=null){//如果设置了监听那么它就不为空，然后回调相应的方法
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemClickListener(holder.itemView,pos);//把事件交给实现的接口那里处理
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemLongClickListener(holder.itemView,pos);//把事件交给实现的接口那里处理
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

}

class MyViewHolderImgUrl extends RecyclerView.ViewHolder{
    LinearLayout mll;
    TextView mTv;
    ImageView mIv;
    public MyViewHolderImgUrl(View itemView) {
        super(itemView);
        mTv = itemView.findViewById(R.id.textView);
        mIv = itemView.findViewById(R.id.iv);
        mll = itemView.findViewById(R.id.ll);
    }
}
