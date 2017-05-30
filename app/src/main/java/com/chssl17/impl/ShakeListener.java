package com.chssl17.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 *
 * 摇一摇
 * Created by Administrator on 2017/3/22.
 */

public class ShakeListener implements SensorEventListener {
    private Context context;
    private Sensor sensor;
    private SensorManager manager;
    private OnShakeListener listener;
    private long lastTime;
    private float last_x;
    private float last_y;
    private float last_z;
    public ShakeListener(Context context) {
        this.context = context;
        start();
    }

    //重力感应器的开始
    public void start() {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (manager != null) {
            sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (sensor != null) {
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    //重力感应器的结束
    public void stop() {
        manager.unregisterListener(this, sensor);
    }

    //重力加速度发生变化时调用此方法
    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime= System.currentTimeMillis();
        long timeInterval=currentTime-lastTime;
        if (timeInterval<70){
            return;
        }
        lastTime=currentTime;
        float x=event.values[0];
        float y=event.values[1];
        float z=event.values[2];
        float delay_x=x-last_x;
        float delay_y=y-last_y;
        float delay_z=z-last_z;
        last_x=x;
        last_y=y;
        last_z=z;
        double speed = Math.sqrt(delay_x * delay_x + delay_y * delay_y + delay_z * delay_z) / timeInterval * 10000;

        if (speed >= 3000) {
            listener.onShake();
        }
    }

    //精度发生变化时调用此方法
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //当重力加速度发生变化时，需要通知mainactivity
    public interface OnShakeListener{
        void onShake();
    }
    public void setOnShake(OnShakeListener listener){
        this.listener=listener;
    }
}
