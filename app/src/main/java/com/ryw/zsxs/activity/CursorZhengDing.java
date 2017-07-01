package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.ZhengDingGeRenBean;
import com.ryw.zsxs.bean.ZhengDingSuoYouBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;
import com.ryw.zsxs.view.MyViewpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/24.
 * 个人中心--------课程征订
 */

public class CursorZhengDing extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.zhengdingBack)
    ImageView zhengdingBack;//返回按钮
    @BindView(R.id.myzhengding_my)
    RadioButton rb_myzhengdingMy;//个人征订按钮
    @BindView(R.id.myzhengding_quanbu)
    RadioButton rb_myzhengdingQuanbu;//全部征订按钮
    @BindView(R.id.zhengding_vp)
    MyViewpager zhengdingVp;//viewpager按钮
    @BindView(R.id.jiahao)
    ImageView jiahao;//右上角的加号
    private ArrayList<RadioButton> zhengding_rb;
    public List<ZhengDingGeRenBean.SubListBean> gerensublist;

    private ZhengDingPagerAdapter zhengDingPagerAdapter;
    private ZDGeRenLvAdapter zdGeRenLvAdapter;
    private ZDSuoYouLvAdapter zdSuoYouLvAdapter;
    private ZhengDingGeRenBean gerenCursor;
    private ZDGeRenLvAdapter.ViewHolder holder;
    private List<ZhengDingGeRenBean.SubListBean> suoyousublist;
    private int TYPE = 0;
    private ListView zdgr_vp_item_lv;
    private ListView zdsuoyou_vp_item_lv;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (zdgr_vp_item_lv != null) {

                        zdgr_vp_item_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(mContext, ZhengDingMessage.class);
                                Bundle bundle = new Bundle();                           //创建Bundle对象
                                bundle.putString("type", "GR");     //装入数据

                                bundle.putString("GRtitle", gerensublist.get(i).title);

                                bundle.putString("GRtime", gerensublist.get(i).addtime);

                                //bundle.putString("GRhf",gerensublist.get(i).hfcontent);


                                intent.putExtras(bundle);

                                startActivity(intent);
                            }
                        });
                    }

                    break;
                case 5:
                    //TODO 有问题

                    if (zdsuoyou_vp_item_lv != null) {
                        //条目的点击事件
                        zdsuoyou_vp_item_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Log.e(TAG, "onClick:条目的点击事件");

                                Intent intent = new Intent(mContext, ZhengDingMessage.class);

                                Bundle bundle = new Bundle();                           //创建Bundle对象
                                bundle.putString("type", "SY");     //装入数据
                                bundle.putString("SYtitle", suoyousublist.get(i).title);
                                bundle.putString("SYcontent", suoyousublist.get(i).content);
                                bundle.putString("SYtime", suoyousublist.get(i).addtime);
                                bundle.putString("SYhot", suoyousublist.get(i).hot);

                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                    break;
            }
        }
    };


    @Override
    public int getContentViewResId() {


        return R.layout.activity_myzhengding;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initData() {
        zhengding_rb = new ArrayList<>();//来确定创建viewpager的个数以及radiogroup的条目选中北原夏美
        zhengding_rb.add(rb_myzhengdingMy);
        zhengding_rb.add(rb_myzhengdingQuanbu);

        if (TYPE == 0) {
            FromNetGR();//获取个人征订
        } else {
            FromNetSY();
        }


        zhengdingVp.setOnPageChangeListener(new MyOnPageChangeListener());


    }

    private void FromNetGR() {

        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getSubscription");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        //hashmap.put("acode", "280d546cc83ab2140127b3a09b0ee265");
        //hashmap.put("Uid", "18733513882");

        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {

                Gson gson = new Gson();
                gerenCursor = gson.fromJson(result, ZhengDingGeRenBean.class);
                gerensublist = gerenCursor.subList;
                if (zhengDingPagerAdapter == null) {
                    zhengDingPagerAdapter = new ZhengDingPagerAdapter();
                    zdGeRenLvAdapter = new ZDGeRenLvAdapter();
                    zhengdingVp.setAdapter(zhengDingPagerAdapter);
                } else {

                    zdSuoYouLvAdapter.notifyDataSetChanged();
                    zdGeRenLvAdapter.notifyDataSetChanged();
                }

                zhengDingPagerAdapter = null;
                FromNetSY();

            }


        });
    }

    private void FromNetSY() {

        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getSubscription");
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {

            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                ZhengDingSuoYouBean suoyouCursor = gson.fromJson(result, ZhengDingSuoYouBean.class);
                suoyousublist = suoyouCursor.subList;

                if (zhengDingPagerAdapter == null) {
                    zhengDingPagerAdapter = new ZhengDingPagerAdapter();
                    zdGeRenLvAdapter = new ZDGeRenLvAdapter();
                    zdSuoYouLvAdapter = new ZDSuoYouLvAdapter();
                    zhengdingVp.setAdapter(zhengDingPagerAdapter);
                } else {
                    zdSuoYouLvAdapter.notifyDataSetChanged();
                    zdGeRenLvAdapter.notifyDataSetChanged();
                }

            }

        });


    }

    private void initEvent() {
        rb_myzhengdingMy.setOnClickListener(this);
        rb_myzhengdingQuanbu.setOnClickListener(this);
        zhengdingBack.setOnClickListener(this);
        jiahao.setOnClickListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhengdingBack:
                finish();
                break;
            case R.id.jiahao://右上角的加号

                Intent intent = new Intent(mContext, WoYaoZhengDing.class);
                startActivity(intent);
                break;
            case R.id.myzhengding_my:
                zhengdingVp.setCurrentItem(0);
                FromNetGR();
                break;
            case R.id.myzhengding_quanbu:
                zhengdingVp.setCurrentItem(1);
                FromNetSY();
                break;

        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {//viewpager页面改变的监听

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            TYPE = position;
            zhengding_rb.get(position).setChecked(true);

            if (position == 0) {
                FromNetGR();
            } else {
                FromNetSY();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    private class ZhengDingPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {

            return zhengding_rb.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;

            if (position == 0) {
                view = View.inflate(mContext, R.layout.zdgeren_vp_item, null);//viewpager填充的布局，为listview
                zdgr_vp_item_lv = view.findViewById(R.id.zdgr_vp_item_lv);

                zdgr_vp_item_lv.setAdapter(zdGeRenLvAdapter);

                //  TODO handler
                //TODO 有问题

                Message message = new Message();

                message.what = 0;

                handler.sendMessage(message);


            } else {
                view = View.inflate(mContext, R.layout.zdsuoyou_vp_item, null);
                zdsuoyou_vp_item_lv = view.findViewById(R.id.zdsuoyou_vp_item_lv);
                zdsuoyou_vp_item_lv.setAdapter(zdSuoYouLvAdapter);

                //TODO 有问题

                Message message = new Message();

                message.what = 5;

                handler.sendMessage(message);


            }

            container.addView(view);


            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    private class ZDGeRenLvAdapter extends BaseAdapter {//个人征订的lv


        @Override
        public int getCount() {
            return gerensublist.size();


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
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(mContext, R.layout.item_zdgeren_lv, null);//listveiw条目的布局
                holder = new ViewHolder();

                holder.zdgr_lv_content = view.findViewById(R.id.zdgr_lv_content);


                view.setTag(holder);

            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            holder.zdgr_lv_content.setText((position + 1) + "." + gerensublist.get(position).title);

            return view;
        }

        public class ViewHolder {
            public TextView zdgr_lv_content;
        }

    }


    public class ZDSuoYouLvAdapter extends BaseAdapter {//所有征订的lv填充

        @Override
        public int getCount() {
            return suoyousublist.size();
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_zdsy_lv, null);//listveiw条目的布局
                holder = new ViewHolder(convertView);


                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.zdsyLvContent.setText((position + 1) + "." + suoyousublist.get(position).title);
            holder.zdsyLvZhuijia.setText(suoyousublist.get(position).hot + "追加");
            holder.zdsyLvZhuijia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "onClick:button.单击事件");
                }
            });

            return convertView;
        }


        public class ViewHolder {
            @BindView(R.id.zdsy_lv_content)
            TextView zdsyLvContent;
            @BindView(R.id.zdsy_lv_zhuijia)
            Button zdsyLvZhuijia;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
