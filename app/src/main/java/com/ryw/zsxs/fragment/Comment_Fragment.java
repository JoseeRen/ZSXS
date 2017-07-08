/*
 * Create on 2017-6-27 下午3:13
 * FileName: Comment_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.LoginAcitvity;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.CommentBean;
import com.ryw.zsxs.events.DataLoadComplatedEvent;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr_Shadow on 2017/6/27.
 * 视频播放页面的评论
 */

public class Comment_Fragment extends BaseFragment {
    static Comment_Fragment instance;
    @BindView(R.id.lv_comment_fragment)
    PullToRefreshListView lvCommentFragment;
    @BindView(R.id.ll_empty_view_comment)
    LinearLayout llEmptyViewComment;
    @BindView(R.id.et_comment_fragment)
    EditText etCommentFragment;
    @BindView(R.id.send_comment_fragment)
    Button sendCommentFragment;
    private static String kcId;
    private CommentBean commentBean;
    private int nowPage = 0;
    private MyAdapter myAdapter;

    public static Comment_Fragment getInstance(String kcid) {
        kcId = kcid;

        if (instance == null) {
            instance = new Comment_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {
//TODO   条目 的item   分页请求网络

        initListview();
        getDataFromNet(0);
    }

    private void initListview() {
        initText();
        lvCommentFragment.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        lvCommentFragment.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (nowPage < commentBean.getPage_all()) {
                    nowPage++;
                    getDataFromNet(nowPage);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvCommentFragment.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

    }

    private void initText() {

        // 设置下拉刷新文本
        ILoadingLayout startLabels = lvCommentFragment
                .getLoadingLayoutProxy(true, false);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String str = format.format(new Date());
        lvCommentFragment.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + str);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载中...");// 刷新时
        startLabels.setReleaseLabel("松手可刷新...");// 下来达到一定距离时，显示的提示

    }

    /**
     * @param ipage 传0 不分页
     */
    private void getDataFromNet(int ipage) {
        String page = null;
        if (ipage > 0) {
            page = ipage + "";
        }
        HashMap<String, String> map = new HashMap<>();

        map.put("Action", "GetCoursePL");
        map.put("cid", kcId);
        map.put("Page", page);
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: getDataFromNet" + result);
                Gson gson = new Gson();
                CommentBean commentBeans = gson.fromJson(result, CommentBean.class);
                commentBean = commentBeans;
                if (commentBean.getPage_all() == 0) {
                    lvCommentFragment.setVisibility(View.GONE);
                    llEmptyViewComment.setVisibility(View.VISIBLE);
                } else {
                    if (myAdapter == null) {
                        myAdapter = new MyAdapter(commentBean);
                        lvCommentFragment.setAdapter(myAdapter);
                    } else {
                        commentBean.getPllist().addAll(0, commentBeans.getPllist());
                        myAdapter.notifyDataSetChanged();
                    }
                    postComplated();
                }
            }
        });
    }

    private void postComplated() {
        Log.e(TAG, "postComplated: " + "加载完成事件");
      Bundle  bundle = new Bundle();
        bundle.putInt("flag", 3);
        EventBus.getDefault().post(new DataLoadComplatedEvent(bundle));
    }
    class MyAdapter extends BaseAdapter {

        private final CommentBean commentBean;
        private List<CommentBean.PllistBean> plList;

        public MyAdapter(CommentBean commentBean) {
            this.commentBean = commentBean;
            this.plList = commentBean.getPllist();
        }

        @Override
        public int getCount() {
            return plList.size();
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
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_comment_fragment_plv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();

            }

            holder.tvCommentFragmentComment.setText(plList.get(i).getPc_content());
            holder.tvCommentFragmentTime.setText(plList.get(i).getPl_time());
            holder.tvCommnetFragmentUname.setText(plList.get(i).getNickname());
            Log.e(TAG, "getView: " + plList.get(i).getNickname());
            XutilsHttp.getInstance().bindCircularImage(holder.ivCommentFragmentUicon, plList.get(i).getUserimg(), true);
            return view;
        }

        class ViewHolder {
            @BindView(R.id.iv_comment_fragment_uicon)
            ImageView ivCommentFragmentUicon;
            @BindView(R.id.tv_commnet_fragment_uname)
            TextView tvCommnetFragmentUname;
            @BindView(R.id.tv_comment_fragment_time)
            TextView tvCommentFragmentTime;
            @BindView(R.id.tv_comment_fragment_comment)
            TextView tvCommentFragmentComment;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comment;
    }


    @OnClick(R.id.send_comment_fragment)
    public void onViewClicked() {
        String comment = etCommentFragment.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            Toast.makeText(mContext, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!SpUtils.getBoolean(mContext, Constant.IS_LOGIN)) {
            Toast.makeText(mContext, "请先登录 ", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("Action", "SaveCoursePL");
        map.put("cid", kcId);
        map.put("plcontent", comment);
        map.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        map.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                etCommentFragment.setText("");
                Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
                getDataFromNet(0);
            }
        });
    }
}
