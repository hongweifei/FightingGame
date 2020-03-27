package com.fly.ui

import android.graphics.Canvas
import android.graphics.Paint
import com.fly.graphic.Renderer

open class Button(var text:String = "Button", x:Float = 0f, y:Float = 0f, width:Float = 0f, height:Float = 0f) : Widget(x,y,width,height)
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
        Render(canvas, renderer, x, y)
    }
    override fun Render(canvas: Canvas,renderer: Renderer,x: Float,y: Float)
    {
        Render(canvas, renderer, x, y, width, height)
    }
    override fun Render(canvas: Canvas,renderer: Renderer,x:Float,y:Float,width:Float,height:Float)
    {
        renderer.SetAlpha(alpha)
        renderer.DrawText(canvas,text,x,y);renderer.DrawRect(canvas,x,y,x + width,y + height)
        renderer.SetStyle(Paint.Style.STROKE)
        renderer.DrawRect(canvas,x,y,x + width,y + height)
        renderer.SetStyle(Paint.Style.FILL_AND_STROKE)
    }

}
