package com.fly.graphic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View


class Scene(context : Context?,attrs:AttributeSet?) : View(context,attrs)
{
    private var renderer : Renderer = Renderer()
    private var scene_renderer : SceneRenderer = SceneRenderer(renderer,Camera())
    private var bg_color : Int = Color.WHITE

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)
        canvas?.drawColor(bg_color)

        if (canvas != null) { renderer.Display(canvas) }

        if(renderer.GetLayerType() != LAYER_TYPE_HARDWARE)
            setLayerType(LAYER_TYPE_SOFTWARE,renderer.GetPaint())

        postInvalidate()
    }

    fun GetWidth() : Int { return width }
    fun GetHeight() : Int { return height }
    fun GetSceneRenderer() : SceneRenderer { return scene_renderer }

    fun SetBackGroundColor(color:Int) { bg_color = color }
    fun SetCamera(camera: Camera){ scene_renderer.SetCamera(camera) }
    fun SetRenderer(r : Renderer) { renderer = r;scene_renderer = SceneRenderer(renderer,scene_renderer.GetCamera()) }
    fun SetSceneRenderer(r : SceneRenderer) { scene_renderer = r }

}
