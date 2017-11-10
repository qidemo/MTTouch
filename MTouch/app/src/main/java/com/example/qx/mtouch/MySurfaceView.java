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
    Paint paint;//画笔paint
    Paint paintl;//画笔paint1
    HashMap<Integer,BNPoint> hm=new HashMap<>();
    static int countl=0;
    BNPoint bp;

    public MySurfaceView(MultiTouchActivity activity) {
        super(activity);
        this.activity=activity;
        this.getHolder().addCallback(this);//设置生命周期回调接口的实现者
        paint=new Paint();//创建画笔
        paint.setAntiAlias(true);//打开抗锯齿

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

        //遍历触控点Map,对触控点一一进行绘制
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
            //获取触控的动作编号
            int action=e.getAction()&MotionEvent.ACTION_MASK;
            //获取主、辅点id（down时主辅点id皆正确，up时辅点id正确，主点id要查询Map中剩下的一个点的id）
            int index=(e.getAction()&MotionEvent.ACTION_POINTER_INDEX_MASK)
                    >>>MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            id=e.getPointerId(index);
            switch(action)                                    //>>>的意思是无符号右移
            {
                case MotionEvent.ACTION_DOWN: //主点down
                    //向Map中记录一个新点
                    hm.put(id, new BNPoint(e.getX(id),e.getY(id),getColor(),countl++));
                    break;
                case MotionEvent.ACTION_POINTER_DOWN://辅点down
                    //向Map中记录一个新点
                    hm.put(id,new BNPoint(e.getX(id),e.getY(id),getColor(),countl++));
                    break;
                case MotionEvent.ACTION_MOVE://主/辅点move
                    //不论主/辅点Move都更新其位置
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
                case MotionEvent.ACTION_UP://主点up
                    //在本应用中主点UP则只需要清空Map即可，在其他一些应用中需要操作的
                    //则取出Map中唯一剩下的点操作即可
                    hm.clear();
                    countl=0;
                    break;
                case MotionEvent.ACTION_POINTER_UP://辅点up
                    //从Map中删除对应id的辅点
                    hm.remove(id);
                    break;
            }
            //重绘画面
            repaint();
        }
        catch(Exception ea)
        {
            System.out.println("id="+id);
            ea.printStackTrace();
        }


        return true;
    }

    //获取颜色编号的方法
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

    //自己为SurfaceView写的重绘方法
    public void repaint() throws Exception {
        SurfaceHolder holder=this.getHolder();
        Canvas canvas=holder.lockCanvas();//获取画布
        try {
            synchronized (holder){
                onDraw(canvas);//绘制
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
    public void surfaceDestroyed(SurfaceHolder holder) {//销毁时被调用

    }
}
