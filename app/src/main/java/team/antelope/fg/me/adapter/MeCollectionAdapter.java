package team.antelope.fg.me.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.me.activity.ImagePicture;

/**
 * Created by Carlos on 2018/1/6.
 */

public class MeCollectionAdapter extends RecyclerView.Adapter<MeCollectionAdapter.ViewHolder> {

    private Context mContext;
    private List<ImagePicture> mImagePictureList;
 static  class  ViewHolder extends RecyclerView.ViewHolder{
     CardView cardView;
     ImageView iv_image;
     TextView  tv_picture_name;
     public  ViewHolder(View view){
         super(view);
         cardView =(CardView) view;
         iv_image =view.findViewById(R.id.iv_image);
         tv_picture_name =view.findViewById(R.id.tv_picture_name);

     }
 }
  public MeCollectionAdapter(List<ImagePicture> imagePictureList){
      mImagePictureList=imagePictureList;
  }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     if (mContext==null) {
         mContext =parent.getContext();
     }
     View view = LayoutInflater.from(mContext).inflate(R.layout.me_collection_item,
             parent,false);
     return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

      ImagePicture imagePicture = mImagePictureList.get(position);
      holder.tv_picture_name.setText(imagePicture.getName());
        Glide.with(mContext).load(imagePicture.getImageId()).into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        return mImagePictureList.size();
    }
}
