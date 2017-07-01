/*
 * Create on 2017-6-8 下午4:12
 * FileName: Adapter.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.db.Kc_Course;
import com.ryw.zsxs.fragment.MyClass_Fragment;

import java.util.List;

/**
 * Created by Mr_Shadow on 2017/6/8.
 */
public class Adapter{

public static class RecentListViewAdapter extends BaseAdapter {
    public List<Kc_Course> list;
    public Context context;

    public RecentListViewAdapter(List<Kc_Course> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        RecentListViewAdapter.Viewholder holder = null;
        if (convertView == null) {
            holder = new RecentListViewAdapter.Viewholder();
            convertView = View.inflate(context, R.layout.item_lishijilu, null);
            holder.tv_title_lishijilu = convertView.findViewById(R.id.tv_title_lishijilu);
            holder.tv_info_lishijilu = convertView.findViewById(R.id.tv_info_lishijilu);
            holder.tv_jifen_lishijilu = convertView.findViewById(R.id.tv_jifen_lishijilu);
            holder.tv_keshi_lishijilu = convertView.findViewById(R.id.tv_keshi_lishijilu);
            holder.tv_dianjiliang_lishijilu = convertView.findViewById(R.id.tv_dianjiliang_lishijilu);
            convertView.setTag(holder);
        } else {
            holder = (RecentListViewAdapter.Viewholder) convertView.getTag();
        }
        holder.tv_title_lishijilu.setText(list.get(position).getKc_title());
        holder.tv_info_lishijilu.setText(list.get(position).getKc_info());
        holder.tv_jifen_lishijilu.setText(list.get(position).getKc_money());
        holder.tv_keshi_lishijilu.setText(list.get(position).getKeshi());
        holder.tv_dianjiliang_lishijilu.setText(list.get(position).getHot());
        return convertView;
    }

    class Viewholder {
        TextView tv_title_lishijilu, tv_info_lishijilu, tv_keshi_lishijilu,
                tv_jifen_lishijilu, tv_dianjiliang_lishijilu;
    }

}
}
