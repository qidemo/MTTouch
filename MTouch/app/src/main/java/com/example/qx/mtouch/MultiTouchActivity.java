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
    static float screenHeight; //
    static float screnWeight;

    @Override
    public void onCreate(Bundle savedInstenceState)
    {
        super.onCreate(savedInstenceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenHeight=dm.heightPixels;
        screnWeight=dm.widthPixels;

        MySurfaceView mySurfaceView=new MySurfaceView(this);
        this.setContentView(mySurfaceView);
    }
}
