package com.fly.ui

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.fly.graphic.Renderer

class ImageButton(path:String?,asset_manager:AssetManager?,x:Int = 0, y:Int = 0, width:Int = 0, height:Int = 0) : Button("",x,y,width,height)
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
        val r:Renderer = renderer
        alpha?.let { r.SetAlpha(it) }
        r.DrawBitmap(canvas,bitmap,null,Rect(x,y,x + width,y + height))
    }
    override fun Render(canvas: Canvas, renderer: Renderer, x: Int, y: Int)
    {
        val r:Renderer = renderer
        alpha?.let { r.SetAlpha(it) }
        renderer.DrawBitmap(canvas,bitmap,null,Rect(x,y,x + width,y + height))
    }
    override fun Render(canvas: Canvas, renderer: Renderer, x: Int, y: Int, width: Int, height: Int)
    {
        val r:Renderer = renderer
        alpha?.let { r.SetAlpha(it) }
        renderer.DrawBitmap(canvas,bitmap,null,Rect(x,y,x + width,y + height))
    }
}
