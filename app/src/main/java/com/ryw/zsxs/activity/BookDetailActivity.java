package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.BookCourseBean;
import com.ryw.zsxs.bean.CommentBean;
import com.ryw.zsxs.bean.CourseBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr_Shadow on 2017/6/25.
 * 读书详情页面
 */

public class BookDetailActivity extends BaseActivity {
    private static final int FAVORITERESULT = 1;
    @BindView(R.id.tv_bookdetail_activity_title)
    TextView tvBookdetailActivityTitle;
    @BindView(R.id.iv_bookdetail_activity_bookimg)
    ImageView ivBookdetailActivityBookimg;
    @BindView(R.id.tv_bookdetail_activity_bookname)
    TextView tvBookdetailActivityBookname;
    @BindView(R.id.tv_bookdetail_activity_upload)
    TextView tvBookdetailActivityUpload;
    @BindView(R.id.tv_bookdetail_activity_dianjiliang)
    TextView tvBookdetailActivityDianjiliang;
    @BindView(R.id.tv_bookdetail_activity_jifen)
    TextView tvBookdetailActivityJifen;
    @BindView(R.id.btn_bookdetail_activity_read)
    Button btnBookdetailActivityRead;
    @BindView(R.id.btn_bookdetail_activity_download)
    Button btnBookdetailActivityDownload;
    @BindView(R.id.btn_bookdetail_activity_addcollect)
    Button btnBookdetailActivityAddcollect;
    @BindView(R.id.rb_bookdetail_activity_detail)
    RadioButton rbBookdetailActivityDetail;
    @BindView(R.id.rb_bookdetail_activity_comment)
    RadioButton rbBookdetailActivityComment;
    @BindView(R.id.rg_bookdetail_activity)
    RadioGroup rgBookdetailActivity;
    @BindView(R.id.iv_bookdetail_activity_tag0)
    ImageView ivBookdetailActivityTag0;
    @BindView(R.id.iv_bookdetail_activity_tag1)
    ImageView ivBookdetailActivityTag1;
    @BindView(R.id.tv_bookdetail_activity_bookinfo)
    TextView tvBookdetailActivityBookinfo;
    @BindView(R.id.btn_bookdetail_activity_bookcatalog)
    Button btnBookdetailActivityBookcatalog;
    @BindView(R.id.ll_bookdetail_activity_detail)
    LinearLayout llBookdetailActivityDetail;

