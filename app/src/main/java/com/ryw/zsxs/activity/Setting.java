package com.ryw.zsxs.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.SpUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/24.
 */

public class Setting extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.setting_back)
    ImageView settingBack;
    @BindView(R.id.setting_bofangtixing)
    Button settingBofangtixing;
    @BindView(R.id.setting_xiazaitixing)
    Button settingXiazaitixing;
    @BindView(R.id.setting_total)
    TextView settingTotal;
    @BindView(R.id.setting_shengyu)
    TextView settingShengyu;
    @BindView(R.id.setting_clear)
    TextView settingClear;
    @BindView(R.id.setting_update)
    TextView settingUpdate;

    private boolean BOFANGISCLICK;
    private boolean XIAZAIISCLICK;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initData();
        initEvent();

    }

    private void initData() {
        BOFANGISCLICK = SpUtils.getBoolean(mContext, "bofangtixing");
        XIAZAIISCLICK = SpUtils.getBoolean(mContext,"xiazaitixing");

        if (XIAZAIISCLICK){
            settingXiazaitixing.setBackgroundResource(R.mipmap.iv_press);
            XIAZAIISCLICK = false;
        }else {
            settingXiazaitixing.setBackgroundResource(R.mipmap.iv_nomal);
            XIAZAIISCLICK = true;
        }

        if (BOFANGISCLICK){
            settingBofangtixing.setBackgroundResource(R.mipmap.iv_press);
            BOFANGISCLICK = false;
        }else {
            settingBofangtixing.setBackgroundResource(R.mipmap.iv_nomal);
            BOFANGISCLICK = true;
        }

        String availMemory = getAvailMemory();

        String totalMemory = getTotalMemory();

        settingShengyu.setText("剩余"+availMemory);
        settingTotal.setText("总量"+totalMemory+"/");

    }



    private String getAvailMemory() {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        String userMemory = Formatter.formatFileSize(getBaseContext(), mi.availMem);//将获取的内存大小规格化(可用内存)
        return userMemory;
    }

    private String getTotalMemory() {//获取adnroid所有存储内存
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");

            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue()*1024 ;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }


    private void initEvent() {
        settingBack.setOnClickListener(this);
        settingClear.setOnClickListener(this);
        settingUpdate.setOnClickListener(this);
        settingBofangtixing.setOnClickListener(this);
        settingXiazaitixing.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_back:
                finish();
                break;
            case R.id.setting_clear:
                Toast.makeText(mContext,"清除缓存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_update:
                Toast.makeText(mContext,"更新版本成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_bofangtixing:

                BoFangtixing();
                break;
            case R.id.setting_xiazaitixing:
                XiaZaitixing();
                break;
        }
    }



    private void BoFangtixing() {
        if (BOFANGISCLICK){
            settingBofangtixing.setBackgroundResource(R.mipmap.iv_press);
            BOFANGISCLICK = false;
        }else {
            settingBofangtixing.setBackgroundResource(R.mipmap.iv_nomal);
            BOFANGISCLICK = true;
        }
        SpUtils.putBoolean(mContext,"bofangtixing",BOFANGISCLICK);

    }

    private void XiaZaitixing() {
        if (XIAZAIISCLICK){
            settingXiazaitixing.setBackgroundResource(R.mipmap.iv_press);
            XIAZAIISCLICK = false;
        }else {
            settingXiazaitixing.setBackgroundResource(R.mipmap.iv_nomal);
            XIAZAIISCLICK = true;
        }
        SpUtils.putBoolean(mContext,"xiazaitixing",XIAZAIISCLICK);

    }
}
