package com.fly.graphic

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService

class Camera(var look_at_x : Float = 0f,var look_at_y : Float = 0f)
{

    init
    {

    }

    fun Move(x:Float,y:Float) { look_at_x = x;look_at_y = y }

}
