package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Properties;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.customized.constant.AccessNetConst;
import team.antelope.fg.customized.util.SetImageViewUtil;
import team.antelope.fg.entity.Orders;
import team.antelope.fg.entity.Person;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.L;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.ToastUtil;

/**
* @说明 Create by Kyrene lx  订单详情页
* @创建日期 2018/6/10 下午4:35
*/
public class OrderDetails extends BaseActivity {

    Properties mProp;
    Orders orders;
    Person person;
    Orders ordersT;
    Person personT1;
    Person personT2;
    int flagByStatus;   //订单状态标识：1为已支付、0为待支付，用于详情页最上方图片展示判断

    long orderId;   //订单编号
    long userId;    //支付者id
    long personId;  //技能拥有者id
    String personImg;  //技能拥有者头像
//    String skillImg;    //技能图片
//    String skillTitle;  //技能标题
//    String skillContent;    //技能介绍
//    String createTime;  //订单创建时间

    Button detailsBack; //返回按钮
    ImageView topPic;   //顶上图片组件
    ImageView personPic;    //技能拥有者头像组件
    TextView personName;    //技能拥有者姓名组件
    ImageView skillPic;     //技能图片组件
    TextView skillTitleTv;  //技能标题组件
    TextView skillContentTv;    //技能内容组件
    TextView skillPriceTv;      //技能总价格
    TextView orderIDTv;     //订单编号组件
    TextView createTimeTv;  //创建时间组件
    TextView userNameTv;    //支付方姓名组件
    TextView skillPersonNameTv;     //技能拥有者姓名组件

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){     //1是获取order、2是获取person、3是获取user
                ordersT= (Orders)msg.obj;
                initLayoutViewForOrder();
            }else if (msg.what == 2){
                personT1 = (Person)msg.obj;
                initLayoutViewForPerson();
            }else if (msg.what == 3){
                personT2 = (Person)msg.obj;
                initLayoutViewForUser();
            }
            else{
                ToastUtil.showCustom(OrderDetails.this,"请检查网络连接",3000);
            }
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        detailsBack = findViewById(R.id.back_orderDetails);
        topPic = findViewById(R.id.orderDetails_bigImg);
        personPic = findViewById(R.id.orderDetails_personImg);
        personName = findViewById(R.id.orderDetails_personName);
        skillPic = findViewById(R.id.orderDetails_skillImg);
        skillTitleTv = findViewById(R.id.orderDetails_skillTitle);
        skillContentTv = findViewById(R.id.orderDetails_skillContent);
        skillPriceTv = findViewById(R.id.orderDetails_orderPrice);
        orderIDTv = findViewById(R.id.orderDetails_orderId);
        createTimeTv = findViewById(R.id.orderDetails_createTime);
        userNameTv = findViewById(R.id.orderDetails_userName);
        skillPersonNameTv = findViewById(R.id.orderDetails_skillPersonName);

        Intent intent = getIntent();
        orderId = intent.getLongExtra("orderid",0);
        userId = intent.getLongExtra("uid", 0);
        personId = intent.getLongExtra("personid", 0);
        flagByStatus = intent.getIntExtra("flagbystatus", 1);
        L.i("flagbystatus","-----"+flagByStatus);

        detailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendOkHttpRequestForOrder();
        sendOkHttpRequestForPerson(personId,"person");
        sendOkHttpRequestForPerson(userId, "user");
    }

    private void initLayoutViewForOrder(){
        L.i("flagbystatus2222","-----"+flagByStatus);
        if (flagByStatus == 1){
            topPic.setBackgroundResource(R.drawable.lx_orderdetailsispay);
        }else{
            topPic.setBackgroundResource(R.drawable.lx_orderdetailsnotpay);
        }
        SetImageViewUtil.setImageToImageView(skillPic, ordersT.getImg());
        skillTitleTv.setText(ordersT.getTitle());
        skillContentTv.setText(ordersT.getContent());
        skillPriceTv.setText(String.valueOf(ordersT.getPrice()));
        orderIDTv.setText(String.valueOf(ordersT.getId()));
        createTimeTv.setText(String.valueOf(ordersT.getCreate_time()));
    }
    private void initLayoutViewForPerson(){
        //技能拥有者头像
        SetImageViewUtil.setImageToImageView(personPic, personT1.getHeadImg());
        //技能拥有者姓名
        personName.setText(personT1.getName());
        skillPersonNameTv.setText(personT1.getName());
    }
    private void initLayoutViewForUser(){
        //用户姓名
        userNameTv.setText(personT2.getName());
    }

    private void sendOkHttpRequestForOrder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();

                    mProp = PropertiesUtil.getInstance();
                    String path = mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH)
                            +mProp.getProperty(AccessNetConst.GETORDERDETAILSENDPATH);
                    Request request = new Request.Builder()
                            .url(path+"?orderId="+orderId)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSONForOrder(responseData);
                    Message message = new Message();
                    //用于判断网络连接
                    if (responseData == null){
                        message.what = 0;
                    }else{
                        message.what = 1;
                        message.obj = orders;
                    }

                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSONForOrder(String responseData) {
        Gson gson = new Gson();
        orders = gson.fromJson(responseData, new TypeToken<Orders>() {}.getType());
    }

    private void sendOkHttpRequestForPerson(final long id, final String flag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();

                    mProp = PropertiesUtil.getInstance();
                    String path = mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH)
                            +mProp.getProperty(AccessNetConst.GETPERSONINFORMATIONENDPATH);
                    Request request = new Request.Builder()
                            .url(path+"?id="+id)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSONForPerson(responseData);
                    Message message = new Message();
                    //用于判断网络连接
                    if (responseData == null){
                        message.what = 0;
                    }else if(flag.equals("person")){
                        message.what = 2;
                        message.obj = person;
                    }else if (flag.equals("user")){
                        message.what = 3;
                        message.obj = person;
                    }

                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSONForPerson(String responseData) {
        Gson gson = new Gson();
        person = gson.fromJson(responseData, new TypeToken<Person>() {}.getType());
    }

    @Override
    public int getLayout() {
        return R.layout.lx_ordersdetails;
    }
}
