package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.CourseListBean;
import com.ryw.zsxs.bean.GetZTShow;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shishaoyou on 2017/6/28.
 */

public class T20172Activity extends BaseActivity {


    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.home_20172_list)
    ListView home20172List;
    @BindView(R.id.home_2017_back)
    TextView home2017Back;
    private List<GetZTShow.CourseBean> list;
    private GetZTShow getZTShowBean;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_t2017;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initdata();
        initevent();
    }

    private void initevent() {
        //返回事件
        home2017Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回上一页
                finish();

            }
        });
    }

    private void initdata() {
        Bundle bundle = getIntent().getExtras();

        getZTShowBean = (GetZTShow) bundle.getSerializable("getZTShowBean");
        String zTtitle = getZTShowBean.getZTtitle();
        list = getZTShowBean.getCourse();
        home2017Back.setText(zTtitle);
        String imgURL = getZTShowBean.getImgURL();
        XutilsHttp.getInstance().bindCommonImage(iv, imgURL, false);
        home20172List.setAdapter(new MyAdapter());
        home20172List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //历史记录存储
                String string = SpUtils.getString(mContext, Constant.HISTORYRECORD);
                if (!string.contains(list.get(i).getKc_id())) {
                    SpUtils.putString(mContext, Constant.HISTORYRECORD, string + getZTShowBean.getCourse().get(i).getKc_id() + ",");
                }
                //准备跳转页面   需要kc_id  Kc_types
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", list.get(i));
                startActivity(VideoPlayActivity.class, bundle);
            }
        });


    }
    class MyAdapter extends BaseAdapter{

        private viewholder holder;

        @Override
        public int getCount() {
            return list.size();
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
            if (convertView == null) {//优化
                convertView = View.inflate(mContext, R.layout.acitivity_t2017_list_item, null);
                 holder = new viewholder();
                holder.home_20172_picture=(ImageView) convertView.findViewById(R.id.home_20172_picture);
                holder.home_20172_tv1= (TextView) convertView.findViewById(R.id.home_20172_tv1);
                holder.home_20172_tv2= (TextView) convertView.findViewById(R.id.home_20172_tv2);
                holder.home_20172_tv3= (TextView) convertView.findViewById(R.id.home_20172_tv3);
                holder.home_20172_tv4= (TextView) convertView.findViewById(R.id.home_20172_tv4);
                convertView.setTag(holder);
            } else {
                holder = (viewholder) convertView.getTag();
            }
                //图片
            XutilsHttp.getInstance().bindCommonImage(holder.home_20172_picture, list.get(position).getImg(), false);
               //标题
            holder.home_20172_tv1.setText(list.get(position).getTitle());
                //课时
            holder.home_20172_tv2.setText(list.get(position).getKeshi()+"课时");
            //积分
            holder.home_20172_tv3.setText(list.get(position).getMoney()+"积分");
            //热度
            holder.home_20172_tv4.setText(list.get(position).getHot()+"");

            return convertView;
        }
    }
    class viewholder {
        public ImageView home_20172_picture;
        public TextView home_20172_tv1, home_20172_tv2,home_20172_tv3,home_20172_tv4;
    }
}
