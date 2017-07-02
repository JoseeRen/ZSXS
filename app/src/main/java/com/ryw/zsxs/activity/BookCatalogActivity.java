/*
 * Create on 2017-7-2 下午3:51
 * FileName: BookCatalogActivity.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.activity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.BookCatalogBean;
import com.ryw.zsxs.utils.XutilsHttp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr_Shadow on 2017/7/2.
 * 图书目录 和图书下载
 */

public class BookCatalogActivity extends BaseActivity {
    @BindView(R.id.tv_bookcatalog_bookname)
    TextView tvBookcatalogBookname;
    @BindView(R.id.lv_expand_bookcatalog)
    PullToRefreshExpandableListView lvExpandBookcatalog;
    @BindView(R.id.btn_bookcatalog_down_selectall)
    Button btnBookcatalogDownSelectall;
    @BindView(R.id.btn_bookcatalog_down_download)
    Button btnBookcatalogDownDownload;
    @BindView(R.id.ll_bookcatalog_down)
    LinearLayout llBookcatalogDown;
    private boolean download;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_bookcatalog;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        String from = bundle.getString("from");
        if ("download".equals(from)) {
            //下载
            download = true;
           lvExpandBookcatalog.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
               @Override
               public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                   Log.e(TAG, "onChildClick: "+"条目单击" );


                 return false;
               }
           });
        } else {
            //查看
            download = false;
            llBookcatalogDown.setVisibility(View.GONE);
        }
        String kcId = bundle.getString("kcId");
        /**
         * 获取数据
         */
        getDataFromNet(kcId);

    }

    private void getDataFromNet(String kcId) {
        String url = "http://api.chinaplat.com/getval_2017?Action=getBookIndexs&kc_id=" + kcId;
        XutilsHttp.getInstance().get(url, null, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                BookCatalogBean bookCatalogBean = gson.fromJson(result, BookCatalogBean.class);
                MyAdapter myAdapter = new MyAdapter(bookCatalogBean);
                lvExpandBookcatalog.getRefreshableView().setAdapter(myAdapter);
                //默认展开
                for (int i = 0; i < bookCatalogBean.getIndex_1().size(); i++) {
                    lvExpandBookcatalog.getRefreshableView().expandGroup(i);
                }
                //默认不能收回子目录
                lvExpandBookcatalog.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                        return true;
                    }
                });
            }
        });

    }

    class MyAdapter implements ExpandableListAdapter {

        private final BookCatalogBean bookCatalogBean;

        public MyAdapter(BookCatalogBean bookCatalogBean) {
            this.bookCatalogBean = bookCatalogBean;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getGroupCount() {
            return bookCatalogBean.getIndex_1().size();
        }

        @Override
        public int getChildrenCount(int i) {
            return bookCatalogBean.getIndex_1().get(i).getIndex_2().size();
        }

        @Override
        public Object getGroup(int i) {
            return bookCatalogBean.getIndex_1().get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return bookCatalogBean.getIndex_1().get(i).getIndex_2().get(i1);

        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            ViewHolderGroup holderGroup = null;
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_expand_group, null);
                holderGroup = new ViewHolderGroup(view);
                view.setTag(holderGroup);
            } else {
                holderGroup = (ViewHolderGroup) view.getTag();
            }
            holderGroup.tvExpandGroupName.setText(bookCatalogBean.getIndex_1().get(i).getTitle_1());

            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            ViewHolderChild holderChild = null;
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_expand_child, null);
                holderChild = new ViewHolderChild(view);
                view.setTag(holderChild);
            } else {
                holderChild = (ViewHolderChild) view.getTag();
            }
            holderChild.tvExpandGroupName.setText(bookCatalogBean.getIndex_1().get(i).getIndex_2().get(i1).getTitle_2());
            if (!download) {
                holderChild.cbExpandGroupDownload.setVisibility(View.INVISIBLE);
            }


            return view;


        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int i) {

        }

        @Override
        public void onGroupCollapsed(int i) {

        }

        @Override
        public long getCombinedChildId(long l, long l1) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long l) {
            return 0;
        }

        class ViewHolderGroup {
            @BindView(R.id.tv_expand_group_name)
            TextView tvExpandGroupName;

            ViewHolderGroup(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ViewHolderChild {
            @BindView(R.id.tv_expand_group_name)
            TextView tvExpandGroupName;
            @BindView(R.id.cb_expand_group_download)
            CheckBox cbExpandGroupDownload;

            ViewHolderChild(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick({R.id.btn_bookcatalog_down_selectall, R.id.btn_bookcatalog_down_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bookcatalog_down_selectall:
                break;
            case R.id.btn_bookcatalog_down_download:
                break;
        }
    }
}
