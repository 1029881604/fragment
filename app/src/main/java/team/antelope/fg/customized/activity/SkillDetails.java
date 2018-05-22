package team.antelope.fg.customized.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.R;
import team.antelope.fg.common.GlideApp;
import team.antelope.fg.customized.trpay.PayActivity;
import team.antelope.fg.customized.constant.AccessNetConst;
import team.antelope.fg.customized.scrollView.MyScrollView;
import team.antelope.fg.customized.trpay.SkillsByTrPayActivity;
import team.antelope.fg.db.dao.IUserDao;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.activity.MeChangeProfileActivity;
import team.antelope.fg.me.constant.MeAccessNetConst;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.ui.business.CustmoizedBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.util.L;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.ToastUtil;


/**
 * Created by Kyrene on 2018/1/5.
 */

public class SkillDetails extends BaseActivity implements View.OnClickListener {

    ImageView ivBack;
    Toolbar toolbar;
    MyScrollView scrollView;
    LinearLayout lvBottom;
    ImageView ivMore;
    ImageView ivShoppingCart;
    LinearLayout content;
    View spiteLine;
    ImageView ivHeader;
    LinearLayout lvHeader;
    TextView skilltitle; //
    TextView skillcontent; //
    TextView skilltype;   //
    TextView startdate;   //
    TextView stopdate; //
    ImageView personpic;
    TextView personaname;    //
    TextView personrank; //
    TextView fansnum;   //
    TextView skillnum; //
    TextView finishnum; //
    TextView skillprice;
    LinearLayout personDetailsLayout;
    ImageView skillpic;

    Button pay;
    Button collectSkill;

    private Long user_id;   //当前登录用户id
//    private Long skillId;  //技能id
    String collectStatus;   //收藏状态（是否收藏）
    private Properties mProp;

