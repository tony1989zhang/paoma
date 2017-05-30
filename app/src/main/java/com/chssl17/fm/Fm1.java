package com.chssl17.fm;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chssl17.entity.LotteryEntity;
import com.chssl17.www.R;
import com.chssl17.base.BaseListFm;
import com.chssl17.base.BasePageAdapter;
import com.chssl17.entity.Pk10Entity2;
import com.chssl17.http.CM;
import com.chssl17.tools.StringUtils;
import com.chssl17.tools.XgoLog;
import com.chssl17.view.CustomPopWindow;
import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Fm1 extends BaseListFm<LotteryEntity> implements View.OnClickListener {
    private TextView mTv1;
    private TextView mTv2;
    private CustomPopWindow mCustomPopWindow;
    private int dateNum = 0;

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        mTv1 = (TextView) root.findViewById(R.id.tv1);
        mTv2 = (TextView) root.findViewById(R.id.tv2);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv1.setTextColor(getResources().getColor(R.color.title_red));
        mTv2.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected List convertToBeanList(LotteryEntity xml) {
        return xml.data;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fm1;
    }


    /* @Override
    protected List convertToBeanList(String loginBean) {
        XgoLog.d("loginBean:" + loginBean);
        HashMap<String,Pk10EntityLists> map = new HashMap<>();
        Pk10EntityLists pk10EntityLists = new Pk10EntityLists();
        map.put("Pk10EntityLists",pk10EntityLists);
        Pk10EntityLists pk10EntityLists2 = (Pk10EntityLists) StringUtils.xml2Bean(map, loginBean);
        XgoLog.e("o:" + pk10EntityLists2.toString());
        return pk10EntityLists2.getPk10EntityArrayList();
    }*/

    @Override
    protected BasePageAdapter initAdapter() {
        return new FmAdapter();
    }

    @Override
    protected boolean isSwipeRefreshLayoutEnabled() {
        return true;
    }

    @Override
    protected int getSizeInPage() {
        return 0;
    }

    @Override
    protected Cancelable initRequest(int start) {
        return CM.getInstance().PaoMa(this);
    }

    @Override
    protected boolean isPageEnabled() {
        return false;
    }

    @Override
    protected boolean isDataGot() {
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv1:
                dateNum = 0;
                mTv1.setTextColor(getResources().getColor(R.color.title_red));
                mTv2.setTextColor(getResources().getColor(R.color.white));
                onRefresh();
                break;
            case R.id.tv2:
                showPopMenu();
               mTv1.setTextColor(getResources().getColor(R.color.white));
                mTv2.setTextColor(getResources().getColor(R.color.title_red));

                break;
        }
    }

    public String[] getBackDate() {
        int backDate[] = {0, -1, -2, -3, -4};
        String[] backDateStr = new String[5];
        for (int i = 0;i < 5;i++){
            Date date = new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, backDate[i]);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //这个时间就是日期往后推一天的结果
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            backDateStr[i] = dateString;
        }
        return backDateStr;
    }


    class FmAdapter extends BasePageAdapter {
        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = View.inflate(viewGroup.getContext(), R.layout.item_quote, null);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_item, viewGroup, false);
            return new ItemViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            if (viewHoder instanceof ItemViewHodler) {
                final ItemViewHodler holder = (ItemViewHodler) viewHoder;
                LotteryEntity.DataBean item = (LotteryEntity.DataBean) mItems.get(position);
                holder.mTv_1.setText("" + item.open_date);
                holder.mTv_31.setText("" + item.num1);
                setTvBg(item.num1, holder.mTv_31);
                holder.mTv_32.setText("" + item.num2);
                setTvBg(item.num2, holder.mTv_32);
                holder.mTv_33.setText("" + "" + item.num3);
                setTvBg(item.num3, holder.mTv_33);
                holder.mTv_34.setText("" + item.num4);
                setTvBg(item.num4, holder.mTv_34);
                holder.mTv_35.setText("" +  item.num5);
                setTvBg(item.num5, holder.mTv_35);
                holder.mTv_36.setText("" + item.num6);
                setTvBg(item.num6, holder.mTv_36);
                holder.mTv_37.setText("" + item.num7);
                setTvBg(item.num7, holder.mTv_37);
                holder.mTv_38.setText("" + item.num8);
                setTvBg(item.num8, holder.mTv_38);
                holder.mTv_39.setText("" + item.num9);
                setTvBg(item.num9, holder.mTv_39);
                holder.mTv_310.setText("" + item.num10);
                setTvBg(item.num10, holder.mTv_310);
                int itemSum = item.num1 + item.num2;
                String bigOrSmail = "小";
                int txColor  = Color.WHITE;
                int txColor2  = Color.WHITE;
                if (itemSum > 11)
                {   bigOrSmail = "大";
                    txColor = Color.RED;
                }
                String shuangOrDan = "单";
                if (itemSum % 2 == 0)
                {   shuangOrDan = "双";
                    txColor2 = Color.RED;
                }

                String gyhStr = itemSum + " " + bigOrSmail + " " + shuangOrDan;
                SpannableString ss = new SpannableString(gyhStr);
                ss.setSpan(new ForegroundColorSpan(txColor), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(txColor2), 4, gyhStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.m_tv_gyh.setText(ss);

                String longhu1 = " 虎";
                String longhu2 = " 虎";
                String longhu3 = " 虎";
                String longhu4 = " 虎";
                String longhu5 = " 虎";
                int colorlonghu1 = Color.WHITE;
                int colorlonghu2 = Color.WHITE;
                int colorlonghu3 = Color.WHITE;
                int colorlonghu4 = Color.WHITE;
                int colorlonghu5 = Color.WHITE;
                if (item.num1 - item.num10 >0 ) {
                    longhu1 = " 龙";
                    colorlonghu1 = Color.RED;
                }
                if (item.num2 - item.num9 > 0) {
                    longhu2 = " 龙";
                    colorlonghu2 = Color.RED;
                }
                if (item.num3 - item.num8 > 0){
                    longhu3 = " 龙";
                colorlonghu3 = Color.RED;
                }
                if (item.num4 - item.num7 > 0){
                    longhu4 = " 龙";
                colorlonghu4 = Color.RED;
                }
                if (item.num5 - item.num6 > 0)
                {    longhu5 = " 龙";
                colorlonghu5 = Color.RED;
                }


                String htmlLh = "<font size=\"3\" color=\"" +
                        colorlonghu1 +
                        "\">" +
                        longhu1 +
                        "</font><font size=\"3\" color=\"" +
                        colorlonghu2 +
                        "\">" +
                        longhu2 +
                        "</font>"+
                        "<font size=\"3\" color=\"" +
                        colorlonghu3 +
                        "\">" +
                         longhu3 +
                        "</font>"+
                        "<font size=\"3\" color=\"" +
                        colorlonghu4 +
                        "\">" +
                       longhu4 +
                        "</font>"+
                        "<font size=\"3\" color=\"" +
                       colorlonghu5 +
                        "\">" +
                        longhu5 +
                        "</font>";

                holder.m_tv_lh.setText(Html.fromHtml(htmlLh));


                holder.m_ll_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.m_ll_child.getVisibility() == LinearLayout.GONE)
                        {
                            holder.m_ll_child.setVisibility(View.VISIBLE);
                        }else{
                            holder.m_ll_child.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }

        public void setTvBg(int i, TextView tv) {
            switch (i) {
                case 1:
                    tv.setBackgroundResource(R.drawable.shape_solid_purple);
                    break;
                case 2:
                    tv.setBackgroundResource(R.drawable.shape_solid_blue);
                    break;
                case 3:
                    tv.setBackgroundResource(R.drawable.shape_solid_cyan);
                    break;
                case 4:
                    tv.setBackgroundResource(R.drawable.shape_solid_cygreen);
                    break;
                case 5:
                    tv.setBackgroundResource(R.drawable.shape_solid_cyan);
                    break;
                case 6:
                    tv.setBackgroundResource(R.drawable.shape_solid_dgreen);
                    break;
                case 7:
                    tv.setBackgroundResource(R.drawable.shape_solid_dcyan);
                    break;
                case 8:
                    tv.setBackgroundResource(R.drawable.shape_solid_dyellow);
                    break;
                case 9:
                    tv.setBackgroundResource(R.drawable.shape_solid_dblue);
                    break;
                case 10:
                    tv.setBackgroundResource(R.drawable.shape_solid_titlecolor);
                    break;
            }
        }

        class ItemViewHodler extends RecyclerView.ViewHolder {

            @ViewInject(R.id.tv_1)
            public TextView mTv_1;
            @ViewInject(R.id.tv_31)
            public TextView mTv_31;
            @ViewInject(R.id.tv_32)
            public TextView mTv_32;
            @ViewInject(R.id.tv_33)
            public TextView mTv_33;
            @ViewInject(R.id.tv_34)
            public TextView mTv_34;
            @ViewInject(R.id.tv_35)
            public TextView mTv_35;
            @ViewInject(R.id.tv_36)
            public TextView mTv_36;
            @ViewInject(R.id.tv_37)
            public TextView mTv_37;
            @ViewInject(R.id.tv_38)
            public TextView mTv_38;
            @ViewInject(R.id.tv_39)
            public TextView mTv_39;
            @ViewInject(R.id.tv_310)
            public TextView mTv_310;
            @ViewInject(R.id.ll_child)
            public LinearLayout m_ll_child;
            @ViewInject(R.id.tv_gyh)
            public TextView m_tv_gyh;
            @ViewInject(R.id.tv_lh)
            public TextView m_tv_lh;
            @ViewInject(R.id.ll_parent)
            public LinearLayout m_ll_parent;



            public ItemViewHodler(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
            }
        }
    }

    private void showPopMenu(){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_menu,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
         mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .create()
                .showAsDropDown(mTv2,0,20);


    }
    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
       final String[] backDate = getBackDate();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = backDate[0];
                        dateNum = 0;
                        break;
                    case R.id.menu2:
                        showContent = backDate[1];
                        dateNum = 1;
                        break;
                    case R.id.menu3:
                        showContent = backDate[2];
                        dateNum = 2;
                        break;
                    case R.id.menu4:
                        showContent = backDate[3];
                        dateNum = 3;
                        break;
                    case R.id.menu5:
                        showContent = backDate[4] ;
                        dateNum = 4;
                        break;
                }
                onRefresh();
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
        TextView tv1 = (TextView) contentView.findViewById(R.id.item_tv_1);
        TextView tv2 = (TextView) contentView.findViewById(R.id.item_tv_2);
        TextView tv3 = (TextView) contentView.findViewById(R.id.item_tv_3);
        TextView tv4 = (TextView) contentView.findViewById(R.id.item_tv_4);
        TextView tv5 = (TextView) contentView.findViewById(R.id.item_tv_5);

        tv1.setText("" + backDate[0]);
        tv2.setText("" + backDate[1]);
        tv3.setText("" + backDate[2]);
        tv4.setText("" + backDate[3]);
        tv5.setText("" + backDate[4]);

    }
}
