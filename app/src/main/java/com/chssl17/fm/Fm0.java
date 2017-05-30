package com.chssl17.fm;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chssl17.MainActivity;
import com.chssl17.entity.BannerEntity;
import com.chssl17.entity.PaoMaEntity;
import com.chssl17.view.GlideImageLoader;
import com.chssl17.view.MarqueeTextView;
import com.chssl17.www.R;
import com.chssl17.base.BaseFm;
import com.chssl17.entity.KaiJiangEntity;
import com.chssl17.http.CM;
import com.chssl17.http.ComCb;
import com.chssl17.module.lottory.LottoTrendActivity;
import com.chssl17.module.news.NewsAct;
import com.chssl17.module.plan.PlanAct;
import com.chssl17.module.suoshui.TwoStartAct;
import com.chssl17.module.suoshui.WebAct;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import com.chssl17.module.zoushi.chartView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
@ContentView(R.layout.layout_fm0)
public class Fm0 extends BaseFm implements OnBannerListener{
    @ViewInject(R.id.tv_1)
    private TextView m_tv_1; //第926期最新开奖;
//    @ViewInject(R.id.checkbox_tags_view_group)
//    private com.chssl17.module.suoshui.CheckBoxTagViewGroup3 m_checkbox_tags_view_group;
    @ViewInject(R.id.tv_2)
    private TextView m_tv_2; //第926期开奖时间剩余;
    @ViewInject(R.id.time)
    private TextView m_time;
    @ViewInject(R.id.banner)
    private Banner banner;
    @ViewInject(R.id.iv_sliding)
    private ImageView m_iv_sliding;
    //    private com.xlidfwsscai525.view.TimeView m_time;
    private static final String M_RULE_LSSC = "http://www.zjt-cp.com/html/rule/rule_Lssc.html?os=android&appVersion=6.2.1705&appName=Android_ssczs&visitFrom=outside";
    private static final String M_LOTTERYCATEGORY = "http://www.zjt-cp.com/html/chengben.html?lotteryCategory=Lssc&os=android&appVersion=6.2.1705&appName=Android_ssczs&visitFrom=outside";
    private long recLen;
    SimpleDateFormat formatter;
    MediaPlayer mMediaPlayer = null;
    Vibrator vibrator = null;
    NotificationManager m_Manager = null;
    PendingIntent m_PendingIntent;
    Notification m_Notification = null;
    ArrayList<String> bannerLists = new ArrayList<>();
    ArrayList<String> bannerPicUrlLists = new ArrayList<>();
    ArrayList<String> bannerTitleLists = new ArrayList<>();
    @Override
    public void initView(View view) {
        super.initView(view);
        if (formatter == null) {
            formatter = new SimpleDateFormat("mm:ss");
        }
        showProgressDailog();
        CM.getInstance().kaijiangpaoma(new FmComCb());
        BmobQuery<BannerEntity> bmobQuery = new BmobQuery<>();
        bannerLists.clear();
        bannerPicUrlLists.clear();
        bannerTitleLists.clear();
        bmobQuery.findObjects(new FindListener<BannerEntity>() {
            @Override
            public void done(List<BannerEntity> list, BmobException e) {
                if(e==null){
                   for (int i = 0;i < list.size();i++)
                    {
                        bannerLists.add(list.get(i).pic);
                        bannerPicUrlLists.add(list.get(i).picUrl);
                        bannerTitleLists.add(list.get(i).title);
                    }
                    banner.setImages(bannerLists)
                            .setBannerTitles(bannerTitleLists)
                            .setBannerAnimation(AccordionTransformer.class)
//                            .setBannerStyle(BannerConfig.C)
                            .setImageLoader(new GlideImageLoader())
                            .setOnBannerListener(Fm0.this)
                            .start();
                }else{
                }
            }
        });
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(), com.chssl17.WebAct.class);
        intent.putExtra(WebAct.WEB_EXT_TITLE,"shishiz");
        intent.putExtra(WebAct.WEB_EXT_URL,bannerPicUrlLists.get(position));
        startActivity(intent);
        Toast.makeText(getActivity(), "dianjile :" + position, Toast.LENGTH_SHORT).show();
    }




    class FmComCb extends ComCb<PaoMaEntity> {
        @Override
        public void onFinished() {
            super.onFinished();
            dismissProgressDialog();
        }

        @Override
        public void onSuccess(final PaoMaEntity result) {
            super.onSuccess(result);
            setFmComCb(result);
        }
    }

    private void setFmComCb(PaoMaEntity result) {
        String result1 = result.current.awardNumbers;
//        String[] split = result1.split(",");
//        ArrayList<String> arrayList = new ArrayList<>();
//        for (String s : split) {
//            arrayList.add(s);
//        }
//        m_checkbox_tags_view_group.update(arrayList);
        m_tv_1.setText("第" + result.current.periodDate + "期开奖:" + result1);
        m_tv_2.setText("第" + result.next.periodDate + "期开奖时间剩余");

           /* long l = Long.valueOf(result.items.time);
            String nextTimeStr = result.items.next.date + " " + result.items.next.time;
            long l1 = StringUtils.dateToStamp(nextTimeStr);
            long l2 = l1 - l;
            XgoLog.e("l2:" + l2);//l2/1000*/
            recLen = result.next.awardTimeInterval;//(l2 / 1000);
            if (recLen < 0)
            {
                showProgressDailog("正在开奖中，请稍等");
                showRingToneToast();
                CM.getInstance().kaijiangpaoma(new FmComCb());
            }else {
                timer.schedule(task, 1000, 1000);
                if (mMediaPlayer != null)
                mMediaPlayer.stop();
                if (vibrator != null)
                vibrator.cancel();
                if (m_Manager != null)
                m_Manager.cancelAll();
            }
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            recLen--;
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);

        }
    };

    Handler handler =  new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    long  min = recLen/60;
                    long  sec = recLen - min*60;
                    String minStr = "";
                    String secStr = "";
                    if (min >= 10){
                        minStr = "" + min;
                    }else{
                        minStr = "0" + min;
                    }
                    if (sec >= 10)
                    {
                        secStr = "" + sec;
                    }else{
                        secStr = "0" + sec;
                    }

                    if (recLen % 5 == 0)
