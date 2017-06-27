/*
 * Create on 2017-6-9 上午10:33
 * FileName: XuanKe_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.SearchActivity;
import com.ryw.zsxs.activity.XuanKeDetailActivity;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.KCTypes;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by Mr_Shadow on 2017/6/9.
 * 选课
 */

public class XuanKe_Fragment extends BaseFragment {
    /**
     * http://api.chinaplat.com/getval_2017?Action=GetCourseTypesList&kc_types=0
     * 获取顶级
     * http://api.chinaplat.com/getval_2017?Action=GetCourseTypesList&kc_types=0&tid=4
     * tid为顶级返回
     * http://api.chinaplat.com/getval_2017?Action=GetCourseTypesList&kc_types=0&tid=6
     * tid仍然为上级返回
     */
    public static XuanKe_Fragment instance = null;
    @BindView(R.id.ib_right_top_search)
    ImageButton ibRightTopSearch;
    @BindView(R.id.ib_right_top_more)
    ImageButton ibRightTopMore;
    @BindView(R.id.layoutFooter)
    RelativeLayout layoutFooter;
    @BindView(R.id.rb_xuanke_exam)
    RadioButton rbXuankeExam;
    @BindView(R.id.rb_xuanke_work)
    RadioButton rbXuankeWork;
    @BindView(R.id.rb_xuanke_life)
    RadioButton rbXuankeLife;
    @BindView(R.id.rg_xuanke_topmenu)
    RadioGroup rgXuankeTopmenu;
    @BindView(R.id.left_tab_layout)
    VerticalTabLayout leftTabLayout;
    @BindView(R.id.gv_courslist)
    GridView gvCourslist;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    //分类类型：0（视频），1（音频），2（读书），3（文章）  默认为0
    private int kc_types = 0;
    //考试 0    工作  1    生活2
    private int kc_menu = 0;
    private ArrayList<KCTypes> kcTypes;
    private ArrayList<KCTypes> kcTypesList;
    private MyListAdapter myListAdapter;
    private String courseList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    kcMap = new HashMap<>();
                    for (KCTypes.TListBean tListBean : kcTypes.get(0).getT_list()) {
                        kcMap.put(tListBean.getName(), tListBean.getId() + "");
                    }
                    getCourseList();
                    break;
                case 1:
                    //通知左侧显示
                    initTablayout();
                    Log.e("左侧显示", kcTypes.size() + "");

                    getLastKC(kcTypesList.get(0).getT_list().get(0).getId());
                    break;

                case 2:
                    //通知右侧 显示
                    Log.e("右侧显示", "ccccccc" + lastKC.get(0).getT_list().size());
                    MyListAdapter myListAdapter = new MyListAdapter(lastKC);
                    gvCourslist.setAdapter(myListAdapter);

