package com.fly.graphic

import android.content.res.AssetManager
import android.graphics.*

class SceneRenderer(private var camera: Camera = Camera()) : Renderer()
{
    private var width_ratio = 1f
    private var height_ratio = 1f

    fun SetWidthRatio(width_ratio:Float){ this.width_ratio = width_ratio }
    fun SetHeightRatio(height_ratio:Float){ this.height_ratio = height_ratio }
    fun SetCamera(camera: Camera) { this.camera = camera }
    fun GetCamera() : Camera { return camera }

    override fun DrawPoint(canvas: Canvas, x:Float, y:Float){ canvas.drawPoint((x - camera.look_at_x) * width_ratio,(y - camera.look_at_y) * height_ratio,paint) }
    override fun DrawPoints(canvas: Canvas, pts:FloatArray)
    {
        val p = FloatArray(pts.size)
        for (i in 0 until pts.size step 2)
            p.set(i,(pts[i] - camera.look_at_x) * width_ratio)
        for (i in 1 until pts.size step 2)
            p.set(i,(pts[i] - camera.look_at_y) * height_ratio)

        canvas.drawPoints(p,paint)
    }

    override fun DrawLine(canvas: Canvas,start_x:Float,start_y:Float,stop_x:Float,stop_y:Float)
    { canvas.drawLine((start_x - camera.look_at_x) * width_ratio,(start_y - camera.look_at_y) * height_ratio,
        (stop_x - camera.look_at_x) * width_ratio,(stop_y - camera.look_at_y) * height_ratio,paint) }

    //override fun DrawPath(canvas: Canvas,path: Path) { canvas.drawPath(path,super.GetPaint()) }

    override fun DrawRect(canvas: Canvas, left:Float, top:Float, right:Float, bottom:Float)
    { canvas.drawRect((left - camera.look_at_x) * width_ratio, (top - camera.look_at_y) * height_ratio,
        (right - camera.look_at_x) * width_ratio, (bottom - camera.look_at_y) * height_ratio, paint) }
    override fun DrawRect(canvas: Canvas, rect: Rect)
    {
        canvas.drawRect(Rect(((rect.left - camera.look_at_x) * width_ratio).toInt(), ((rect.top - camera.look_at_y) * height_ratio).toInt(),
            ((rect.right - camera.look_at_x) * width_ratio).toInt(), ((rect.bottom - camera.look_at_y) * height_ratio).toInt()), paint)
    }
    override fun DrawRect(canvas: Canvas, rect: RectF)
    {
        canvas.drawRect(RectF((rect.left - camera.look_at_x) * width_ratio, (rect.top - camera.look_at_y) * height_ratio,
            (rect.right - camera.look_at_x) * width_ratio, (rect.bottom - camera.look_at_y) * height_ratio), paint)
    }
    override fun DrawRect(canvas: Canvas, x:Int, y:Int, width: Int, height: Int)
    {
        canvas.drawRect((x.toFloat() - camera.look_at_x) * width_ratio, (y.toFloat() - camera.look_at_y) * height_ratio,
            ((x + width).toFloat() - camera.look_at_x) * width_ratio, ((y + height).toFloat() - camera.look_at_y) * height_ratio,paint)
    }

    override fun DrawRegion(canvas: Canvas, region: Region)
    {
        val iterator = RegionIterator(region)
        val rect = Rect()
        while (iterator.next(rect))
        {
            canvas.drawRect(Rect(((rect.left - camera.look_at_x) * width_ratio).toInt(), ((rect.top - camera.look_at_y) * height_ratio).toInt(),
                ((rect.right - camera.look_at_x) * width_ratio).toInt(), ((rect.bottom - camera.look_at_y) * height_ratio).toInt()), paint)
        }
    }

    override fun DrawText(canvas: Canvas, text:String, x:Float, y:Float) { canvas.drawText(text,x - camera.look_at_x,y - camera.look_at_y,paint) }

