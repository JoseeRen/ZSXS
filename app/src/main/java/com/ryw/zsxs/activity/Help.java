package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.HelpBean;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/24.
 * 个人中心-----帮助与反馈条目
 */

public class Help extends BaseActivity {

    @BindView(R.id.help_back)
    ImageView helpBack;
    @BindView(R.id.help_hotproblem)
    ListView helpHotproblem;
    private List<HelpBean.ListBean> helpCourse;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_help;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initData() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Action","getHelpList");

        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashMap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                HelpBean helpBean = gson.fromJson(result, HelpBean.class);
                helpCourse = helpBean.list;
                helpHotproblem.setAdapter(new HelpProbleAdapter());

                helpHotproblem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent(mContext,HelpXiangqing.class);

                        Bundle bundle = new Bundle();
                        bundle.putInt("position",position);

                        bundle.putInt("id",helpCourse.get(position).id);
                        intent.putExtras(bundle);

                        startActivity(intent);


                    }
                });

            }
        });
    }

    private void initEvent() {
        helpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public class HelpProbleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return helpCourse.size();
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
        public View getView(int i, View contentView, ViewGroup viewGroup) {
            View view = View.inflate(mContext, R.layout.item_helpproblem, null);
            TextView item_help_title = view.findViewById(R.id.item_help_title);

            item_help_title.setText(helpCourse.get(i).title);


            return view;
        }
    }

}