    Person mPerson;  //人物实例
    public CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void initView(Bundle savedInstanceState) {
        ivBack = findViewById(R.id.iv_back);    //上方返回按钮
        toolbar = findViewById(R.id.toolbar);   //toolbar
        scrollView = findViewById(R.id.scrollView);     //scrollView滚动视图
        lvBottom = findViewById(R.id.lv_bottom);    //下方联系等LinearLayout
        ivMore = findViewById(R.id.iv_more);    //更多按钮
        ivShoppingCart = findViewById(R.id.iv_shopping_cart);       //购物车按钮
        content = findViewById(R.id.content);       //skillPage的LinearLayout
        spiteLine = findViewById(R.id.spite_line);      //分割线
        ivHeader = findViewById(R.id.iv_header);      //上方大图
        lvHeader = findViewById(R.id.lv_header);    //上方LinearLayout
        skilltitle = findViewById(R.id.skillTitle);     //技能标题
        skillcontent = findViewById(R.id.skillContent);     //技能内容
        skilltype = findViewById(R.id.skillType);       //技能类型
        startdate = findViewById(R.id.startDate);       //技能开始日期
        stopdate = findViewById(R.id.stopDate);     //技能停止日期
        personpic = findViewById(R.id.personPic);       //人物头像图片
        personaname = findViewById(R.id.personName);    //人物名字
        personrank = findViewById(R.id.personRank);     //人物星级
        fansnum = findViewById(R.id.fansNum);       //人物粉丝数
        skillnum = findViewById(R.id.skillNum);     //人物技能数量
        finishnum = findViewById(R.id.finishNum);   //人物技能完成数
        personDetailsLayout = findViewById(R.id.persondetails);     //人物信息部分LinearLayout
        skillpic = findViewById(R.id.skillbb);     //技能图片
        skillprice = findViewById(R.id.skillprice); //技能价格
        pay = findViewById(R.id.pay_btn);   //支付按钮
        collectSkill = findViewById(R.id.collectskill);     //收藏按钮


        ivBack.setOnClickListener(this);
        ivShoppingCart.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        pay.setOnClickListener(this);
        collectSkill.setOnClickListener(this);

        //获得Intent，并获取上一个activity传递过来的值
        Intent intent=getIntent();
        final long skillId = intent.getLongExtra("skillid", 0);
        final String skillTitle=intent.getStringExtra("title");
        final String skillContent=intent.getStringExtra("contents");
        final String skillType=intent.getStringExtra("skilltype");
        final String startDate=intent.getStringExtra("startdate");
        final String stopDate=intent.getStringExtra("stopdate");
        final String skillPicture = intent.getStringExtra("skillpic");  //新增的传递的图片
//        L.i("testskillpic",skillPicture);

        final long userid = intent.getLongExtra("userid", 0);       //技能拥有者id
//        L.i("testuserid",String.valueOf(userid));

//        personDao=new PersonDaoImpl(this);
//        person=personDao.queryById(userid);     //搜寻uid所对应的人物所有信息

        /**
         * @说明 获取当前登录用户的id
         * @创建日期 2018/5/18 下午8:11
         */
        IUserDao userDao = new UserDaoImpl(SkillDetails.this);
        User user = userDao.queryAllUser().get(0);
        user_id = user.getId();     //当前登录用户id


        /**
         * @说明 从服务器端获取person对象读取数据
         * @创建日期 2018/4/10 下午5:13
         */
        //从服务端获取person对象读取数据库
        String endUrl = PropertiesUtil.getInstance().
                getProperty(AccessNetConst.GETPERSONDETAILSENDPATH);
        Observable<Person> observable = RetrofitServiceManager.getInstance()
                .create(CustmoizedBusiness.class).getPerson(endUrl, userid
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
        addSubscription(observable.subscribe(new Subscriber<Person>() {
            @Override
            public void onCompleted() {
                L.i("skilldetails","111111111111");
                L.i("person",mPerson.toString());

//                SetImageViewUtil.setImageToImageView(skillHeaderPic,skillPic);

                skilltitle.setText(skillTitle);
                skillcontent.setText(skillContent);
                skilltype.setText(skillType);
                startdate.setText(startDate);
                stopdate.setText(stopDate);
//                personpic.setImageBitmap(BitmapUtil.getBitmap(mPerson.getHeadImg()));

//                L.i("testforpersonimg",mPerson.getHeadImg());

                if (mPerson!=null) {
                    personaname.setText(mPerson.getName());
                    personrank.setText(String.valueOf(mPerson.getStarnum()));
//        skillnum.setText(person.get);
                    fansnum.setText(String.valueOf(mPerson.getFansnum()));
                    finishnum.setText(String.valueOf(mPerson.getDealnum()));
//                skillBitmap.setImageURI(Uri.parse(skillPic));   没用
//                SetImageViewUtil.setImageToImageView(skillBitmap,skillPic);

                    /**
                     * @说明 设置图片
                     * @创建日期 2018/5/10 下午10:07
                     */
                    RequestOptions options = new RequestOptions();
                    options.centerCrop()
                            .placeholder(R.mipmap.default_avatar200)
                            .error(R.mipmap.error200)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter();
                    GlideApp.with(SkillDetails.this)
                            .load(mPerson.getHeadImg())
                            .apply(options)
                            .into(personpic);
                    GlideApp.with(SkillDetails.this)
                            .load(skillPicture)
                            .apply(options)
                            .into(skillpic);
                    GlideApp.with(SkillDetails.this)
                            .load(skillPicture)
                            .apply(options)
                            .into(ivHeader);
                    L.i("testPic", skillPicture);
                }

            }

            @Override
            public void onError(Throwable e) {
                L.i("customized", "onError");
            }

            @Override
            public void onNext(Person person) {
                mPerson = person;
                L.i("customized", "onNext1");
            }
        }));

        /**
         * @说明 获取收藏状态（是否收藏）
         * @创建日期 2018/5/18 下午10:10
         */
        String endUrl1 = PropertiesUtil.getInstance().
                getProperty(AccessNetConst.GETCOLLECTIONSTATUSENDPATH);
        Observable<String> observable1 = RetrofitServiceManager.getInstance()
                .create(CustmoizedBusiness.class).getCollectionStatus(endUrl1, Long.toString(user_id), Long.toString(skillId)).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
        addSubscription(observable1.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("status", "statusUSERID"+user_id);
                Log.i("status", "statusSKILLID"+skillId);
                Log.i("status", "statusCollect"+collectStatus);
                if (!collectStatus.equals("")) {
                    collectSkill.setText(collectStatus);
                }

            }

            @Override
            public void onError(Throwable e) {
                L.i("statusOnError", "onError");
            }

            @Override
            public void onNext(String s) {
                collectStatus = s;
                L.i("statusOnNext", "onNext1");
            }
        }));

//        /**
//         * @说明 获取收藏状态，并根据状态设置不同的ui
//         * @创建日期 2018/5/18 下午9:26
//         */
//        sendOkHttpRequestForGetStatus();
//        Log.i("status", "ss"+collectStatus);



