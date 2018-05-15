package team.antelope.fg.customized.trpay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.math.BigDecimal;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;

import java.lang.reflect.Method;
import java.util.UUID;

import team.antelope.fg.R;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.ToastUtil;

/**
 * Create by Kyrene
* @说明 支付页
* @创建日期 2018/5/15 上午10:45
*/
public class SkillsByTrPayActivity extends BaseActivity implements View.OnClickListener {

    private TextView commodityName;//商品名称
    private TextView commodityMoney;//商品价格
    private LinearLayout to_alipay;//支付宝支付按钮
    private LinearLayout to_wechatpay;//微信支付按钮
    private final static String channel = "暂无";//应用商店渠道名(如：360，小米、华为)
    private final static String appkey = "be6c44e655104d3d90e0d42432eb3c4d";//应用AppKey(测试key)

    /**
     * 初始化视图
     */
    private void findViews() {
        commodityName = findViewById(R.id.skillTitle_Trpay);
        commodityMoney =  findViewById(R.id.skillPrice_Trpay);
        to_alipay = findViewById(R.id.to_alipay);
        to_wechatpay = findViewById(R.id.to_wechatpay);
        to_alipay.setOnClickListener(this);
        to_wechatpay.setOnClickListener(this);

        Intent intent = getIntent();
        String skilltitle = intent.getStringExtra("title");
        String skillprice = intent.getStringExtra("price");
        commodityName.setText(skilltitle);
        commodityMoney.setText(skillprice);
    }

    @Override
    public void onClick(View v) {

        //发起支付所需参数
        String userid = "trpay@52yszd.com";//商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
        String outtradeno = UUID.randomUUID() + "";//商户系统订单号(为便于演示，此处利用UUID生成模拟订单号，商户系统内唯一)
        String tradename = commodityName.getText().toString().trim();//商品名称
        String backparams = "name=2&age=22";//商户系统回调参数
        String notifyurl = "http://101.200.13.92/notify/alipayTestNotify";//商户系统回调地址

        double amoutMediator = mulString(commodityMoney.getText().toString().trim());
        Long amount = new Double(amoutMediator).longValue();
//        Long amount = Long.valueOf(commodityMoney.getText().toString().trim());//商品价格（单位：分。如1.5元传150）
        switch (v.getId()){

            case R.id.to_alipay:{
                /**
                 * 发起支付宝支付调用
                 */
                TrPay.getInstance(this).callAlipay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
                    /**
                     * 支付完成回调
                     * @param context      上下文
                     * @param outtradeno   商户系统订单号
                     * @param resultCode   支付状态(RESULT_CODE_SUCC：支付成功、RESULT_CODE_FAIL：支付失败)
                     * @param resultString 支付结果
                     * @param payType      支付类型（1：支付宝 2：微信）
                     * @param amount       支付金额
                     * @param tradename    商品名称
                     */
                    @Override
                    public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                        if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {       //1：支付成功回调
                            TrPay.getInstance((Activity) context).closePayView();       //关闭快捷支付页面
                            ToastUtil.showCustom(SkillsByTrPayActivity.this, resultString, Toast.LENGTH_LONG);
                            //支付成功逻辑处理
                        } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {        //2：支付失败回调
                            ToastUtil.showCustom(SkillsByTrPayActivity.this, resultString, Toast.LENGTH_LONG);
                            //支付失败逻辑处理
                        }
                    }
                });
                break;
            }
            case R.id.to_wechatpay:{
                /**
                 * 发起微信支付调用
                 */
                TrPay.getInstance(this).callWxPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
                    /**
                     * 支付完成回调
                     * @param context      上下文
                     * @param outtradeno   商户系统订单号
                     * @param resultCode   支付状态(RESULT_CODE_SUCC：支付成功、RESULT_CODE_FAIL：支付失败)
                     * @param resultString 支付结果
                     * @param payType      支付类型（1：支付宝 2：微信）
                     * @param amount       支付金额
                     * @param tradename    商品名称
                     */
                    @Override
                    public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                        if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {       //1：支付成功回调
                            TrPay.getInstance((Activity) context).closePayView();       //关闭快捷支付页面
                            ToastUtil.showCustom(SkillsByTrPayActivity.this, resultString, Toast.LENGTH_LONG);
                            //支付成功逻辑处理
                        } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {        //2：支付失败回调
                            ToastUtil.showCustom(SkillsByTrPayActivity.this, resultString, Toast.LENGTH_LONG);
                            //支付失败逻辑处理
                        }
                    }
                });
            }

        }
    }//onClick

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViews();
        //初始化PaySdk(Context请传入当前Activity对象)
        TrPay.getInstance(this).initPaySdk(appkey, channel);
    }

    /**
    * @说明 数额*100，因为单位是分，*100用于内部计算
    * @创建日期 2018/5/15 下午3:45
    */
    public double mulString(String num){
        try {
            double result = Double.parseDouble(num);
            result *= 100;
            return result;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getLayout() {
        return R.layout.lx_skillsbytrpay;
    }
}
