package com.fly.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.fly.graphic.Renderer

class UIView(context : Context?,attrs: AttributeSet?) : View(context,attrs)
{
    private var widget_list:ArrayList<Widget> = ArrayList()
    private var click:(event: MotionEvent?)->Unit = {}
    private var up:(event: MotionEvent?)->Unit = {}
    private var move:(event: MotionEvent?)->Unit = {}

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?)
    {
        for (i in 0 until widget_list.size)
        {
            if (canvas != null) { widget_list[i].Render(canvas, Renderer()) }
        }

        super.onDraw(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        if (event?.action == MotionEvent.ACTION_DOWN)
        {
            click(event)
            WidgetClick(event)
        }
        if (event?.action == MotionEvent.ACTION_UP)
        {
            up(event)
            WidgetUp()
        }
        if (event?.action == MotionEvent.ACTION_MOVE)
        {
            move(event)
        }

        return true
    }

    private fun Collision(x1:Int,y1:Int,width1:Int,height1:Int,x2:Int,y2:Int,width2:Int,height2:Int) : Boolean
    {
        if (x1 <= x2 + width2 && x1 + width1 >= x2 && y1 + height1 >= y2 && y1 <= y2 + height2)
            return true
        return false
    }

    fun SetClick(click:(event: MotionEvent?)->Unit){ this.click = click }
    fun SetUp(up:(event: MotionEvent?)->Unit){ this.up = up }
    fun SetMove(move:(event: MotionEvent?)->Unit){ this.move = move }

    fun AddWidget(widget:Widget) { widget_list.add(widget) }
    fun AddWidgets(widgets: ArrayList<Widget>) { widget_list.addAll(widgets) }

    fun FindWidget(x:Int,y:Int) : ArrayList<Int>
    {
        val index : ArrayList<Int> = ArrayList<Int>()
        for (i in 0 until widget_list.size)
        {
            if (Collision(widget_list[i].x,widget_list[i].y,widget_list[i].width,widget_list[i].height,
                    x,y,1,1))
            {
                index.add(i)
            }
        }
        return index
    }

    fun WidgetClick(event: MotionEvent)
    {
        val index = FindWidget(event.x.toInt(),event.y.toInt())
        for (i in 0 until index.size)
        {
            widget_list[index[i]].is_up = false

            widget_list[index[i]].is_click = true
            widget_list[index[i]].Click()
        }
    }

    fun WidgetUp()
    {
        for (i in 0 until widget_list.size)
        {
            if (widget_list[i].is_click)
            {
                widget_list[i].is_click = false

                widget_list[i].is_up = true
                widget_list[i].Up()
            }
        }
    }

}
