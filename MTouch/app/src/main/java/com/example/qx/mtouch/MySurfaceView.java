package com.example.qx.mtouch;

import android.annotation.SuppressLint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by QX on 2017/11/9.
 */
@SuppressLint("WrongCall")
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
    MultiTouchActivity activity;
    Paint paint;
    Paint paintl;
    HashMap<Integer,BNPoint> hm=new HashMap<>();
    static int countl=0;
    BNPoint bp;

    public MySurfaceView(MultiTouchActivity activity) {
        super(activity);
        this.activity=activity;
        this.getHolder().addCallback(this);
        paint=new Paint();
        paint.setAntiAlias(true);

        paintl=new Paint();
        paintl.setAntiAlias(true);
        paintl.setTextSize(35);
        System.out.println("-----------------------------");
    }

    public void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);

        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        paintl.setColor(Color.WHITE);
        paintl.setStrokeWidth(5);
        paintl.setStyle(Paint.Style.STROKE);

        Set<Integer> ks=hm.keySet();
        for(int i:ks)
        {
            bp=hm.get(i);
            bp.drawSelf(paint,paintl,canvas);
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        int id=-1;
        try {
            int action=e.getAction()&MotionEvent.ACTION_MASK;
            int index=(e.getAction()&MotionEvent.ACTION_POINTER_INDEX_MASK)
                    >>>MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            id=e.getPointerId(index);
            switch(action)
            {
                case MotionEvent.ACTION_DOWN:
                    hm.put(id, new BNPoint(e.getX(id),e.getY(id),getColor(),countl++));
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    hm.put(id,new BNPoint(e.getX(id),e.getY(id),getColor(),countl++));
                    break;
                case MotionEvent.ACTION_MOVE:
                    int count=e.getPointerCount();
                    for(int i=0;i<count;i++)
                    {
                        int tempID=e.getPointerId(i);
                        if(hm.get(tempID)!=null)
                        {
                            hm.get(tempID).setLocation(e.getX(i),e.getY(i));
                            if (hm.get(tempID)!=null)
                            {
                                hm.get(tempID).setLocation(e.getX(id),e.getY(i));
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    hm.clear();
                    countl=0;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    hm.remove(id);
                    break;
            }
            repaint();
        }
        catch(Exception ea)
        {
            System.out.println("id="+id);
            ea.printStackTrace();
        }


        return true;
    }

    public int[] getColor()
    {
        int[] result=new int[4];
        result[0]=(int)(Math.random()*255);
        result[1]=(int)(Math.random()*255);
        result[2]=(int)(Math.random()*255);
        result[3]=(int)(Math.random()*255);
        return result;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void repaint() throws Exception {
        SurfaceHolder holder=this.getHolder();
        Canvas canvas=holder.lockCanvas();
        try {
            synchronized (holder){
                onDraw(canvas);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            if(canvas!=null)
            {
                holder.unlockCanvasAndPost(canvas);
            }

        }

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
