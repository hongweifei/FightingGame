package com.fly.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
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
        renderer.DrawText(canvas,text,x.toFloat(),y.toFloat() + height / 2);
        renderer.SetStyle(Paint.Style.STROKE)
        renderer.DrawRect(canvas,x,y,width,height)
        renderer.SetStyle(Paint.Style.FILL_AND_STROKE)
    }
    override fun Render(canvas: Canvas,renderer: Renderer,x: Int,y: Int) { renderer.DrawText(canvas,text,x.toFloat(),y.toFloat());renderer.DrawRect(canvas,x,y,width,height) }
    override fun Render(canvas: Canvas,renderer: Renderer,x:Int,y:Int,width:Int,height:Int) { renderer.DrawText(canvas,text,x.toFloat(),y.toFloat());renderer.DrawRect(canvas,x,y,width,height) }

}
