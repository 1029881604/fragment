package team.antelope.fg.publish.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.PublishNeedDaoImpl;
import team.antelope.fg.db.dao.impl.PublishSkillDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.entity.User;
import team.antelope.fg.publish.adapter.PublishFbPicGridViewAdapter;
import team.antelope.fg.publish.widget.CustomDatePicker;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.L;

/**
 * Created by PC_LRY on 2018/1/4.
 */

public class PublishNeedActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = PublishNeedActivity.class.getSimpleName();
    private Context mContext;
    private Toolbar mToolbar;
    private PopupWindow popupWindow;
    private Button btn_fabu;
    private EditText et_title;
    private TextView tv_type;
    private EditText et_dec;
    private TextView tv_stoptime;
    private CheckBox cb_isonline;
    private TextView tv_address;

    private TextView currentTime;
    private GridView gridView;
    private CustomDatePicker customDatePicker;

    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private PublishFbPicGridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private String dizhi="江西南昌";
    @Override
    public int getLayout() {
        return R.layout.publish_fb_need;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();
        initEvent();

    }
    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==0x11&&resultCode==0x11){
            Bundle bundle=data.getExtras();
            String type=bundle.getString("type");
            tv_type.setText(type);
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == PublishFbPicConstant.REQUEST_CODE_MAIN && resultCode == PublishFbPicConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(PublishFbPicConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }

    }
    /**
     * @Date: 2018/1/5 20:27
     * @Description: onclick方法
     */
    @Override
    public void onClick(View whichbtn){
        switch(whichbtn.getId()){
            case R.id.publish_fb_type_con:
                Intent intent1=new Intent();
                intent1.setClass(PublishNeedActivity.this, PublishNeedTypeActivity.class);
                startActivityForResult(intent1,0x11);
                break;
            case R.id.publish_fb_stoptime_con:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker.show(currentTime.getText().toString());
                break;
            case R.id.publish_btn_fb:
                showpopfb();
                break;
            case R.id.cancel_tv:
                popupWindow.dismiss();
                break;
            case R.id.ok_tv:
                btn_fabuneed();
                popupWindow.dismiss();
                finish();
                break;
        }
    }
    /**
     * @Date: 2018/1/6 14:57
     * @Description: 用于初始化视图
     */
    public void init(){
        mContext = this;
        currentTime = (TextView) findViewById(R.id.publish_fb_stoptime_con);
        gridView = (GridView) findViewById(R.id.publish_fb_pic_gridview);
        btn_fabu=findViewById(R.id.publish_btn_fb);
        et_title=findViewById(R.id.publish_fb_title_con);
        tv_type=findViewById(R.id.publish_fb_type_con);
        et_dec=findViewById(R.id.publish_fb_des_con);
        tv_stoptime=findViewById(R.id.publish_fb_stoptime_con);
        cb_isonline=findViewById(R.id.publish_fb_isonline_cb);
        tv_address=findViewById(R.id.publish_fb_weizhi_con);

    }

    /**
     * @Date: 2018/1/6 14:58
     * @Description: 用于处理事件
     */
    public void initEvent(){
        currentTime.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        btn_fabu.setOnClickListener(this);
        tv_address.setText(dizhi);
        //是否线上CheckBox状态改变的事件监听
        changeCheckBox();
        initDatePicker();
        initGridView();
    }
    /**
     * @Date: 2018/1/5 20:26
     * @Description: CheckBox状态改变的监听方法
     */
    public void changeCheckBox(){
        cb_isonline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                RelativeLayout fb_weizhi=findViewById(R.id.publish_fb_weizhi);
                View fb_weizhi_view=findViewById(R.id.publish_fb_weizhi_view);
                if(isChecked){
                    fb_weizhi_view.setVisibility(View.GONE);
                    fb_weizhi.setVisibility(View.GONE);
                }else{
                    fb_weizhi_view.setVisibility(View.VISIBLE);
                    fb_weizhi.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        String end=sdf.format(new Date(System.currentTimeMillis()+24*3600));
        currentTime.setText(now);

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentTime.setText(time);
            }
        },now ,"2018-01-30 10:10"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
    }

    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new PublishFbPicGridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == PublishFbPicConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(PublishFbPicConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PublishFbPicPlusImageActivity.class);
        intent.putStringArrayListExtra(PublishFbPicConstant.IMG_LIST, mPicList);
        intent.putExtra(PublishFbPicConstant.POSITION, position);
        startActivityForResult(intent, PublishFbPicConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PublishFbPicSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    public void btn_fabuneed(){
        long id,uid;
        String title,content,img="http:img",type,address;
        Date date=new Date(),stopdata;
        Boolean isonline,iscomplete=false;
        double x=0.0,y=0.0;

        List<PublishNeed> publishNeeds=(new PublishNeedDaoImpl(PublishNeedActivity.this)).queryAllPublishNeed();
        id=publishNeeds.get(publishNeeds.size()-1).getId()+1;
        List<User> users=(new UserDaoImpl(PublishNeedActivity.this)).queryAllUser();
        uid=users.get(0).getId();
        title=et_title.getText().toString();
        content=et_dec.getText().toString();
        type=tv_type.getText().toString();
        address=tv_address.getText().toString();
        stopdata=new Date(System.currentTimeMillis()+3600*24);
        isonline=true;
        PublishNeedDaoImpl needdao = new PublishNeedDaoImpl(PublishNeedActivity.this);
        needdao.insert(new PublishNeed(id , uid , title , content ,  type,date,stopdata, iscomplete, isonline, address, x,y));
    }
    public void showpopfb(){
        View contentView= LayoutInflater.from(PublishNeedActivity.this).inflate(R.layout.publish_fb_sure,null);
        popupWindow=new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.MATCH_PARENT,true);
        //设置各个控件的点击响应
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView tv1=(TextView)contentView.findViewById(R.id.cancel_tv);
        tv1.setOnClickListener(this);
        TextView tv2=(TextView)contentView.findViewById(R.id.ok_tv);
        tv2.setOnClickListener(this);
        L.i("tag",tv1.getText().toString());
        //显示PopupWindow

        //popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);  //去除控件宽度
        View rootview=LayoutInflater.from(PublishNeedActivity.this).inflate(R.layout.publish_fb_skill, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER,0,0);
        //设置打开popupwindow时背景变暗
        backgroundAlpha(0.7f);
        //关闭popupwindow时背景恢复原来
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