                  /*  if (myListAdapter == null) {
                        myListAdapter = new MyListAdapter();
                        gvCourslist.setAdapter(myListAdapter);
                    } else {
                        myListAdapter.notifyDataSetChanged();
                    }*/
                    break;
            }
        }
    };
    private MyTabAdapter myTabAdapter;
    private HashMap<String, String> kcMap;
    private ArrayList<KCTypes> lastKC;


    class MyListAdapter extends BaseAdapter {
        private final ArrayList<KCTypes> list;

        public MyListAdapter(ArrayList<KCTypes> list) {
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.get(0).getT_list().size();
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
        public View getView(final int i, View contentView, ViewGroup viewGroup) {
            View view = View.inflate(mContext, R.layout.item_gridview, null);
            Button tv = (Button) view.findViewById(R.id.tv_item);

            tv.setText(list.get(0).getT_list().get(i).getName());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("types", kc_types);
                    bundle.putString("tid", list.get(0).getT_list().get(i).getId() + "");
                    bundle.putInt("position", i);

                    bundle.putString("courseList", courseList);
                    startActivity(XuanKeDetailActivity.class, bundle);
                }
            });
            return tv;
        }
    }

    public static XuanKe_Fragment getInstance() {
        if (instance == null) {
            instance = new XuanKe_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        rgXuankeTopmenu.check(R.id.rb_xuanke_exam);
        initData();
        initEvent();
    }

    private void initTablayout() {
        myTabAdapter = new MyTabAdapter();
        if (leftTabLayout==null)
            return;
        leftTabLayout.setTabAdapter(myTabAdapter);
        leftTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                getLastKC(kcTypesList.get(0).getT_list().get(position).getId());
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }

    /**
     * 0视频-----考试----经管财 ---人力资源
     * <p>
     * <p>
     * 工作
     * 生活
     * <p>
     * 1音频
     */
    private void initEvent() {
        rgXuankeTopmenu.setOnCheckedChangeListener(new RgTopmenuCheckedChangeListener());
    }

    /**
     * 初始化数据
     */
    private void initData() {
        /**
         * 二级课程
         */
        kcTypes = new ArrayList<KCTypes>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Action", "GetCourseTypesList");
        map.put("kc_types", kc_types + "");
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                KCTypes kcType = gson.fromJson(result, KCTypes.class);
                Log.e(TAG + "initData", result);
                kcTypes.add(kcType);
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }


        });


    }


    /**
     * 获取三级课程分类详细信息
     */
    private void getCourseList() {

        kcTypesList = new ArrayList<KCTypes>();
        String position = "" + 0;
        switch (kc_menu) {
            case 0:
                //考试
                position = kcMap.get("考试");
                break;
            case 1:
                //考试
                position = kcMap.get("工作");

                break;
            case 2:
                //考试
                position = kcMap.get("生活");

                break;
        }

        String kcTypesURL = Constant.HOSTNAME + Constant.ACTION_COURSETYPES + "&kc_types=" + kc_types + "&tid=" + position;

        XutilsHttp.getInstance().get(kcTypesURL, null, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {

                Gson gson = new Gson();
                Log.e(TAG + "getCourseList   ", result);
                KCTypes kcType = gson.fromJson(result, KCTypes.class);
                kcTypesList.add(kcType);
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }


        });


    }

    public void getLastKC(int tid) {
        lastKC = new ArrayList<KCTypes>();
        HashMap<String, String> map = new HashMap<>();

        map.put("Action", "GetCourseTypesList");
        map.put("kc_types", kc_types + "");
        map.put("tid", tid + "");
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG + "LastKC    ", result);
                Gson gson = new Gson();
                courseList = result;
                KCTypes kcType = gson.fromJson(result, KCTypes.class);

                lastKC.add(kcType);
                Message msg = mHandler.obtainMessage();
                msg.what = 2;
                mHandler.sendMessage(msg);
            }


        });
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_xuanke;
    }


    @OnClick({R.id.ib_right_top_search, R.id.ib_right_top_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_right_top_search:
               startActivity(SearchActivity.class,null);
                break;
           case R.id.ib_right_top_more:
                showPopupMenu(view);
                break;
        }
    }

    /**
     * 没有用到
     *
     * @param view
     * @deprecated
     */
    private void showPopupWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(mContext);
        //popupWindow.setContentView();
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);


        popupWindow.showAsDropDown(view);

    }

    /**
     * 弹出菜单
     *
     * @param view
     */
    private void showPopupMenu(View view) {
        /**
         * 当前popupmenu显示的相对view的位置
         */
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.xuanke_poputmenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                tvTitle.setText(menuItem.getTitle() + "中心");
                switch (menuItem.getItemId()) {
                    case R.id.coursetype_video:
                        if (kc_types == 0)
                            break;
                        kc_types = 0;
                        break;
                    case R.id.coursetype_audio:
                        if (kc_types == 1)
                            break;
                        kc_types = 1;
                        break;
                    case R.id.coursetype_read:
                        if (kc_types == 2)
                            break;
                        kc_types = 2;
                        break;
                    case R.id.coursetype_article:
                        if (kc_types == 3)
                            break;
                        kc_types = 3;
                        break;
                }

                kcTypesList = null;
                kc_menu = 0;
                rgXuankeTopmenu.check(R.id.rb_xuanke_exam);
                initData();
                return true;
            }
        });

        popupMenu.show();


    }


    private class RgTopmenuCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            switch (i) {
                case R.id.rb_xuanke_exam:
                    kc_menu = 0;
                    break;
                case R.id.rb_xuanke_work:
                    kc_menu = 1;
                    break;
                case R.id.rb_xuanke_life:
                    kc_menu = 2;
                    break;

            }
            getCourseList();
        }
    }

    private class MyTabAdapter implements TabAdapter {
        @Override
        public int getCount() {

            return kcTypesList.get(0).getT_list().size();
        }

        @Override
        public TabView.TabBadge getBadge(int position) {
            return null;
        }

        @Override
        public TabView.TabIcon getIcon(int position) {
            return null;
        }

        @Override
        public TabView.TabTitle getTitle(int position) {
            return new QTabView.TabTitle.Builder()
                    .setContent(kcTypesList.get(0).getT_list().get(position).getName())
                    .setTextColor(Color.parseColor("#52ca56"), Color.parseColor("#a9a9a9"))
                    .build();

        }

        @Override
        public int getBackground(int position) {
            return 0;
        }
    }
}
