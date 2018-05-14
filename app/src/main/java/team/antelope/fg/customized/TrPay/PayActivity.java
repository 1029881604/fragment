package team.antelope.fg.customized.TrPay;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;

import java.util.UUID;

import team.antelope.fg.R;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.ToastUtil;

/**
 * describe：接入TRPAY快捷支付Demo实例
 * author：trpay
 * date：2017-12-01
 */
public class PayActivity extends BaseActivity implements View.OnClickListener {

    private EditText commodityName;//商品名称
    private EditText commodityMoney;//商品价格
    private Button topay;//快捷支付按钮
    private Button to_alpay;//支付宝支付按钮
    private Button to_wexinpay;//微信支付按钮
    private final static String channel = "暂无";//应用商店渠道名(如：360，小米、华为)
    private final static String appkey = "be6c44e655104d3d90e0d42432eb3c4d";//应用AppKey

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViews();
        //初始化PaySdk(Context请传入当前Activity对象)
        TrPay.getInstance(PayActivity.this).initPaySdk(appkey, channel);
    }

    @Override
    public int getLayout() {
        return R.layout.lx_paylayout;
    }

    /**
     * 初始化视图
     */
    private void findViews() {
        commodityName = findViewById(R.id.commodity_name);
        commodityMoney =  findViewById(R.id.commodity_money);
        topay =  findViewById(R.id.topay);
        to_alpay =  findViewById(R.id.to_alpay);
        to_wexinpay =  findViewById(R.id.to_wexinpay);
        topay.setOnClickListener(PayActivity.this);
        to_alpay.setOnClickListener(PayActivity.this);
        to_wexinpay.setOnClickListener(PayActivity.this);
    }

    @Override
    public void onClick(View v) {

        //发起支付所需参数
        String userid = "trpay@52yszd.com";//商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
        String outtradeno = UUID.randomUUID() + "";//商户系统订单号(为便于演示，此处利用UUID生成模拟订单号，商户系统内唯一)
        String tradename = commodityName.getText().toString().trim();//商品名称
        String backparams = "name=2&age=22";//商户系统回调参数
        String notifyurl = "http://101.200.13.92/notify/alipayTestNotify";//商户系统回调地址
        if (TextUtils.isEmpty(tradename)) {
            ToastUtil.showCustom(PayActivity.this, "请输入商品名称！", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(commodityMoney.getText().toString().trim())) {
            ToastUtil.showCustom(PayActivity.this, "请输入商品价格！", Toast.LENGTH_SHORT);
            return;
        }
        Long amount = Long.valueOf(commodityMoney.getText().toString().trim());//商品价格（单位：分。如1.5元传150）
        if (amount < 1) {
            ToastUtil.showCustom(PayActivity.this, "金额不能小于1分！", Toast.LENGTH_SHORT);
            return;
        }

        if (v == topay) {
            /**
             * 发起快捷支付调用
             */
            TrPay.getInstance(this).callPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
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
                    if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                        TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                        ToastUtil.showCustom(PayActivity.this, resultString, Toast.LENGTH_LONG);
                        //支付成功逻辑处理
                    } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                        ToastUtil.showCustom(PayActivity.this, resultString, Toast.LENGTH_LONG);
                        //支付失败逻辑处理
                    }
                }
            });
        } else if (v == to_alpay) {
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
                    if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                        TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                        ToastUtil.showCustom(PayActivity.this, resultString, Toast.LENGTH_LONG);
                        //支付成功逻辑处理
                    } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                        ToastUtil.showCustom(PayActivity.this, resultString, Toast.LENGTH_LONG);
                        //支付失败逻辑处理
                    }
                }
            });
        } else if (v == to_wexinpay) {
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
                    if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                        TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                        ToastUtil.showCustom(PayActivity.this, resultString, Toast.LENGTH_LONG);
                        //支付成功逻辑处理
                    } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                        ToastUtil.showCustom(PayActivity.this, resultString, Toast.LENGTH_LONG);
                        //支付失败逻辑处理
                    }
                }
            });
        }
    }
}

