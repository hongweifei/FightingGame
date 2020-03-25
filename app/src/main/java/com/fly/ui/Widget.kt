package com.fly.ui

import android.graphics.Canvas
import com.fly.graphic.Renderer

open class Widget(var x:Int = 0,var y:Int = 0,var width:Int = 0,var height:Int = 0)
{
    protected var alpha:Int? = null

    private var click : () -> Unit = {}
    var is_click = false

    private var up : () -> Unit = {}
    var is_up :Boolean = true

    fun SetAlpha(a:Int) { alpha = a }

    //fun SetRender(render: (canvas: Canvas,renderer: Renderer) -> Unit) { this.render = render }
    open fun Render(canvas: Canvas,renderer: Renderer) { return }
    open fun Render(canvas: Canvas,renderer: Renderer,x: Int,y: Int) { return }
    open fun Render(canvas: Canvas,renderer: Renderer,x:Int,y:Int,width:Int,height:Int) { return }

    fun SetClick(click:() ->Unit) { this.click = click }
    fun Click() { click() }

    fun SetUp(up:() ->Unit) { this.up = up }
    fun Up() { up() }

}
