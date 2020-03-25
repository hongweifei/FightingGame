package com.fly.graphic

import android.content.res.AssetManager
import android.graphics.*

class SceneRenderer(private var camera: Camera = Camera()) : Renderer()
{
    fun SetCamera(camera: Camera) { this.camera = camera }
    fun GetCamera() : Camera { return camera }

    override fun DrawPoint(canvas: Canvas, x:Float, y:Float){ canvas.drawPoint(x - camera.look_at_x,y - camera.look_at_y,paint) }
    override fun DrawPoints(canvas: Canvas, pts:FloatArray)
    {
        val p = FloatArray(pts.size)
        for (i in 0 until pts.size step 2)
            p.set(i,pts[i] - camera.look_at_x)
        for (i in 1 until pts.size step 2)
            p.set(i,pts[i] - camera.look_at_y)

        canvas.drawPoints(p,paint)
    }

    override fun DrawLine(canvas: Canvas,start_x:Float,start_y:Float,stop_x:Float,stop_y:Float)
    { canvas.drawLine(start_x - camera.look_at_x,start_y - camera.look_at_y,stop_x - camera.look_at_x,stop_y - camera.look_at_y,paint) }

    //override fun DrawPath(canvas: Canvas,path: Path) { canvas.drawPath(path,super.GetPaint()) }

    override fun DrawRect(canvas: Canvas, left:Float, top:Float, right:Float, bottom:Float)
    { canvas.drawRect(left - camera.look_at_x, top - camera.look_at_y, right - camera.look_at_x, bottom - camera.look_at_y, paint) }
    override fun DrawRect(canvas: Canvas, rect: Rect)
    {
        canvas.drawRect(Rect((rect.left - camera.look_at_x).toInt(), (rect.top - camera.look_at_y).toInt(),
            (rect.right - camera.look_at_x).toInt(), (rect.bottom - camera.look_at_y).toInt()), paint)
    }
    override fun DrawRect(canvas: Canvas, rect: RectF)
    {
        canvas.drawRect(RectF(rect.left - camera.look_at_x, rect.top - camera.look_at_y,
        rect.right - camera.look_at_x, rect.bottom - camera.look_at_y), paint)
    }
    override fun DrawRect(canvas: Canvas, x:Int, y:Int, width: Int, height: Int)
    {
        canvas.drawRect(x.toFloat() - camera.look_at_x, y.toFloat() - camera.look_at_y,
            (x + width).toFloat() - camera.look_at_x, (y + height).toFloat() - camera.look_at_y,paint)
    }

    override fun DrawRegion(canvas: Canvas, region: Region)
    {
        val iterator = RegionIterator(region)
        val rect = Rect()
        while (iterator.next(rect))
        {
            canvas.drawRect(Rect((rect.left - camera.look_at_x).toInt(), (rect.top - camera.look_at_y).toInt(),
            (rect.right - camera.look_at_x).toInt(), (rect.bottom - camera.look_at_y).toInt()), paint)
        }
    }

    override fun DrawText(canvas: Canvas, text:String, x:Float, y:Float) { canvas.drawText(text,x - camera.look_at_x,y - camera.look_at_y,paint) }

    //override fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, matrix: Matrix) { canvas.drawBitmap(bitmap,matrix,super.GetPaint()) }
    override fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, src: Rect?, dst: Rect)
    {
        if (src != null)
        {
            canvas.drawBitmap(bitmap,src,
                Rect((src.left - camera.look_at_x).toInt(), (src.top - camera.look_at_y).toInt(),
                    (src.right - camera.look_at_x).toInt(), (src.bottom - camera.look_at_y).toInt()),paint)
        }
    }
    override fun DrawBitmap(canvas: Canvas,bitmap: Bitmap,src: Rect?,dst: RectF)
    {
        if (src != null)
        {
            canvas.drawBitmap(bitmap,src,
                RectF(src.left - camera.look_at_x, src.top - camera.look_at_y,
                src.right - camera.look_at_x, src.bottom - camera.look_at_y),paint)
        }
    }
    override fun DrawBitmap(canvas: Canvas,bitmap:Bitmap,left:Float,top:Float) { canvas.drawBitmap(bitmap,left - camera.look_at_x,top - camera.look_at_y,paint) }
    /*
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,matrix,super.GetPaint())
    }
    */
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, src: Rect?, dst: Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        if (src != null)
        {
            canvas.drawBitmap(bitmap,src,
                Rect((src.left - camera.look_at_x).toInt(), (src.top - camera.look_at_y).toInt(),
                (src.right - camera.look_at_x).toInt(), (src.bottom - camera.look_at_y).toInt()),paint)
        }
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, src: Rect?, dst: RectF)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        if (src != null)
        {
            canvas.drawBitmap(bitmap,src,
                RectF(src.left - camera.look_at_x, src.top - camera.look_at_y,
                src.right - camera.look_at_x, src.bottom - camera.look_at_y),paint)
        }
    }
    override fun DrawBitmap(canvas: Canvas,bitmap_path: String,left:Float,top:Float)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,left - camera.look_at_x,top - camera.look_at_y,paint)
    }
    /*
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,matrix,super.GetPaint())
    }
    */
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, src: Rect?, dst: Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        if (src != null)
        {
            canvas.drawBitmap(bitmap,src,
                Rect((src.left - camera.look_at_x).toInt(), (src.top - camera.look_at_y).toInt(),
                (src.right - camera.look_at_x).toInt(), (src.bottom - camera.look_at_y).toInt()),
                super.GetPaint())
        }
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, src: Rect?, dst: RectF)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))

        if (src != null)
        {
            canvas.drawBitmap(bitmap,
                src,
                RectF(src.left - camera.look_at_x, src.top - camera.look_at_y,
                    src.right - camera.look_at_x, src.bottom - camera.look_at_y),paint)
        }
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, left:Float, top:Float)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,left - camera.look_at_x,top - camera.look_at_y,paint)
    }

    override fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float, y: Float, width:Int, height:Int, index:Int)
    { super.DrawSprite(canvas,sprite,x - camera.look_at_x, y - camera.look_at_y, width, height, index) }
    override fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float, y: Float, width:Int, height:Int, index:Int, flip_x:Float, flip_y: Float)
    { super.DrawSprite(canvas,sprite,x - camera.look_at_x, y - camera.look_at_y, width, height, index, flip_x, flip_y) }

    override fun DrawObject(canvas: Canvas, obj: Object, width: Int, height: Int, index: Int)
    {
        obj.GetSprite()?.let{ DrawSprite(canvas, it,obj.x,obj.y,width,height,index) }
        if (obj.GetCollisionBox() != null)
        {
            obj.GetCollisionBox()!!.SetRect(RectF(obj.x,obj.y,obj.x + width,obj.y + height))
            if (obj.GetRigid() != null && FPS != null && !obj.GetCollisionBox()!!.Collision())
                obj.y += obj.GetRigid()!!.GetDropHeight((1000 / FPS!!).toFloat())
        }
    }

}
