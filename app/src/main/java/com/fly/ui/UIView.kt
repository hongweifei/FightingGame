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
    private var click:(event: MotionEvent?)->Unit = {}
    private var up:(event: MotionEvent?)->Unit = {}
    private var move:(event: MotionEvent?)->Unit = {}

    private var widget_list:ArrayList<Widget> = ArrayList()

    private var renderer:Renderer = Renderer()
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?)
    {
        for (i in 0 until widget_list.size)
        {
            if (canvas != null) { widget_list[i].Render(canvas, renderer) }
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

    private fun Collision(x1:Float,y1:Float,width1:Float,height1:Float,x2:Float,y2:Float,width2:Float,height2:Float) : Boolean
    {
        if (x1 <= x2 + width2 && x1 + width1 >= x2 && y1 + height1 >= y2 && y1 <= y2 + height2)
            return true
        return false
    }

    fun SetClick(click:(event: MotionEvent?)->Unit){ this.click = click }
    fun SetUp(up:(event: MotionEvent?)->Unit){ this.up = up }
    fun SetMove(move:(event: MotionEvent?)->Unit){ this.move = move }

    fun SetRenderer(r:Renderer) { renderer = r }

    fun AddWidget(widget:Widget) { widget_list.add(widget) }
    fun AddWidgets(widgets: ArrayList<Widget>) { widget_list.addAll(widgets) }

    fun FindWidget(x:Float,y:Float) : ArrayList<Int>
    {
        val index : ArrayList<Int> = ArrayList<Int>()
        for (i in 0 until widget_list.size)
        {
            if (Collision(widget_list[i].x,widget_list[i].y,widget_list[i].width,widget_list[i].height,
                    x,y,1f,1f))
            {
                index.add(i)
            }
        }
        return index
    }

    fun WidgetClick(event: MotionEvent)
    {
        val index = FindWidget(event.x,event.y)
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
