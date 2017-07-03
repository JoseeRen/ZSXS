package com.ryw.zsxs.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.RegisterBean;
import com.ryw.zsxs.bean.YanZhengMaBean;
import com.ryw.zsxs.utils.MD5utils;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.register_back)
    ImageView registerBack;//返回按钮

    @BindView(R.id.register_nickname)
    EditText registerNickname;//昵称

    @BindView(R.id.register_phonenumber)
    EditText registerPhonenumber;//手机号码

    @BindView(R.id.register_et_yanzhengma)
    EditText registerEtYanzhengma;//手动输入的验证码

    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;//密码

    @BindView(R.id.iv_register_pwdlook)
    ImageView ivRegisterPwdlook;

    @BindView(R.id.btn_register)
    Button btnRegister;//注册按钮
    @BindView(R.id.register_btn_yanzhengma)
    Button registerBtnYanzhengma;//获取验证码的按钮



    private boolean ISPWDLOOK = true;
    private String yanzhengma;
    private String number;
    private String ecode;
    private TimerCount timer;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_register;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initEvent();

    }

    private void initEvent() {
        registerBack.setOnClickListener(this);
        ivRegisterPwdlook.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        registerBtnYanzhengma.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.iv_register_pwdlook:
                if (ISPWDLOOK) {
                    ivRegisterPwdlook.setImageResource(R.mipmap.zhuce_mima_kejian);
                    etRegisterPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ISPWDLOOK = false;
                } else {
                    ivRegisterPwdlook.setImageResource(R.mipmap.zhuce_mima_bukejian);
                    etRegisterPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ISPWDLOOK = true;
                }
                break;
            case R.id.btn_register:
                register();//注册
                break;
            case R.id.register_btn_yanzhengma://获取验证码

                timer = new TimerCount(60000, 1000, registerBtnYanzhengma);
                YanZhengMa();

                break;

        }
    }


    private void register() {
        number = registerPhonenumber.getText().toString().trim();
        String pwd = etRegisterPwd.getText().toString().trim();
        String nickname = registerNickname.getText().toString().trim();

        String shuruYZM = registerEtYanzhengma.getText().toString().trim();//获取验证码文本框的值

        Log.e("shuruyzm", shuruYZM + "1111111111");

        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(mContext, "请输入昵称", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(number)) {
                Toast.makeText(mContext, "请输入手机号码", Toast.LENGTH_SHORT).show();
            } else {
                String regExp = "(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[0589]\\d{7}";//正则表达式
                Pattern r = Pattern.compile(regExp);
                Matcher m = r.matcher(number);
                if (!m.matches()) {
                    Toast.makeText(mContext, "您输入的号码无效", Toast.LENGTH_SHORT).show();
                } else {


                    if (TextUtils.isEmpty(shuruYZM)) {
                        Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        shuruYZM = registerEtYanzhengma.getText().toString().trim();
                        //TODO 有问题？？？？？？？？？？？
                        Log.e("shuruyzm", shuruYZM + "22222222");
                        if (!shuruYZM.equals(yanzhengma)) {

                            //yanzhengma赋值在229行

                            Log.e("shuruyzm", shuruYZM + "333333333");
                            Toast.makeText(mContext, "手机验证码不对", Toast.LENGTH_SHORT).show();

                        } else {
                            if (TextUtils.isEmpty(pwd)) {
                                Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                            } else {
                                ecode = MD5utils.encode(pwd);
                                FromNetRegister(number, ecode);//连接服务器注册
                            }
                        }
                    }
                }
            }
        }


    }

    private void FromNetRegister(String number, String ecode) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Action", "SaveReg");
        hashMap.put("uid", number);
        hashMap.put("pwd", ecode);

        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashMap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);
                String errorMessage = registerBean.errorMessage;
                if (errorMessage == null) {
                    Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                    String TuiJianCode = registerBean.Mycode;

                    SpUtils.putString(mContext,Constant.MYCODE,TuiJianCode);//TODO 有可能有问题


                    Intent intent = new Intent(mContext, LoginAcitvity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, registerBean.errorMessage, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginAcitvity.class);
                    startActivity(intent);
                }

            }
        });
    }


    private void YanZhengMa() {//获取验证码
        number = registerPhonenumber.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(mContext, "请输入手机号码", Toast.LENGTH_SHORT).show();
        } else {
            String regExp = "(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[0589]\\d{7}";//正则表达式
            Pattern r = Pattern.compile(regExp);
            Matcher m = r.matcher(number);

            if (!m.matches()) {
                Toast.makeText(mContext, "您输入的号码无效", Toast.LENGTH_SHORT).show();
            } else {
                String randNum = getRandNum(6);//六位验证码

                Log.e("验证码", randNum);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Action", "SendSms");
                hashMap.put("mobile", number);
                hashMap.put("dotype", "reg");
                hashMap.put("codes", randNum);
                XutilsHttp.getInstance().get(Constant.HOSTNAME, hashMap, new XutilsHttp.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        if (result.contains("errorCode")) {
                            Toast.makeText(mContext, "该手机号已被注册", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            YanZhengMaBean yanZhengMaBean = gson.fromJson(result, YanZhengMaBean.class);
                            yanzhengma = yanZhengMaBean.codes;
                            timer.start();
                        }
                    }
                });

            }
        }
    }


    /*随机生成六个验证码*/
    public String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }


    /*实现按钮上面时间的变化*/
    public class TimerCount extends CountDownTimer {

        private final Button bnt;

        public TimerCount(long millisInFuture, long countDownInterval, Button bnt) {
            super(millisInFuture, countDownInterval);
            this.bnt = bnt;
        }


        @Override
        public void onTick(long l) {
            bnt.setClickable(false);
            bnt.setText(l / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            bnt.setClickable(true);
            bnt.setText("获取验证码");
        }
    }

}