    //override fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, matrix: Matrix) { canvas.drawBitmap(bitmap,matrix,super.GetPaint()) }
    override fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, src: Rect?, dst: Rect)
    {
            super.DrawBitmap(canvas, bitmap, src,
                Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(), ((dst.top - camera.look_at_y) * height_ratio).toInt(),
                    ((dst.right - camera.look_at_x) * width_ratio).toInt(), ((dst.bottom - camera.look_at_y) * height_ratio).toInt()))
    }
    override fun DrawBitmap(canvas: Canvas,bitmap: Bitmap,src: Rect?,dst: RectF)
    {
        super.DrawBitmap(canvas, bitmap, src,
                RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
                    (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio))
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
        super.DrawBitmap(canvas, bitmap_path, src,
                Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(), ((dst.top - camera.look_at_y) * height_ratio).toInt(),
                    ((dst.right - camera.look_at_x) * width_ratio).toInt(), ((dst.bottom - camera.look_at_y) * height_ratio).toInt()))
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, src: Rect?, dst: RectF)
    {
        super.DrawBitmap(canvas,bitmap_path,src,
                RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
                    (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio))
    }
    override fun DrawBitmap(canvas: Canvas,bitmap_path: String,left:Float,top:Float)
    { super.DrawBitmap(canvas,bitmap_path,(left - camera.look_at_x) * width_ratio,(top - camera.look_at_y) * height_ratio) }
    /*
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,matrix,super.GetPaint())
    }
    */
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, src: Rect?, dst: Rect)
    {
        super.DrawBitmap(canvas, bitmap_path, asset_manager, src,
                Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(), ((dst.top - camera.look_at_y) * height_ratio).toInt(),
                    ((dst.right - camera.look_at_x) * width_ratio).toInt(), ((dst.bottom - camera.look_at_y) * height_ratio).toInt()))
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, src: Rect?, dst: RectF)
    {
        super.DrawBitmap(canvas, bitmap_path, asset_manager, src,
                RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
                    (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio))
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, left:Float, top:Float)
    { super.DrawBitmap(canvas, bitmap_path,(left - camera.look_at_x) * width_ratio,(top - camera.look_at_y) * height_ratio) }

    override fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float, y: Float, width:Float, height:Float, index:Int)
    { super.DrawSprite(canvas,sprite,(x - camera.look_at_x) * width_ratio, (y - camera.look_at_y) * height_ratio, width * width_ratio, height * height_ratio, index) }
    override fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float, y: Float, width:Float, height:Float, index:Int, flip_x:Float, flip_y: Float)
    { super.DrawSprite(canvas,sprite,(x - camera.look_at_x) * width_ratio, (y - camera.look_at_y) * height_ratio, width * width_ratio, height * height_ratio, index, flip_x, flip_y) }

    override fun DrawObject(canvas: Canvas, obj: Object, width: Float, height: Float, index: Int)
    {
        val obj_x = obj.x * width_ratio
        val obj_y = obj.y * height_ratio
        val obj_width = width * width_ratio
        val obj_height = height * height_ratio
        obj_last_x = obj_x
        obj_last_y = obj_y

        if (obj.GetRigid() != null)
        {
            if (obj.GetCollisionBox() != null)
            {
                obj.GetCollisionBox()!!.SetRect(RectF(obj_x,obj_y,obj_x + obj_width,obj_y + obj_height))
                if (!obj.GetCollisionBox()!!.Collision())
                {
                    val will_y = obj_y + obj.GetRigid()!!.GetDropHeight(render_time.toFloat())
                    obj.GetCollisionBox()!!.SetRect(RectF(obj_x,will_y,obj_x + obj_width,will_y + obj_height))
                    if (obj.GetCollisionBox()!!.Collision())
                    {
                        if (will_y + obj_height > obj.GetCollisionBox()!!.GetCollisionRect().top)
                        {
                            if (!(will_y + obj_height < obj.GetCollisionBox()!!.GetCollisionRect().top + 0.1f * height_ratio))
                                obj.y -= will_y + obj_height - obj.GetCollisionBox()!!.GetCollisionRect().top / height_ratio
                            else
                                obj_next_y = obj.GetCollisionBox()!!.GetCollisionRect().top / height_ratio - height
                        }
                    }
                    else
                    {
                        //obj.GetCollisionBox()!!.SetRect(RectF(obj_x,obj_y,obj_x + obj_width,obj_y + obj_height))
                        for (i in 0 until obj.GetCollisionBox()!!.GetAllCollisionRect().size)
                        {
                            if (obj_y >= obj.GetCollisionBox()!!.GetAllCollisionRect()[i].bottom // ||  obj_y > obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top
                                && obj_last_y!! + obj_height < obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top)
                            {
                                obj.y = obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top / height_ratio - height
                                return
                            }
                        }
                        obj.y += obj.GetRigid()!!.GetDropHeight(render_time.toFloat()) / 100
                    }
                }
            }
            else
                obj.y += obj.GetRigid()!!.GetDropHeight(render_time.toFloat()) / 100
        }

        obj.GetSprite()?.let{ DrawSprite(canvas, it,obj.x,obj.y,width,height,index) }
        if (obj_next_y != null)
            obj.y = obj_next_y!!
        obj_next_y = null
    }

}
