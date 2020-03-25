package com.fly.ui

import android.graphics.Canvas
import android.graphics.Paint
import com.fly.graphic.Renderer

open class Button(var text:String = "Button", x:Int = 0, y:Int = 0, width:Int = 0, height:Int = 0) : Widget(x,y,width,height)
{
    init
    {
        /*
        val button_render:(canvas: Canvas,renderer: Renderer)->Unit = { canvas: Canvas, renderer: Renderer ->
            renderer.DrawText(canvas,text,x.toFloat(),y.toFloat());renderer.DrawRect(canvas,x,y,width,height)
        }

        SetRender(button_render)
        */
    }

    override fun Render(canvas: Canvas,renderer: Renderer)
    {
        val r:Renderer = renderer

        r.SetAlpha(alpha)
        r.DrawText(canvas,text,x.toFloat(),y.toFloat() + height / 2);
        r.SetStyle(Paint.Style.STROKE)
        r.DrawRect(canvas,x,y,width,height)
    }
    override fun Render(canvas: Canvas,renderer: Renderer,x: Int,y: Int)
    {
        val r:Renderer = renderer
        r.SetAlpha(alpha)
        r.DrawText(canvas,text,x.toFloat(),y.toFloat());renderer.DrawRect(canvas,x,y,width,height)
        r.SetStyle(Paint.Style.STROKE)
        r.DrawRect(canvas,x,y,width,height)
    }
    override fun Render(canvas: Canvas,renderer: Renderer,x:Int,y:Int,width:Int,height:Int)
    {
        val r:Renderer = renderer
        r.SetAlpha(alpha)
        r.DrawText(canvas,text,x.toFloat(),y.toFloat());renderer.DrawRect(canvas,x,y,width,height)
        r.SetStyle(Paint.Style.STROKE)
        r.DrawRect(canvas,x,y,width,height)
    }

}
