package team.antelope.fg.customized.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.customized.util.SetImageViewUtil;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersHolder> {
    private List<Long> orderID;
    private List<String> resids;
    private List<String> skillTitle;
    private List<String> skillContent;
    private Context context;
    private OrdersRecyclerAdapter.OnItemClickListener mListener;


    public OrdersRecyclerAdapter(Context context, List<Long> orderID, List<String> resids,
                                 List<String> skillTitle, List<String> skillContent) {
        this.context = context;
        this.orderID = orderID;
        this.resids = resids;
        this.skillTitle = skillTitle;
        this.skillContent = skillContent;
    }
    public void setOnClickListener(OrdersRecyclerAdapter.OnItemClickListener listener){
        this.mListener = listener;
    }

    public interface OnItemClickListener{
        void ItemClickListener(View view, int postion);
        void ItemLongClickListener(View view, int postion);
        void ItemDeleteListener(View view, int postion);
        void ItemPayListener(View view, int postion);
    }

    @Override
    public OrdersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lx_orderitem,parent,false);
        OrdersHolder viewHolder = new OrdersHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrdersHolder holder, int position) {
        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
        holder.itemView.setLayoutParams(params);//把params设置给item布局

        holder.mOrderID.setText(String.valueOf(orderID.get(position)));
        SetImageViewUtil.setImageToImageView(holder.mSkillPic, resids.get(position));
        holder.mSkillTitle.setText(skillTitle.get(position));
        holder.mSkillContent.setText(skillContent.get(position));//为控件绑定数据



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
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mListener.ItemDeleteListener(holder.itemView, pos);
                }
            });
            holder.mPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mListener.ItemPayListener(holder.itemView, pos);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return orderID.size();
    }
}

class OrdersHolder extends RecyclerView.ViewHolder{
    LinearLayout mll;
    TextView mOrderID;
    ImageView mSkillPic;
    TextView mSkillTitle;
    TextView mSkillContent;
    Button mComment;
    Button mPay;
    Button mDelete;
    public OrdersHolder(View itemView) {
        super(itemView);
        mll = itemView.findViewById(R.id.orderll);
        mOrderID = itemView.findViewById(R.id.orderid);
        mSkillPic = itemView.findViewById(R.id.orderskillimg);
        mSkillTitle = itemView.findViewById(R.id.orderskilltitle);
        mSkillContent = itemView.findViewById(R.id.orderskillcontent);
        mComment = itemView.findViewById(R.id.ordercomment);
        mPay = itemView.findViewById(R.id.orderpay);
        mDelete = itemView.findViewById(R.id.orderdelete);
    }
}