    //评论
    @BindView(R.id.lv_comment_fragment)
    PullToRefreshListView lvCommentFragment;
    @BindView(R.id.ll_empty_view_comment)
    LinearLayout llEmptyViewComment;
    @BindView(R.id.et_comment_fragment)
    EditText etCommentFragment;
    @BindView(R.id.send_comment_fragment)
    Button sendCommentFragment;
    @BindView(R.id.ll_bookdetail_activity_comment)
    LinearLayout llBookdetailActivityComment;
    private CourseBean course;
    private BookCourseBean bookCourse;
    commentAdapter myAdapter;
    private CommentBean commentBean;
    int nowPage = 1;
    String mssg;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FAVORITERESULT:
                    Toast.makeText(mContext, mssg, Toast.LENGTH_SHORT).show();
                    getDataFromNet();
                    break;

            }
        }
    };
    private Bundle bundle;

    @Override
    public int getContentViewResId() {

        return R.layout.activity_bookdetail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        //获取到的课程详细
        rgBookdetailActivity.check(R.id.rb_bookdetail_activity_detail);
        course = (CourseBean) bundle.getSerializable("data");
        Log.e(TAG, "init: " + course.toString());
        //给一些控件设置值
        setView();
        lvCommentFragment.setMode(PullToRefreshBase.Mode.DISABLED);
        //获取评论列表
        rgBookdetailActivity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_bookdetail_activity_detail:
                        ivBookdetailActivityTag0.setVisibility(View.VISIBLE);
                        ivBookdetailActivityTag1.setVisibility(View.INVISIBLE);
                        llBookdetailActivityComment.setVisibility(View.GONE);
                        llBookdetailActivityDetail.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_bookdetail_activity_comment:
                        llBookdetailActivityDetail.setVisibility(View.GONE);
                        llBookdetailActivityComment.setVisibility(View.VISIBLE);
                        ivBookdetailActivityTag0.setVisibility(View.INVISIBLE);
                        ivBookdetailActivityTag1.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        getPLList(0);
        getDataFromNet();

    }

    public void getDataFromNet() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Action", "GetCourseInfo");
        map.put("kc_types", "2");
        map.put("kc_id", course.getKc_id() + "");
        map.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        map.put("uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                bookCourse = gson.fromJson(result, BookCourseBean.class);
                tvBookdetailActivityBookinfo.setText(bookCourse.getKc_info());
                if (bookCourse.getShoucang().equals("1")) {
                    btnBookdetailActivityAddcollect.setBackgroundColor(getResources().getColor(R.color.leftTextColorSelected));

                }
            }
        });


    }

    private void getPLList(int ipage) {
        String page = null;
        if (ipage > 0) {
            page = ipage + "";
        }
        HashMap<String, String> map = new HashMap<>();

        map.put("Action", "GetCoursePL");
        map.put("cid", course.getKc_id());
        map.put("Page", page);
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: getDataFromNet" + result);
                Gson gson = new Gson();
                CommentBean commentBeans = gson.fromJson(result, CommentBean.class);
                if (commentBeans.getPage_all() == 0) {
                    lvCommentFragment.setVisibility(View.GONE);
                    llEmptyViewComment.setVisibility(View.VISIBLE);
                } else {

                    if (nowPage == 1 && nowPage <= commentBeans.getPage_all()) {
                        commentBean = commentBeans;

                        nowPage++;
                        getPLList(nowPage);
                    } else {
                        if (nowPage < commentBeans.getPage_all()) {
                            nowPage++;
                            commentBean.getPllist().addAll(commentBeans.getPllist());
                            getPLList(nowPage);
                        }

                    }
                    if (nowPage >= commentBeans.getPage_all()) {
                        rbBookdetailActivityComment.setText("评论(" + commentBean.getPllist().size() + ")");
                        lvCommentFragment.setAdapter(new commentAdapter(commentBean));
                    }
                }
            }
        });
    }


    private void setView() {
        XutilsHttp.getInstance().bindCommonImage(ivBookdetailActivityBookimg,course.getImg(),false);
        tvBookdetailActivityTitle.setText(course.getTitle());
        tvBookdetailActivityBookname.setText(course.getTitle());
        tvBookdetailActivityDianjiliang.setText("点击：" + course.getHot());
        tvBookdetailActivityUpload.setText("上传：" + course.getTeacher());
        tvBookdetailActivityJifen.setText(course.getMoney() + "积分");
        tvBookdetailActivityBookinfo.setText(course.getInfo());

    }


    @OnClick({R.id.btn_bookdetail_activity_read, R.id.btn_bookdetail_activity_download, R.id.btn_bookdetail_activity_addcollect, R.id.btn_bookdetail_activity_bookcatalog, R.id.send_comment_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bookdetail_activity_read:
                //立即阅读

                break;
            case R.id.btn_bookdetail_activity_download:
                //下载缓存
                bundle = new Bundle();
                bundle.putString("from", "download");
                bundle.putString("kcId", course.getKc_id());

                startActivity(BookCatalogActivity.class, bundle);
                break;
            case R.id.btn_bookdetail_activity_bookcatalog:
                //查看书籍目录
                bundle = new Bundle();
                bundle.putString("from", "catalog");
                bundle.putString("kcId", course.getKc_id());

                startActivity(BookCatalogActivity.class, bundle);
                break;
            case R.id.btn_bookdetail_activity_addcollect:
                //加入收藏
                if (!SpUtils.getBoolean(mContext, Constant.IS_LOGIN)) {
                    Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                String kc_id = course.getKc_id();

                //     http://api.chinaplat.com/getval_2017?Action=SaveFavorite&cid=140706&acode=02c1c3dacbbc74b67f5190df5bbcc735&Uid=18733502093
                HashMap<String, String> map = new HashMap<>();
                int flag = 0;
                if (bookCourse.getShoucang().equals("0")) {
                    //0  没有收藏  收藏操作
                    map.put("Action", "SaveFavorite");
                    btnBookdetailActivityAddcollect.setBackgroundColor(getResources().getColor(R.color.leftTextColorSelected));
                    flag = 0;
                } else {
                    map.put("Action", "DeleteFavorite");
                    flag = 1;
                    btnBookdetailActivityAddcollect.setBackgroundResource(R.drawable.buttonstyle);
                    //  删除收藏 操作
                    //1  已经收藏

                }
                map.put("cid", kc_id);
                map.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
                map.put("Uid", Constant.USERNAME);
                final int finalFlag = flag;
                XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        if (result.contains("success")) {
                            //成功
                            if (finalFlag == 0) {
                                mssg = "添加收藏成功";
                            } else {
                                mssg = "取消收藏成功";

                            }

                        } else {
                            //失败
                            if (finalFlag == 0) {
                                mssg = "您还没收藏该课程";
                            } else {
                                mssg = "已收藏或购买，不用重新收";

                            }
                        }
                        handler.sendEmptyMessage(FAVORITERESULT);

                    }
                });

                break;

            case R.id.send_comment_fragment:
                //发送评论
                String comment = etCommentFragment.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    Toast.makeText(mContext, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!SpUtils.getBoolean(mContext, Constant.IS_LOGIN)) {
                    Toast.makeText(mContext, "请先登录 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> map1 = new HashMap<>();
                map1.put("Action", "SaveCoursePL");
                map1.put("cid", course.getKc_id() + "");
                map1.put("plcontent", comment);
                map1.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
                map1.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
                XutilsHttp.getInstance().get(Constant.HOSTNAME, map1, new XutilsHttp.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        etCommentFragment.setText("");
                        Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


    class commentAdapter extends BaseAdapter {

        private final CommentBean commentBean;
        private List<CommentBean.PllistBean> plList;

        public commentAdapter(CommentBean commentBean) {
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


}
