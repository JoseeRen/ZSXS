package com.ryw.zsxs.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.UserInfoBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Y.Q on 2017/6/23.
 * 中仕个人中心 个人信息页面
 */

public class UserLoginMessageActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_userloginmessage_back)
    ImageView ivUserloginmessageBack;
    @BindView(R.id.ll_userpic)
    LinearLayout llUserpic;
    @BindView(R.id.ll_usernickname)
    LinearLayout llUsernickname;
    @BindView(R.id.ll_usersex)
    LinearLayout llUsersex;
    @BindView(R.id.ll_useraddress)
    LinearLayout llUseraddress;
    @BindView(R.id.ll_useremail)
    LinearLayout llUseremail;
    @BindView(R.id.ll_userupdatepsw)
    LinearLayout llUserupdatepsw;
    @BindView(R.id.iv_userpic)
    ImageView ivUserpic;
    @BindView(R.id.tv_usernickname)
    TextView tvUsernickname;
    @BindView(R.id.tv_usernum)
    TextView tvUsernum;
    @BindView(R.id.tv_usersex)
    TextView tvUsersex;
    @BindView(R.id.tv_useraddress)
    TextView tvUseraddress;
    @BindView(R.id.tv_useremail)
    TextView tvUseremail;

    private File sdcardTempFile;
    private int crop;
    private UserInfoBean userInfoBean;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_userloginmessage;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }


    private void initData() {}

    /**
     * 获取个人信息的方法
     */
    private void getUserLoginMessage() {
        final ImageOptions options = new ImageOptions.Builder()
                .setUseMemCache(true)
                .setIgnoreGif(true)
                .build();
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action","getUserInfo");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext,LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: "+result );
                Gson gson = new Gson();
                userInfoBean = gson.fromJson(result, UserInfoBean.class);

                Log.e(TAG, "onResponse1111111: "+userInfoBean.Sex);

                x.image().loadDrawable(userInfoBean.Pic, options, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        ivUserpic.setImageDrawable(result); // 头像
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

                tvUsernickname.setText(userInfoBean.Nicename);// 昵称
                tvUsernum.setText(userInfoBean.Mobile);// 电话/账号
                tvUsersex.setText(userInfoBean.Sex);// 性别
                tvUseraddress.setText(userInfoBean.Address);// 地址
                tvUseremail.setText(userInfoBean.Email);// 邮箱
            }
        });
    }


    private void initEvent() {
        ivUserloginmessageBack.setOnClickListener(this);
        llUserpic.setOnClickListener(this);
        llUsernickname.setOnClickListener(this);
        llUsersex.setOnClickListener(this);
        llUseraddress.setOnClickListener(this);
        llUseremail.setOnClickListener(this);
        llUserupdatepsw.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_userloginmessage_back:
                // 退出个人信息页面，返回到中仕个人中心页面
                finish();
                break;
            case R.id.ll_userpic:
                // 弹出对话框 选择 拍照或者从相册中选取
                Toast.makeText(mContext,"弹出对话框选择拍照或者相册选",Toast.LENGTH_SHORT).show();
                showTouxiangDialog();
                break;
            case R.id.ll_usernickname:
                // 跳转到昵称页面，并且把数据传递传递过去
                Intent userNickname_intent = new Intent(mContext, UserNicknameActivity.class);
                userNickname_intent.putExtra("userNickname",userInfoBean.Nicename);
                startActivity(userNickname_intent);
                break;
            case R.id.ll_usersex:
                // 弹出对话框选择性别
                Toast.makeText(mContext,"弹出对话框选择男或女",Toast.LENGTH_SHORT).show();
                showSexDialog();
                break;
            case R.id.ll_useraddress:
                // 跳转到地址信息页面
                Intent userAddress_intent = new Intent(mContext, UserAddressActivity.class);
                startActivity(userAddress_intent);
                break;
            case R.id.ll_useremail:
                // 跳转到邮箱页面
                Intent userEmail_intent = new Intent(mContext, UserEmailActivity.class);
                userEmail_intent.putExtra("userEmail",userInfoBean.Email);
                startActivity(userEmail_intent);
                break;
            case R.id.ll_userupdatepsw:
                // 跳转到修改密码页面
                Intent userUpdatepsw_intent = new Intent(mContext, UserUpdatepswActivity.class);
                startActivity(userUpdatepsw_intent);
                break;
        }
    }


    /**
     * 头像对话对话框
     */
    private void showTouxiangDialog() {
        sdcardTempFile =new File("/mnt/sdcard/", "tmp_pic_" + SystemClock.currentThreadTimeMillis() + ".jpg");;
        crop = 180;

        final String[] items = { "拍照","从相册选取"};
        AlertDialog.Builder touxiangDialog =
                new AlertDialog.Builder(UserLoginMessageActivity.this);

        TextView tv = new TextView(UserLoginMessageActivity.this);
        tv.setText("设置头像");    //内容
        tv.setTextSize(22);//字体大小
        tv.setPadding(30, 20, 10, 10);//位置
        tv.setTextColor(Color.GREEN);//颜色
        touxiangDialog.setCustomTitle(tv);

        touxiangDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                switch (which){
                    case 0:
                        // 跳转到手机拍照功能
                        startCamera();
                        break;
                    case 1:
                        // 打开手机相册
                        startPhoto();
                        break;
                }
            }
        });
        touxiangDialog.create();
        touxiangDialog.show();
    }


    /**
     * 跳转到相机的方法(有问题没办法剪裁)
     */
    private void startCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", Uri.fromFile(sdcardTempFile));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", crop);// 输出图片大小
        intent.putExtra("outputY", crop);
        startActivityForResult(intent, 101);
    }


    /**
     * 跳转到相册的方法(可以剪裁也可以设置就是图片不是圆的)
     */
    private void startPhoto() {
        Intent intent1 = new Intent("android.intent.action.PICK");
        intent1.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent1.putExtra("output", Uri.fromFile(sdcardTempFile));
        intent1.putExtra("crop", "true");
        intent1.putExtra("aspectX", 1);// 裁剪框比例
        intent1.putExtra("aspectY", 1);
        intent1.putExtra("outputX", crop);// 输出图片大小
        intent1.putExtra("outputY", crop);
        startActivityForResult(intent1, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            Bitmap bmp = BitmapFactory.decodeFile(sdcardTempFile.getAbsolutePath());
            ivUserpic.setImageBitmap(bmp);
        }
    }


    /**
     * 获取性别对话框
     */
    private void showSexDialog() {
        final String[] items = { "男","女"};
        AlertDialog.Builder sexDialog =
                new AlertDialog.Builder(UserLoginMessageActivity.this);

        TextView tv = new TextView(UserLoginMessageActivity.this);
        tv.setText("性别");    //内容
        tv.setTextSize(22);//字体大小
        tv.setPadding(30, 20, 10, 10);//位置
        tv.setTextColor(Color.GREEN);//颜色
        sexDialog.setCustomTitle(tv);

        sexDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                switch (which){
                    case 0:
                        // 把获取到的性别设置给tvUsersex男
                        tvUsersex.setText(items[0]);
                        // 把修改的性别提交到服务器上
                        setSave();
                        break;
                    case 1:
                        // // 把获取到的性别设置给tvUsersex女
                        tvUsersex.setText(items[1]);
                        // 把修改的性别提交到服务器上
                        setSave();
                        break;
                }
            }
        });
        sexDialog.create();
        sexDialog.show();
}


    /**
     * 更新数据
     */
    private void setSave() {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action","saveUserInfo");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext,LoginAcitvity.USERNAME));
        hashmap.put("sex",tvUsersex.getText().toString());
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
        @Override
        public void onResponse(String result) {}});
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 首先要获取,个人信息：头像，昵称，账户，性别，地址，邮箱
        getUserLoginMessage();
    }
}
