package com.ryw.zsxs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.db.Kc_Course;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gui on 2017/7/1.
 */

public  class RecentListViewAdapter extends BaseAdapter {
    public List<Kc_Course> list;
    public Context context;
    public boolean flag;
    private  static HashMap<Integer,Boolean> isSelected;

    public void setList(List<Kc_Course> list) {
        this.list = list;
    }

    public RecentListViewAdapter(List<Kc_Course> list, Context context) {
        this.list = list;
        this.context = context;
        isSelected=new HashMap<>();
        initDate();
    }

    public HashMap<Integer, Boolean> getIsSelected() {

        return isSelected;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }

    private void initDate() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i,false);
        }
    }


    @Override
    public int getCount() {

         return list == null ? 0 : list.size();
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
     Viewholder holder = null;
        if (convertView == null) {
            holder = new Viewholder();
            convertView = View.inflate(context, R.layout.item_lishijilu, null);
            holder.tv_title_lishijilu = convertView.findViewById(R.id.tv_title_lishijilu);
            holder.tv_info_lishijilu = convertView.findViewById(R.id.tv_info_lishijilu);
            holder.tv_jifen_lishijilu = convertView.findViewById(R.id.tv_jifen_lishijilu);
            holder.tv_keshi_lishijilu = convertView.findViewById(R.id.tv_keshi_lishijilu);
            holder.tv_dianjiliang_lishijilu = convertView.findViewById(R.id.tv_dianjiliang_lishijilu);
            holder.ck_lishijilu=convertView.findViewById(R.id.ck_lishijilu);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        holder.tv_title_lishijilu.setText(list.get(position).getKc_title());
        holder.tv_info_lishijilu.setText(list.get(position).getKc_info());
        holder.tv_jifen_lishijilu.setText(list.get(position).getKc_money());
        holder.tv_keshi_lishijilu.setText(list.get(position).getKeshi());
        holder.tv_dianjiliang_lishijilu.setText(list.get(position).getHot());
        if(flag){
            holder.ck_lishijilu.setVisibility(View.VISIBLE);
        }else{
            holder.ck_lishijilu.setVisibility(View.GONE);
        }
        final Kc_Course course = list.get(position);
         holder.ck_lishijilu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

             }
         });
        // 根据isSelected来设置checkbox的选中状况
        holder.ck_lishijilu.setChecked(getIsSelected().get(position));

        return convertView;
    }

    class Viewholder {
        TextView tv_title_lishijilu, tv_info_lishijilu, tv_keshi_lishijilu,
                tv_jifen_lishijilu, tv_dianjiliang_lishijilu;
        CheckBox ck_lishijilu;
    }

}
