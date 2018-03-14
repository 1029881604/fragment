package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.antelope.fg.R;
import team.antelope.fg.customized.scrollView.MyScrollView;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.ToastUtil;


/**
 * Created by Kyrene on 2018/1/5.
 */

public class SkillDetails extends BaseActivity {

    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.scrollView) MyScrollView scrollView;
    @BindView(R.id.lv_bottom) LinearLayout lvBottom;
    @BindView(R.id.iv_more) ImageView ivMore;
    @BindView(R.id.iv_shopping_cart) ImageView ivShoppingCart;
    @BindView(R.id.content) LinearLayout content;
    @BindView(R.id.spite_line) View spiteLine;
    @BindView(R.id.iv_header) ImageView ivHeader;
    @BindView(R.id.lv_header) LinearLayout lvHeader;
    @BindView(R.id.skillTitle) TextView skilltitle; //
    @BindView(R.id.skillContent) TextView skillcontent; //
    @BindView(R.id.skillType) TextView skilltype;   //
    @BindView(R.id.startDate) TextView startdate;   //
    @BindView(R.id.stopDate) TextView stopdate; //
    @BindView(R.id.personPic) ImageView personpic;
    @BindView(R.id.personName) TextView personaname;    //
    @BindView(R.id.personRank) TextView personrank; //
    @BindView(R.id.fansNum) TextView fansnum;   //
    @BindView(R.id.skillNum) TextView skillnum; //
    @BindView(R.id.finishNum) TextView finishnum; //
    @BindView(R.id.persondetails) LinearLayout personDetailsLayout;

    Person person;  //人物实例
    PersonDaoImpl personDao;    //人物数据实例



    @Override
    protected void initView(Bundle savedInstanceState) {
//        ImageView ivBack=(ImageView)findViewById(R.id.iv_back);
//        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
//        MyScrollView scrollView=(MyScrollView)findViewById(R.id.scrollView);
//        LinearLayout lvBottom=(LinearLayout)findViewById(R.id.lv_bottom);
//        ImageView ivMore=(ImageView)findViewById(R.id.iv_more);
//        ImageView ivShoppingCart=(ImageView)findViewById(R.id.iv_shopping_cart);
//        LinearLayout content=(LinearLayout)findViewById(R.id.content);
//        View spiteLine=(View)findViewById(R.id.spite_line);
//        ImageView ivHeader=(ImageView)findViewById(R.id.iv_header);
//        LinearLayout lvHeader=(LinearLayout)findViewById(R.id.lv_header);

        ButterKnife.bind(this);

        //获得Intent，并获取上一个activity传递过来的值
        Intent intent=getIntent();
        String skillTitle=intent.getStringExtra("title");
        String skillContent=intent.getStringExtra("contents");
        String skillType=intent.getStringExtra("skilltype");
        String startDate=intent.getStringExtra("startdate");
        String stopDate=intent.getStringExtra("stopdate");
        final long userid = getIntent().getLongExtra("userid", 0l);

        personDao=new PersonDaoImpl(this);
        person=personDao.queryById(userid);     //搜寻uid所对应的人物所有信息

        skilltitle.setText(skillTitle);
        skillcontent.setText(skillContent);
        skilltype.setText(skillType);
        startdate.setText(startDate);
        stopdate.setText(stopDate);
        personaname.setText(person.getName());
        personrank.setText(String.valueOf(person.getStarnum()));
//        skillnum.setText(person.get);
        fansnum.setText(String.valueOf(person.getFansnum()));
        finishnum.setText(String.valueOf(person.getDealnum()));

        //获取dimen属性中 标题和头部图片的高度
        final float title_height = getResources().getDimension(R.dimen.title_height);
        final float head_height = getResources().getDimension(R.dimen.head_height);

        //滑动事件回调监听（一次滑动的过程一般会连续触发多次）
        scrollView.setOnScrollListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {

                float move_distance = head_height - title_height;
                if (!isUp && dy <= move_distance) {//手指往上滑,距离未超过200dp
                    //标题栏逐渐从透明变成不透明
                    toolbar.setBackgroundColor(ContextCompat.getColor(SkillDetails.this, R.color.color_white));
                    TitleAlphaChange(dy, move_distance);//标题栏渐变
                    HeaderTranslate(dy);//图片视差平移

                } else if (!isUp && dy > move_distance) {//手指往上滑,距离超过200dp
                    TitleAlphaChange(1, 1);//设置不透明百分比为100%，防止因滑动速度过快，导致距离超过200dp,而标题栏透明度却还没变成完全不透的情况。

                    HeaderTranslate(head_height);//这里也设置平移，是因为不设置的话，如果滑动速度过快，会导致图片没有完全隐藏。

                    ivBack.setImageResource(R.mipmap.lx_ic_back_dark);
                    ivMore.setImageResource(R.mipmap.lx_ic_more_dark);
                    ivShoppingCart.setImageResource(R.mipmap.lx_ic_shopping_dark);
                    spiteLine.setVisibility(View.VISIBLE);

                } else if (isUp && dy > move_distance) {//返回顶部，但距离头部位置大于200dp
                    //不做处理

                } else if (isUp && dy <= move_distance) {//返回顶部，但距离头部位置小于200dp
                    //标题栏逐渐从不透明变成透明
                    TitleAlphaChange(dy, move_distance);//标题栏渐变
                    HeaderTranslate(dy);//图片视差平移

                    ivBack.setImageResource(R.mipmap.lx_ic_back);
                    ivMore.setImageResource(R.mipmap.lx_ic_more);
                    ivShoppingCart.setImageResource(R.mipmap.lx_ic_shopping_cart);
                    spiteLine.setVisibility(View.GONE);
                }
            }
        });


        /**
        * @说明 人物信息栏Layout点击事件
        * @创建日期 2018/1/8 上午5:28
        */
        personDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPerson=new Intent();
                intentPerson.putExtra("person_id",userid);
                intentPerson.setClass(SkillDetails.this,PersonDetails.class);
                startActivity(intentPerson);
            }
        });

    }

    private void HeaderTranslate(float distance) {
        lvHeader.setTranslationY(-distance);
        ivHeader.setTranslationY(distance/2);
    }

    private void TitleAlphaChange(int dy, float mHeaderHeight_px) {//设置标题栏透明度变化
        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight_px);
        //如果是设置背景透明度，则传入的参数是int类型，取值范围0-255
        //如果是设置控件透明度，传入的参数是float类型，取值范围0.0-1.0
        //设置背景透明度就好，因为设置控件透明度的话，返回ICON等也会变成透明的。
        //alpha 值越小越透明
        int alpha = (int) (percent * 255);
        toolbar.getBackground().setAlpha(alpha);//设置控件背景的透明度，传入int类型的参数（范围0~255）

        ivBack.getBackground().setAlpha(255 - alpha);
        ivMore.getBackground().setAlpha(255 - alpha);
        ivShoppingCart.getBackground().setAlpha(255 - alpha);
    }

    @OnClick({R.id.iv_back, R.id.iv_shopping_cart, R.id.iv_more})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
//                ToastUtil.showCustom(this, "点击了返回按钮", 2000);
                break;
            case R.id.iv_shopping_cart:
                ToastUtil.showCustom(this, "点击了分享按钮", 2000);
                break;
            case R.id.iv_more:
                ToastUtil.showCustom(this, "点击了更多按钮", 2000);
                break;
//            case R.id.persondetails:
//                Intent intentPerson=new Intent();
//                intentPerson.putExtra("person_id",userid);
//                intentPerson.setClass(SkillDetails.this,PersonDetails.class);
//                startActivity(intentPerson);

        }
    }

    @Override
    public int getLayout() {
        return R.layout.lx_activity_skilldetails;
    }

}
