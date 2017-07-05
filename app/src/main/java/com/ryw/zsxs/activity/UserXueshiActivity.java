package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.XueShiBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Y.Q on 2017/6/23.
 * 中仕个人中心 学识收支记录页面
 */

public class UserXueshiActivity extends BaseActivity {
    @BindView(R.id.iv_userxueshi_back)
    ImageView ivUserxueshiBack;
    @BindView(R.id.ptf_listview)
    PullToRefreshListView ptfListview;

    private List<XueShiBean.ListBean> xueshilist;
    private MyPtfAdapter myPtfAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_userxueshi;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initListener();
    }

    private void initData() {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getXueshiRecords");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: " + result);
                Gson gson = new Gson();
                XueShiBean xueShiBean = gson.fromJson(result, XueShiBean.class);
                xueshilist = xueShiBean.list;
                Log.e(TAG, "onResponse---------- "+xueshilist.size() );
                // 判断list里面数据是否为空，为空证明没有数据，弹出土司
                if (xueshilist.size()==0){
                    Toast.makeText(UserXueshiActivity.this,"当前没有数据", Toast.LENGTH_SHORT).show();

                }
                myPtfAdapter = new MyPtfAdapter();
                ptfListview.setAdapter(myPtfAdapter);
            }
        });
    }


    class MyPtfAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return xueshilist.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_shouzhi, null);
                holder=new ViewHolder();
                holder.tv_shouzhi_title=convertView.findViewById(R.id.tv_shouzhi_title);
                holder.tv_shouzhi_shijian=convertView.findViewById(R.id.tv_shouzhi_shijian);
                holder.tv_shouzhi_shouzhi=convertView.findViewById(R.id.tv_shouzhi_shouzhi);
                holder.tv_shouzhi_jifen=convertView.findViewById(R.id.tv_shouzhi_jifen);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_shouzhi_title.setText(xueshilist.get(position).getTitle());
            holder.tv_shouzhi_shijian.setText(xueshilist.get(position).getAddtime()+"");
            holder.tv_shouzhi_shouzhi.setText(xueshilist.get(position).getShouzhi()+"");
            holder.tv_shouzhi_jifen.setText(xueshilist.get(position).getMoney()+"");

            return convertView;
        }

        class ViewHolder {
            public TextView tv_shouzhi_title,
                    tv_shouzhi_shijian,
                    tv_shouzhi_shouzhi,
                    tv_shouzhi_jifen;
        }

    }


    private void initListener() {
        ivUserxueshiBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
