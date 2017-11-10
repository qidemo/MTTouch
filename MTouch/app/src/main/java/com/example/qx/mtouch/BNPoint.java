package com.example.qx.mtouch;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by QX on 2017/11/9.
 */

//用于记录触控点坐标、及绘制触控点的类
public class BNPoint {
    //触控点绘制半径
    static final float RADIS=80;
    //触控点X、Y坐标
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
    //修改触控点位置的方法
    public void setLocation(float x, float y)
    {
        this.x=x;
        this.y=y;
    }
    //绘制触控点的方法
    public void drawSelf(Paint p, Paint pl, Canvas c)
    {
        p.setARGB(180,color[1],color[2],color[3]);//设置画笔颜色

        //绘制圆环
        c.drawCircle(x,y,RADIS,p);
        p.setARGB(150,color[1],color[2],color[3]);//设置画笔颜色
        c.drawCircle(x,y,RADIS-10,p);
        c.drawCircle(x,y,RADIS-18,pl);
        c.drawText(countl+1+"",x,y-100,pl);
    }
}
