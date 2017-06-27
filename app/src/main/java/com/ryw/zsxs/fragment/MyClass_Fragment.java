

package com.ryw.zsxs.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.LoginAcitvity;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.UserInfoBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;
import com.ryw.zsxs.view.MyViewpager;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;



/**
 * Created by 赵贵 on 2017/6/9.
 * 我的课
 */

public class MyClass_Fragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.circleImageView)
    ImageView circleImageView;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.rb_hasbuy)
    RadioButton rbHasbuy;
    @BindView(R.id.rb_recent)
    RadioButton rbRecent;
    @BindView(R.id.rb_offine)
    RadioButton rbOffine;
    @BindView(R.id.rg_mine)
    RadioGroup rgMine;
    @BindView(R.id.fl_mine)
    FrameLayout flMine;
    private RadioButton bt_wenzhang;
    private RadioGroup rg_mine_item;
    private RadioButton bt_shipin;
    private int childCount;
    private MyViewpager vp_mime;
    private MyAdapter adapter;
    private List<RadioButton> list;
    private RadioButton bt_yinpin;
    private RadioButton bt_dushu;
    private int kc_types=0;

    public static MyClass_Fragment instance = null;
    private ImageOptions options;
    //private MyListViewAdapter listAdapter;

    public static MyClass_Fragment getInstance() {
        if (instance == null) {
            instance = new MyClass_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        //检查是否登录
        initLogin();
        initData();
    }

    private void initLogin() {
        boolean isLogin = SpUtils.getBoolean(mContext, Constant.IS_LOGIN);
        if (!isLogin){
            startActivity(LoginAcitvity.class,null);
            return;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_myclass;
    }
    public void creatImageOptions() {
        //设置加载过程中的图片
//                   .setLoadingDrawableId(R.mipmap.guodu_icon)
//设置加载失败后的图片
//设置使用缓存
//设置显示圆形图片
//               .setCircular(true)
//设置支持gif
        options = new ImageOptions.Builder()
//设置加载过程中的图片
//                   .setLoadingDrawableId(R.mipmap.guodu_icon)
//设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.guodu_icon)
//设置使用缓存
                .setUseMemCache(true)
//设置显示圆形图片
//               .setCircular(true)
//设置支持gif
                .setIgnoreGif(false)
                .build();

    }
    protected void initData() {
          initTop();
          initBelowData();
        DataFormNet(kc_types);
        vp_mime.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                MyClass_Fragment.this.list.get(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTop() {
        creatImageOptions();
        String acode = SpUtils.getString(mContext, "acode");
        String username = SpUtils.getString(mContext, "username");
        HashMap<String, String> map = new HashMap<>();
        map.put("Action","getUserInfo");
        map.put("acode",acode);
        map.put("Uid",username);
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson=new Gson();
                UserInfoBean bean = gson.fromJson(result, UserInfoBean.class);
                Log.e("zhaogui",bean.Nicename+"aaaaaaaaaaaaa");
                tvMine.setText(bean.Nicename);
                x.image().loadDrawable(bean.Pic, options, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        circleImageView.setImageDrawable(result);
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
                XutilsHttp.getInstance().bindCircularImage(circleImageView,bean.Pic,true);
            }
        });
    }
    private void initBelowData() {
        list = new ArrayList<RadioButton>();
        rbHasbuy.setChecked(true);
        rgMine.setOnCheckedChangeListener(this);
        View view = View.inflate(mContext, R.layout.fragment_mine_item, null);
        vp_mime = (MyViewpager) view.findViewById(R.id.vp_mime);
        bt_shipin = (RadioButton) view.findViewById(R.id.bt_shipin);
        bt_yinpin = (RadioButton) view.findViewById(R.id.bt_yinpin);
        bt_dushu = (RadioButton) view.findViewById(R.id.bt_dushu);
        bt_wenzhang = (RadioButton) view.findViewById(R.id.bt_wenzhang);
        rg_mine_item = (RadioGroup) view.findViewById(R.id.rg_mine_item);
        bt_shipin.setChecked(true);
        list.add(bt_shipin);
        list.add(bt_yinpin);
        list.add(bt_dushu);
        list.add(bt_wenzhang);
        childCount = rg_mine_item.getChildCount();
        rg_mine_item.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.bt_shipin:
                        if(rbHasbuy.isChecked()==true){
                            DataFormNet(0);
                        }
                        vp_mime.setCurrentItem(0);
                        break;
                    case R.id.bt_yinpin:
                        if(rbHasbuy.isChecked()==true){
                            DataFormNet(1);
                        }
                        vp_mime.setCurrentItem(1);
                        break;
                    case R.id.bt_dushu:
                        if(rbHasbuy.isChecked()==true){
                            DataFormNet(2);
                        }
                        vp_mime.setCurrentItem(2);
                        break;
                    case R.id.bt_wenzhang:
                        vp_mime.setCurrentItem(3);
                        break;
                }
            }
        });
        flMine.addView(view);
    }
    private void DataFormNet(int kc_types) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Action","GetCourseInfo");
        map.put("Kc_types",kc_types+"");
        map.put("kc_id","118256");//课程号
        map.put("Acode",SpUtils.getString(mContext,LoginAcitvity.ACODE));
        map.put("uid",SpUtils.getString(mContext,LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                //解析数据

                if(adapter==null){
                    adapter = new MyAdapter();
                    vp_mime.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_hasbuy:
                bt_wenzhang.setVisibility(View.GONE);
                //网络获取
                DataFormNet(0);
                bt_shipin.setChecked(true);
                childCount = 3;
                list.clear();
                list.add(bt_shipin);
                list.add(bt_yinpin);
                list.add(bt_dushu);

                adapter.notifyDataSetChanged();

                break;
            case R.id.rb_recent:
                bt_wenzhang.setVisibility(View.VISIBLE);
                bt_shipin.setChecked(true);
                childCount = 4;
                list.clear();
                list.add(bt_shipin);
                list.add(bt_yinpin);
                list.add(bt_dushu);
                list.add(bt_wenzhang);
                //读取本地的

                adapter.notifyDataSetChanged();
                break;
            case R.id.rb_offine:
                bt_wenzhang.setVisibility(View.GONE);
                bt_shipin.setChecked(true);
                childCount = 3;
                list.clear();
                list.add(bt_shipin);
                list.add(bt_yinpin);
                list.add(bt_dushu);
                //读取本地的

                adapter.notifyDataSetChanged();
                break;
        }
        vp_mime.setCurrentItem(0);
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.vp_item, null);
            ListView lv_vp_item = (ListView) view.findViewById(R.id.lv_vp_item);
           /* if(listAdapter==null){
               // listAdapter = new MyListViewAdapter();
               // lv_vp_item.setAdapter(listAdapter);
            }else{
             //   listAdapter.notifyDataSetChanged();
            }*/

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

  /*  class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {

           return listtext.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = View.inflate(mContext, R.layout.left_listview_item, null);
            Button bt = (Button) convertView.findViewById(R.id.bt_left_listView);
            bt.setText(listtext.get(position) + "");
            return convertView;
        }
    }*/


}
