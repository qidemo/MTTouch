package com.example.qx.mtouch;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by QX on 2017/11/9.
 */

public class MultiTouchActivity extends Activity
{
    static float screenHeight; //屏幕高度
    static float screnWeight; //屏幕宽度

    @Override
    public void onCreate(Bundle savedInstenceState)
    {
        super.onCreate(savedInstenceState);

        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //设置为竖屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //获取屏幕尺寸
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenHeight=dm.heightPixels;
        screnWeight=dm.widthPixels;

        MySurfaceView mySurfaceView=new MySurfaceView(this);
        this.setContentView(mySurfaceView);
    }
}
