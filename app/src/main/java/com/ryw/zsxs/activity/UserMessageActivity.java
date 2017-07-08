package com.ryw.zsxs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.MessageCenterBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/6/23.
 * 中仕个人中心 消息中心
 */

public class UserMessageActivity extends BaseActivity {
    @BindView(R.id.iv_usermessage_back)
    ImageView ivUsermessageBack;
    @BindView(R.id.lv_usermessage)
    ListView lvUsermessage;
    private List<MessageCenterBean.ListBean> messagelist;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_usermessage;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initData() {
        // 获取消息中心数据
        getMessageCenterData();

    }

    private void getMessageCenterData() {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getNotice");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: "+result );
                Gson gson = new Gson();
                MessageCenterBean messageCenterBean = gson.fromJson(result, MessageCenterBean.class);
                messagelist = messageCenterBean.list;

                MessagelistAdapter messagelistAdapter = new MessagelistAdapter();
                lvUsermessage.setAdapter(messagelistAdapter);
            }
        });
    }

    class MessagelistAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return messagelist.size();
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
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_messagecenter, null);
                holder = new ViewHolder();
                holder.tv_messagecenter_title=convertView.findViewById(R.id.tv_messagecenter_title);
                holder.tv_messagecenter_time=convertView.findViewById(R.id.tv_messagecenter_time);
                convertView.setTag(holder);
            }else {

                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_messagecenter_title.setText(messagelist.get(position).getTitle());
            holder.tv_messagecenter_time.setText(messagelist.get(position).getAddtime());

            return convertView;
        }

        class ViewHolder {
            public TextView tv_messagecenter_title,
                    tv_messagecenter_time;
        }
    }


    private void initEvent() {

        // 返回按钮的点击事件
        ivUsermessageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // listview条目的点击事件
        lvUsermessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent messagecenter_intent = new Intent(UserMessageActivity.this, UserMessageCenter.class);
                messagecenter_intent.putExtra("title",messagelist.get(position).getTitle());
                messagecenter_intent.putExtra("content",messagelist.get(position).getContent());
                messagecenter_intent.putExtra("time",messagelist.get(position).getAddtime());
                startActivity(messagecenter_intent);
            }
        });


    }


}
