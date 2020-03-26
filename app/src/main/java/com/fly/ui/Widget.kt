package com.fly.ui

import android.graphics.Canvas
import com.fly.graphic.Renderer

open class Widget(var x:Float = 0f,var y:Float = 0f,var width:Float = 0f,var height:Float = 0f)
{
    protected var alpha:Int = 255

    private var click : () -> Unit = {}
    var is_click = false

    private var up : () -> Unit = {}
    var is_up :Boolean = true

    fun SetAlpha(a:Int) { alpha = a }

    //fun SetRender(render: (canvas: Canvas,renderer: Renderer) -> Unit) { this.render = render }
    open fun Render(canvas: Canvas,renderer: Renderer) { return }
    open fun Render(canvas: Canvas,renderer: Renderer,x: Float,y: Float) { return }
    open fun Render(canvas: Canvas,renderer: Renderer,x:Float,y:Float,width:Float,height:Float) { return }

    fun SetClick(click:() ->Unit) { this.click = click }
    fun Click() { click() }

    fun SetUp(up:() ->Unit) { this.up = up }
    fun Up() { up() }

}
