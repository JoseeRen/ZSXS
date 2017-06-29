package com.ryw.zsxs.fragment;

import android.app.ActivityManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.LoginAcitvity;
import com.ryw.zsxs.adapter.Adapter;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.UserInfoBean;
import com.ryw.zsxs.db.Db;
import com.ryw.zsxs.db.Kc_Course;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;
import com.ryw.zsxs.view.MyViewpager;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


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
    @BindView(R.id.title_delete)
    Button titleDelete;
    @BindView(R.id.r)
    RelativeLayout r;
    Unbinder unbinder;
    private RadioButton bt_wenzhang;
    private RadioGroup rg_mine_item;
    private RadioButton bt_shipin;
    private int childCount;
    private MyViewpager vp_mime;
    private MyAdapter adapter;
    private List<RadioButton> list;
    private RadioButton bt_yinpin;
    private RadioButton bt_dushu;
    private int kc_types = 0;

    public static MyClass_Fragment instance = null;
    private ImageOptions options;
    private HasbuyListViewAdapter hasbuyListViewAdapter;
    private OffineListViewAdapter offineListViewAdapter;
    private TextView tv_vp_item;
    private List<Kc_Course> dbAll;
    private Adapter.RecentListViewAdapter recentListViewAdapter;
    private DbManager db;


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
        if (!isLogin) {
            startActivity(LoginAcitvity.class, null);
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
        vp_mime.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initTop() {
        creatImageOptions();
        String acode = SpUtils.getString(mContext, "acode");
        String username = SpUtils.getString(mContext, "username");
        HashMap<String, String> map = new HashMap<>();
        map.put("Action", "getUserInfo");
        map.put("acode", acode);
        map.put("Uid", username);
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                UserInfoBean bean = gson.fromJson(result, UserInfoBean.class);
                Log.e("zhaogui", bean.Nicename + "aaaaaaaaaaaaa");
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
                XutilsHttp.getInstance().bindCircularImage(circleImageView, bean.Pic, true);
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
        if (adapter == null) {
            adapter = new MyAdapter();
            vp_mime.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        childCount = rg_mine_item.getChildCount();
        rg_mine_item.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.bt_shipin:
                        if (rbOffine.isChecked() == true) {
                            tv_vp_item.setVisibility(View.VISIBLE);

                        }else{
                            tv_vp_item.setVisibility(View.GONE);
                        }
                         if(rbRecent.isChecked()==true){
                             try {
                                 dbAll=db.selector(Kc_Course.class).where("flag","=",0).findAll();
                                 if(dbAll==null){
                                     dbAll=new ArrayList<Kc_Course>();
                                 }
                                 recentListViewAdapter.notifyDataSetChanged();
                             } catch (DbException e) {
                                 e.printStackTrace();
                             }
                         }

                        adapter.notifyDataSetChanged();
                        vp_mime.setCurrentItem(0);
                        break;
                    case R.id.bt_yinpin:
                        if (rbOffine.isChecked() == true) {
                            tv_vp_item.setVisibility(View.VISIBLE);
                        }else{
                            tv_vp_item.setVisibility(View.GONE);
                        }
                        if(rbRecent.isChecked()==true){
                            try {
                                dbAll=db.selector(Kc_Course.class).where("flag","=",1).findAll();
                                if(dbAll==null){
                                    dbAll=new ArrayList<>();
                                }
                                recentListViewAdapter.notifyDataSetChanged();
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        vp_mime.setCurrentItem(1);
                        break;
                    case R.id.bt_dushu:
                        if(rbRecent.isChecked()==true){
                            try {
                                dbAll=db.selector(Kc_Course.class).where("flag","=",2).findAll();
                                if(dbAll==null){
                                    dbAll=new ArrayList<Kc_Course>();
                                }
                                recentListViewAdapter.notifyDataSetChanged();
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                        vp_mime.setCurrentItem(2);
                        break;
                    case R.id.bt_wenzhang:
                        if(rbRecent.isChecked()==true){
                            try {
                                dbAll=db.selector(Kc_Course.class).where("flag","=",3).findAll();
                                if(dbAll==null){
                                    dbAll=new ArrayList<Kc_Course>();
                                }
                                recentListViewAdapter.notifyDataSetChanged();
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                        vp_mime.setCurrentItem(3);
                        break;
                }
            }
        });
        flMine.addView(view);
    }

    private void DataFormNet(int kc_types) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Action", "GetCourseInfo");
        map.put("Kc_types", kc_types + "");
        map.put("kc_id", "118256");//课程号
        map.put("Acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        map.put("uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                //解析数据


            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_hasbuy:
                tv_vp_item.setVisibility(View.GONE);
                titleDelete.setVisibility(View.GONE);
                bt_wenzhang.setVisibility(View.GONE);
                bt_shipin.setChecked(true);
                childCount = 3;
                list.clear();
                list.add(bt_shipin);
                list.add(bt_yinpin);
                list.add(bt_dushu);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rb_recent:
                tv_vp_item.setVisibility(View.GONE);
                titleDelete.setVisibility(View.VISIBLE);
                bt_wenzhang.setVisibility(View.VISIBLE);
                bt_shipin.setChecked(true);
                childCount = 4;
                list.clear();
                list.add(bt_shipin);
                list.add(bt_yinpin);
                list.add(bt_dushu);
                list.add(bt_wenzhang);
                //读取本地的
                db = x.getDb( Db.getDaoConfig());
                try {
                    dbAll = db.selector(Kc_Course.class).where("flag","=",0).findAll();
                    if (dbAll==null){
                        dbAll = new ArrayList<>();
                    }
                    Log.e("zhaogui",dbAll+"aaaaaaaa");
                } catch (DbException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.rb_offine:
                tv_vp_item.setVisibility(View.VISIBLE);
                titleDelete.setVisibility(View.VISIBLE);
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
            tv_vp_item = view.findViewById(R.id.tv_vp_item);
            tv_vp_item.setText("当前总内存"+getTotalMemory()+"/当前剩余内存"+getAvailMemory());
            if(rbOffine.isChecked()==true){
                tv_vp_item.setVisibility(View.VISIBLE);
            }else{
                tv_vp_item.setVisibility(View.GONE);
            }
            ListView lv_vp_item = (ListView) view.findViewById(R.id.lv_vp_item);
             if(rbRecent.isChecked()==true&&bt_shipin.isChecked()==true){
                 if(recentListViewAdapter==null){
                     recentListViewAdapter= new Adapter.RecentListViewAdapter(dbAll,mContext);
                     lv_vp_item.setAdapter(recentListViewAdapter);
                 }else{
                     recentListViewAdapter.notifyDataSetChanged()
;
                 }
             }
             if(rbHasbuy.isChecked()==true){
                 hasbuyListViewAdapter=new HasbuyListViewAdapter();
                 lv_vp_item.setAdapter(hasbuyListViewAdapter);
             }
             if(rbOffine.isChecked()==true){
                 offineListViewAdapter=new OffineListViewAdapter();
                 lv_vp_item.setAdapter(offineListViewAdapter);
             }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class OffineListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }




    class HasbuyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return 0;
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
            return convertView;
        }
    }
    /**
     * 获取手机内存大小
     *
     * @return
     */
    private String getTotalMemory()
    {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2 = null;
        String[] arrayOfString;
        long initial_memory = 0;
        try
        {
            FileReader localFileReader = null;
            try {
                localFileReader = new FileReader(str1);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            try {
                str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString)
            {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            try {
                localBufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Formatter.formatFileSize(mContext, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }
    /**
     * 获取当前可用内存大小
     *
     * @return
     */
    private String getAvailMemory()
    {
        ActivityManager am = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(mContext, mi.availMem);
    }
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
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
    }
}
