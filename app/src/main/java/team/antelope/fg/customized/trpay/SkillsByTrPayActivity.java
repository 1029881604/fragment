package team.antelope.fg.customized.trpay;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import team.antelope.fg.BuildConfig;
import team.antelope.fg.R;
import team.antelope.fg.customized.activity.SkillDetails;
import team.antelope.fg.customized.constant.AccessNetConst;
import team.antelope.fg.customized.dialog.DialogButtonListener;
import team.antelope.fg.customized.dialog.DialogUtil;
import team.antelope.fg.customized.util.AppUtils;
import team.antelope.fg.db.dao.IUserDao;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;
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

    private Properties mProp;
    private Long user_id;   //当前登录用户id
    private Long uid_s;
    private Long skillid;
    private String content;
    private String img;
    private String skilltype;
    private String skilltitle;
    private String skillprice;

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
        uid_s = intent.getLongExtra("uid_s",0);
        skillid = intent.getLongExtra("skillid",0);
        content = intent.getStringExtra("content");
        img = intent.getStringExtra("img");
        skilltype = intent.getStringExtra("skilltype");
        skilltitle = intent.getStringExtra("title");
        skillprice = intent.getStringExtra("price");
        Log.i("alipay", "获得到的————"+uid_s+skillid+content+img+skilltype+skilltitle);
        commodityName.setText(skilltitle);
        commodityMoney.setText(skillprice);

        /**
         * @说明 获取当前登录用户的id
         * @创建日期 2018/5/18 下午8:11
         */
        IUserDao userDao = new UserDaoImpl(SkillsByTrPayActivity.this);
        User user = userDao.queryAllUser().get(0);
        user_id = user.getId();     //当前登录用户id
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
                            TrPay.getInstance((Activity) context).closePayView();       //关闭支付页面
                            ToastUtil.showCustom(SkillsByTrPayActivity.this, resultString, Toast.LENGTH_LONG);
                            //支付成功逻辑处理
                            mProp = PropertiesUtil.getInstance();
                            Log.i("alipay111", "1111"+skillid+user_id+uid_s+skillid+skilltitle+skillprice);
                            Log.i("alipay111",mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH) +mProp.getProperty(AccessNetConst.ADDORDERENDPATH));
                            sendRequestWithOkHttp("1");

                        } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {        //2：支付失败回调
                            ToastUtil.showCustom(SkillsByTrPayActivity.this, resultString, Toast.LENGTH_LONG);
                            //支付失败逻辑处理
                            mProp = PropertiesUtil.getInstance();
                            Log.i("alipay222", "2222"+skillid+user_id+uid_s+skillid+skilltitle+skillprice);
                            Log.i("alipay222",mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH) +mProp.getProperty(AccessNetConst.ADDORDERENDPATH));
                            sendRequestWithOkHttp("0");

                        }
                    }
                });
                break;
            }//case11111



            case R.id.to_wechatpay: {
                /**
                 * 发起微信支付调用
                 */
                if (AppUtils.isAppInstalled(this, "com.tencent.mm")) {
                    /**
                     * @说明 用户已安装微信的逻辑
                     * @创建日期 2018/5/16 上午9:04
                     */
                    TrPay.getInstance(this).callWxPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
                        /**
                         * 支付完成回调
                         *
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
                                TrPay.getInstance((Activity) context).closePayView();       //关闭支付页面
                                ToastUtil.showCustom(SkillsByTrPayActivity.this, resultString, Toast.LENGTH_LONG);
                                //支付成功逻辑处理
                                mProp = PropertiesUtil.getInstance();
                                Log.i("alipay111", "1111"+skillid+user_id+uid_s+skillid+skilltitle+skillprice);
                                Log.i("alipay111",mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH) +mProp.getProperty(AccessNetConst.ADDORDERENDPATH));
                                sendRequestWithOkHttp("1");

                            } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                                ToastUtil.showCustom(SkillsByTrPayActivity.this, resultString, Toast.LENGTH_LONG);
                                //支付失败逻辑处理
                                mProp = PropertiesUtil.getInstance();
                                Log.i("alipay222", "2222"+skillid+user_id+uid_s+skillid+skilltitle+skillprice);
                                Log.i("alipay222",mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH) +mProp.getProperty(AccessNetConst.ADDORDERENDPATH));
                                sendRequestWithOkHttp("0");

                            }
                        }
                    });
                }//if

                else{
                    /**
                     * @说明 用户未安装微信的逻辑
                     * @创建日期 2018/5/16 上午9:05
                     */

                    ToastUtil.showCustom(SkillsByTrPayActivity.this, "未安装微信，请先安装微信", Toast.LENGTH_LONG);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SkillsByTrPayActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("是否下载微信？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDownloadProgressDialog(SkillsByTrPayActivity.this);
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showCustom(SkillsByTrPayActivity.this, "用户取消下载", Toast.LENGTH_LONG);
//                            mProp = PropertiesUtil.getInstance();
//                            Log.i("alipay", "1111"+skillid+user_id+uid_s+skillid+skilltitle+skillprice);
//                            Log.i("alipay",mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH) +mProp.getProperty(AccessNetConst.ADDORDERENDPATH));
//                            sendRequestWithOkHttp();
                        }
                    });
                    dialog.show();


                }//else

            }//case2222

        }//switch

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

    /**
    * @说明 建立连接
    * @创建日期 2018/5/20 下午10:43
    */
    private void sendRequestWithOkHttp(final String ispay){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = null;
                try {
                    url = mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH) +
                            mProp.getProperty(AccessNetConst.ADDORDERENDPATH);
//                                    OkHttpClient client = new OkHttpClient();
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();
                    //POST方式
                    RequestBody requestBody = new FormBody.Builder()
                            .add("uid", String.valueOf(user_id))
                            .add("uid_s", String.valueOf(uid_s))
                            .add("skillid", String.valueOf(skillid))
                            .add("title", skilltitle)
                            .add("content", content)
                            .add("img", img)
                            .add("skilltype", skilltype)
                            .add("price", skillprice)
                            .add("ispay", ispay)
                            .add("isdelete", "0")
                            .add("iscomment", "0")
                            .build();
                    Log.i("trpay111", "1111");
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
                    Log.i("trpay111", "成功"+"userid:"+user_id+"skillid:"+skillid);
                } else
                    Log.i("trpay111", "失败");
            }
        }).start();
    }//sendRequest

    /**
     * @说明 下载任务
     * @创建日期 2018/5/16 上午11:04
     */
    private void showDownloadProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在下载...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        String downloadUrl = "http://dldir1.qq.com/weixin/android/weixin666android1300.apk";
        new DownloadAPK(progressDialog).execute(downloadUrl);
    }

    /**
     * 下载APK的异步任务

     */

    private class DownloadAPK extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        File file;

        public DownloadAPK(ProgressDialog progressDialog) {
            this.progressDialog = progressDialog;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                int fileLength = conn.getContentLength();
                bis = new BufferedInputStream(conn.getInputStream());
                String fileName = Environment.getExternalStorageDirectory().getPath() + "/download/new.apk";

                file = new File(fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            openFile(file);
            progressDialog.dismiss();
        }

        private void openFile(File file) {
//
////参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//            Uri apkUri =
//                    FileProvider.getUriForFile(SkillsByTrPayActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
//
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            // 由于没有在Activity环境下启动Activity,设置下面的标签
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//            SkillsByTrPayActivity.this.startActivity(intent);



            if (file!=null){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // 由于没有在Activity环境下启动Activity,设置下面的标签
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                SkillsByTrPayActivity.this.startActivity(intent);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }


        }
    }
}
