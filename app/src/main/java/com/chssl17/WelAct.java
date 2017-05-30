package com.chssl17;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chssl17.adapter.VpAdapter;
import com.chssl17.www.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 作用：欢迎页面
 */
@ContentView(R.layout.act_wel)
public class WelAct extends Activity{
    @ViewInject(R.id.viewpager)
    private ViewPager m_viewpager;
    @ViewInject(R.id.iv_image)
    private LinearLayout m_iv_image;
    @ViewInject(R.id.layout_btn)
    private LinearLayout m_layout_btn;
    @ViewInject(R.id.btn_ok)
    private Button m_btn_ok;
    private VpAdapter adapter;
    private Intent intent = new Intent();
    private ImageView[] imageViews;
    private final List<View> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setAdapter();
    }
    private void setAdapter() {
        data.clear();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        ImageView imageView = new ImageView(this);
        InputStream is = getResources().openRawResource(R.raw.welcome_01);
        Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
        imageView.setImageBitmap(bm);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        data.add(imageView);
        imageView = new ImageView(this);
        is = getResources().openRawResource(R.raw.welcome_02);
        bm = BitmapFactory.decodeStream(is, null, opt);
        imageView.setImageBitmap(bm);
        // imageView.setBackgroundResource(R.drawable.pic_welcome_2);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        data.add(imageView);
        imageView = new ImageView(this);
        is = getResources().openRawResource(R.raw.welcome_03);
        bm = BitmapFactory.decodeStream(is, null, opt);
        imageView.setImageBitmap(bm);
        // imageView.setBackgroundResource(R.drawable.pic_welcome_3);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        data.add(imageView);
        imageView = new ImageView(this);
        is = getResources().openRawResource(R.raw.welcome_04);
        bm = BitmapFactory.decodeStream(is, null, opt);
        imageView.setImageBitmap(bm);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        data.add(imageView);
        adapter = new VpAdapter(data);
        m_viewpager.setAdapter(adapter);
        setCirclePageIndicator();
        m_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                arg0 = arg0 % data.size();
                // 当viewpager换页时 改掉下面对应的小点

                if (arg0 == data.size() -1){
                    m_layout_btn.setVisibility(View.VISIBLE);
                }else{
                    m_layout_btn.setVisibility(View.GONE);
                }
                if (data.size() > 0) {
                    for (int i = 0; i < imageViews.length; i++) { // 设置当前的对应的小点为选中状态
                        imageViews[arg0].setBackgroundResource(R.mipmap.jshop_banner_point_active);
                        if (arg0 != i) { // 设置为非选中状态
                            imageViews[i].setBackgroundResource(R.mipmap.jshop_banner_point_inactive);
                        }
                    }
                }
            }

        });
    }

    /**
     * 设置圆点指示器
     */
    private void setCirclePageIndicator() {
        m_iv_image.removeAllViews();
        int pageCount = data.size();// 对应小点个数 final ImageView[]
        imageViews = new ImageView[pageCount];
        if (this.data.size() > 0) {
            for (int i = 0; i < pageCount; i++) {
                LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 设置每个小圆点距离左边的间距
                if (i > 0) {
                    margin.setMargins(10, 0, 0, 0);
                } else {
                    margin.setMargins(0, 0, 0, 0);
                }

                ImageView imageView = new ImageView(this); // 设置每个小圆点的宽高
                // imageView.setLayoutParams(new LayoutParams(15, 15));
                imageViews[i] = imageView;
                if (i == 0) { // 默认选中第一张图片
                    imageViews[i].setBackgroundResource(R.mipmap.jshop_banner_point_active);
                } else { // 其他图片都设置未选中状态
                    imageViews[i].setBackgroundResource(R.mipmap.jshop_banner_point_inactive);
                }
                m_iv_image.addView(imageViews[i], margin);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m_viewpager =null;
        setContentView(R.layout.view_null);
    }

    @Event(R.id.btn_ok)
    private void welOnClick(View v){
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
