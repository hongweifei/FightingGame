package com.fly.control

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnPreDrawListener


@SuppressLint("ViewConstructor")
class Rocker(context:Context, back_ground_bitmap_path:String, button_bitmap_path:String, asset_manager:AssetManager) : View(context)
{
    //固定摇杆背景圆形的X,Y坐标以及半径
    private var back_ground_x:Float = 0f
    private var back_ground_y:Float = 0f
    private var back_ground_r:Float = 0f
    private var back_ground : Bitmap = BitmapFactory.decodeStream(asset_manager.open(back_ground_bitmap_path))
    //摇杆的X,Y坐标以及摇杆的半径
    private var rocker_x:Float = 0f
    private var rocker_y:Float = 0f
    private var rocker_r:Float = 0f
    private var rocker : Bitmap = BitmapFactory.decodeStream(asset_manager.open(button_bitmap_path))

    private lateinit var center_point: PointF

    init
    {

        viewTreeObserver.addOnPreDrawListener(
            OnPreDrawListener()
            {
                center_point = PointF((width / 2).toFloat(), (height / 2).toFloat())

                back_ground_x = center_point.x
                back_ground_y = center_point.y

                rocker_x = center_point.x
                rocker_y = center_point.y

                val tmp_f: Float = back_ground.width / (back_ground.width + rocker.width) as Float
                back_ground_r = tmp_f * width / 2
                rocker_r = (1.0f - tmp_f) * width / 2

                return@OnPreDrawListener true
            }
        )

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        canvas?.drawBitmap(back_ground,null,
            RectF(back_ground_x - back_ground_r,back_ground_y - back_ground_r,back_ground_x + back_ground_r,back_ground_y + back_ground_r),null)

        canvas?.drawBitmap(rocker,null,
            RectF(rocker_x - rocker_r,rocker_y - rocker_r,rocker_x + rocker_r,rocker_y + rocker_r),null)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        if (event?.getAction() == MotionEvent.ACTION_DOWN || event?.getAction() == MotionEvent.ACTION_MOVE)
        {
            // 当触屏区域不在活动范围内
            if (Math.sqrt(Math.pow(((back_ground_x - event.x).toDouble()), 2.0) + Math.pow(((back_ground_y -  event.y).toDouble()), 2.0)) >= back_ground_r)
            {
                //得到摇杆与触屏点所形成的角度
                val tempRad : Double = GetRad(back_ground_x, back_ground_y, event.x, event.y);
                //保证内部小圆运动的长度限制
                GetXY(back_ground_x, back_ground_y, back_ground_r, tempRad);
            }
            else
            {//如果小球中心点小于活动区域则随着用户触屏点移动即可
                rocker_x =  event.x;
                rocker_y =  event.y;
            }
        }
        else if (event?.getAction() == MotionEvent.ACTION_UP)
        {
            //当释放按键时摇杆要恢复摇杆的位置为初始位置
            rocker_x = center_point.x;
            rocker_y = center_point.y;
        }

        return super.onTouchEvent(event)
    }

    /***
     * 得到两点之间的弧度
     */

    private fun GetRad(px1:Float, py1:Float, px2:Float, py2:Float) : Double
    {
        //得到两点X的距离
        val x:Float = px2 - px1;
        //得到两点Y的距离
        val y:Float = py1 - py2;
        //算出斜边长
        val xie:Double = Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0));
        //得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
        val cosAngle:Double = x / xie;
        //通过反余弦定理获取到其角度的弧度
        var rad:Double = Math.acos(cosAngle);
        //注意：当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
        if (py2 < py1)
            rad = -rad

        return rad;
    }

    /**
     *
     * @param r  圆周运动的旋转点
     * @param centerX 旋转点X
     * @param centerY 旋转点Y
     * @param rad 旋转的弧度
     */

    private fun GetXY(center_x:Float, center_y:Float, r:Float, rad:Double)
    {
        //获取圆周运动的X坐标
        rocker_x = ((r * Math.cos(rad)) + center_x).toFloat();
        //获取圆周运动的Y坐标
        rocker_y = ((r * Math.sin(rad)) + center_y).toFloat();
    }

}