//        skilltitle.setText(skillTitle);
//        skillcontent.setText(skillContent);
//        skilltype.setText(skillType);
//        startdate.setText(startDate);
//        stopdate.setText(stopDate);
//        personaname.setText(person.getName());
//        personrank.setText(String.valueOf(person.getStarnum()));
////        skillnum.setText(person.get);
//        fansnum.setText(String.valueOf(person.getFansnum()));
//        finishnum.setText(String.valueOf(person.getDealnum()));

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

        /**
         * @说明 支付按钮点击事件
         * @创建日期 2018/5/15 下午3:13
         */
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("uid_s", userid);//技能拥有者id
                intent1.putExtra("skillid", skillId);//技能id
                intent1.putExtra("title", skillTitle);//技能标题
                intent1.putExtra("content",skillContent);//技能详情
                intent1.putExtra("img", skillPicture);//技能图片
                intent1.putExtra("skilltype", skillType);//技能类型
                intent1.putExtra("price",skillprice.getText().toString().trim());
                intent1.setClass(SkillDetails.this, SkillsByTrPayActivity.class);
                Log.i("alipay", userid+skillId+skillTitle+skillContent+skillPicture+skillType);
                startActivity(intent1);
            }
        });

        /**
         * @说明 收藏按钮点击事件
         * @创建日期 2018/5/18 下午10:49
         */
        collectSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(SkillDetails.this);
                dialog.setTitle("提示");
                if (collectStatus.equals("收藏")){
                    dialog.setMessage("确定收藏该技能？");
                }else{
                    dialog.setMessage("取消收藏？");
                }
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProp = PropertiesUtil.getInstance();
//                        doDatabase();
                        sendRequestWithOkHttp();
                        if (collectStatus.equals("收藏")){
                            ToastUtil.showCustom(SkillDetails.this, "收藏成功", Toast.LENGTH_LONG);
                        }else{
                            ToastUtil.showCustom(SkillDetails.this, "取消收藏成功", Toast.LENGTH_LONG);
                        }
                        finish();
                    }

                    //                    private void doDatabase() {
//                        IUserDao userDao = new UserDaoImpl(SkillDetails.this);
//                        User user = userDao.queryAllUser().get(0);
//                        user_id = user.getId();     //当前用户id
//                    }
                    private void sendRequestWithOkHttp() {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String url = null;
                                try {
                                    url = mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH) +
                                            mProp.getProperty(AccessNetConst.COLLECTSKILLSENDPATH);
//                                    OkHttpClient client = new OkHttpClient();
                                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                                    OkHttpClient client = builder.build();
                                    //POST方式
                                    RequestBody requestBody = new FormBody.Builder()
                                            .add("userid", String.valueOf(user_id))
                                            .add("skillid", String.valueOf(skillId))
                                            .build();
                                    Request request = new Request.Builder().url(url).post(requestBody).build();
                                    Response response = client.newCall(request).execute();
                                    String responseData = response.body().string();
                                    showResponse(responseData);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            private void showResponse(String responseData) {
                                if (responseData != null) {
                                    Log.i("666", "成功"+"userid:"+user_id+"skillid:"+skillId);
                                } else
                                    Log.i("666", "失败");
                            }
                        }).start();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        ToastUtil.showCustom(SkillDetails.this, "用户取消收藏", Toast.LENGTH_LONG);
                    }
                });
                dialog.show();
            }
        });

    }


    private void HeaderTranslate(float distance) {
        lvHeader.setTranslationY(-distance);
        ivHeader.setTranslationY(distance/2);
    }

    /**
     * @说明 改变标题栏控件透明度，实现上下拉透明改变效果
     * @创建日期 2018/4/10 下午5:11
     */
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

    /**
     * @说明 点击事件
     * @创建日期 2018/4/10 下午5:12
     */
    @Override
    public void onClick(View v) {
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
//            case R.id.pay_btn:
//                Intent intent = new Intent();
//                intent.putExtra("title",skillTitle);
//                intent.setClass(this, PayActivity.class);
//
//                startActivity(intent);

//            case R.id.collectskill:
//                ToastUtil.showCustom(this,"点击了收藏按钮",2000);
//                break;
            default:
                break;
        }
    }

    /**
     * @说明 获取布局
     * @创建日期 2018/4/10 下午5:12
     */
    @Override
    public int getLayout() {
        return R.layout.lx_activity_skilldetails;
    }

    public void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
    }


}
