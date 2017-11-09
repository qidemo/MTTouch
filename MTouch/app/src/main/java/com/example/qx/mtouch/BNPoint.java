package com.example.qx.mtouch;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by QX on 2017/11/9.
 */

public class BNPoint {
    static final float RADIS=80;
    float x;
    float y;
    int color[];
    int countl;
    public  BNPoint(float x,float y,int color[],int countl)
    {
        this.x=x;
        this.y=y;
        this.color=color;
        this.countl=countl;
    }
    public void setLocation(float x, float y)
    {
        this.x=x;
        this.y=y;
    }
    public void drawSelf(Paint p, Paint pl, Canvas c)
    {
        p.setARGB(180,color[1],color[2],color[3]);

        c.drawCircle(x,y,RADIS,p);
        p.setARGB(150,color[1],color[2],color[3]);
        c.drawCircle(x,y,RADIS-10,p);
        c.drawCircle(x,y,RADIS-18,pl);
        c.drawText(countl+1+"",x,y-100,pl);
    }
}
