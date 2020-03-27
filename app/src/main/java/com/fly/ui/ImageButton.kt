package com.fly.ui

import android.content.res.AssetManager
import android.graphics.*
import com.fly.graphic.Renderer

class ImageButton(path:String?,asset_manager:AssetManager?,x:Float = 0f, y:Float = 0f, width:Float = 0f, height:Float = 0f) : Button("",x,y,width,height)
{
    private var bitmap:Bitmap

    init
    {
        bitmap = BitmapFactory.decodeStream(path?.let { asset_manager?.open(it) })
    }

    fun SetImage(path:String,asset_manager:AssetManager) { bitmap = BitmapFactory.decodeStream(asset_manager.open(path)) }
    fun GetImage() : Bitmap { return bitmap }

    override fun Render(canvas: Canvas, renderer: Renderer)
    {
        Render(canvas, renderer, x, y)
    }
    override fun Render(canvas: Canvas, renderer: Renderer, x: Float, y: Float)
    {
        Render(canvas, renderer, x, y, width, height)
    }
    override fun Render(canvas: Canvas, renderer: Renderer, x: Float, y: Float, width: Float, height: Float)
    {
        renderer.SetAlpha(alpha)
        renderer.DrawBitmap(canvas,bitmap,null,RectF(x,y,x + width,y + height))
    }


}