//                     m_tv_1.startFor0();

                    if (recLen > 0) {
                        m_time.setText("" + minStr + ":" + secStr);
                    }else{
                        m_time.setText("正在开奖");
                        CM.getInstance().kaijiangpaoma(new FmComCb());
                        break;
                    }
                    break;
            }
        }
    };

    public void showRingToneToast() {
        // 使用来电铃声的铃声路径
        // 如果为空，才构造，不为空，说明之前有构造过
        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.setDataSource(getContext(), uri);
            mMediaPlayer.setLooping(true); //循环播放
            AssetFileDescriptor fileDescriptor = getActivity().getAssets().openFd("zhongjiangle.mp3");
            mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),
                    fileDescriptor.getStartOffset());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
            // 等待3秒，震动3秒，从第0个索引开始，一直循环
            vibrator.vibrate(new long[]{1000, 1000}, 0);

            m_Manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            //m_Manager.cancel(1023); //取消
            m_PendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(), MainActivity.class), 0);
            m_Notification = new Notification();
            m_Notification.icon = R.mipmap.ic_launcher; //设置图标
            m_Notification.tickerText = "要开奖啦。。准备好";
            m_Manager.notify(1023, m_Notification); // 这个notification 的 id 设为1023
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Event({R.id.ll_00,
            R.id.ll_01,
            R.id.ll_02,
            R.id.ll_03,
            R.id.ll_plan,
            R.id.ll_cbsc,
            R.id.ll_knowledge,
            R.id.ll_donar,
            R.id.ll_news,
            R.id.iv_sliding
    })
    private void OnFm0OnClick(View v) {

        switch (v.getId()) {
            case R.id.ll_00:
                startActivity(new Intent(getActivity(), TwoStartAct.class));
                break;
            case R.id.ll_01:
                MainActivity m = (MainActivity) getActivity();
                m.setCurrent(1);
                break;
            case R.id.ll_02:
                MainActivity m2 = (MainActivity) getActivity();
                m2.setCurrent(4);
                break;
            case R.id.ll_03:
                startActivity(new Intent(getActivity(), LottoTrendActivity.class));
                break;
            case R.id.ll_plan:
                startActivity(new Intent(getActivity(), PlanAct.class));
                break;
            case R.id.ll_cbsc:
                WebAct.onNewInstance(getActivity(), M_LOTTERYCATEGORY, getResources().getString(R.string.layout_fm0_tv_text_05));
                break;
            case R.id.ll_knowledge:
                WebAct.onNewInstance(getActivity(), M_RULE_LSSC, getResources().getString(R.string.layout_fm0_tv_title_06));
                break;
            case R.id.ll_donar:
                startActivity(new Intent(getActivity(), chartView.class));
//            startActivity(new Intent(getActivity(), RealmDatabaseActivityLine.class));
                break;
            case R.id.ll_news:
                startActivity(new Intent(getActivity(), NewsAct.class));
                break;
            case R.id.iv_sliding:
              MainActivity mainAct = (MainActivity) getActivity();
                mainAct.openNavDrawer();
                break;
        }
    }
}
