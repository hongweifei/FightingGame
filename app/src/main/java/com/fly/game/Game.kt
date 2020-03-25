package com.fly.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.fly.graphic.Camera
import com.fly.graphic.Renderer
import com.fly.mygame.R
import com.fly.physics.RigidBody
import com.fly.ui.ImageButton
import kotlinx.android.synthetic.main.game.*


class Game : Activity() , View.OnTouchListener
{
    var renderer : Renderer = Renderer()
    var camera: Camera = Camera(50f,100f)

    var width:Int = 0
    var height:Int = 0

    lateinit var player:Player

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)

        width = dm.widthPixels // 屏幕宽度（像素）
        height = dm.heightPixels // 屏幕高度（像素）

        //val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        //val densityDpi = dm.densityDpi // 屏幕密度dpi（120 / 160 / 240）

        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        //width = (width / density).toInt() // 屏幕宽度(dp)
        //height = (height / density).toInt() // 屏幕高度(dp)


        setContentView(R.layout.game)
        InitGame()
        Game()
    }

    fun InitGame()
    {
        player = Player("player/Kakashi_Right.png",assets)
        //player.SetSprite()
        player.SetRigidBody(RigidBody())
        player.InitSpriteSrcRect(0,0,80,80,10,7)
        player.width = this.width / 10
        player.height = this.height / 8

        renderer.SetDisplay {
            scene.GetSceneRenderer().DrawObject(it,player,player.width,player.height,0)
            if (player.jump)
                player.Jump()
            if (player.run != None)
                player.Run(player.run)
        }

        scene.SetCamera(camera)
        scene.SetRenderer(renderer)

        InitSkillButton()
    }

    fun Game()
    {
        JumpButton.setOnTouchListener(this)
        DownButton.setOnTouchListener(this)
        LeftButton.setOnTouchListener(this)
        RightButton.setOnTouchListener(this)
    }

    fun InitSkillButton()
    {
        val button_width = width / 2 / 6
        val button_height = height / 2 / 5

        val skill_button:ImageButton = ImageButton("UI/RockerButton.png",assets,width - button_width,height/2,button_width,button_height)
        val button_1:ImageButton = ImageButton("UI/RockerButton.png",assets,0 * button_width,0 * button_height,button_width,button_height)
        val button_2:ImageButton = ImageButton("UI/RockerButton.png",assets,1 * button_width,0 * button_height,button_width,button_height)
        val button_3:ImageButton = ImageButton("UI/RockerButton.png",assets,2 * button_width,0 * button_height,button_width,button_height)
        val button_4:ImageButton = ImageButton("UI/RockerButton.png",assets,3 * button_width,0 * button_height,button_width,button_height)
        val button_5:ImageButton = ImageButton("UI/RockerButton.png",assets,0 * button_width,1 * button_height,button_width,button_height)
        val button_6:ImageButton = ImageButton("UI/RockerButton.png",assets,1 * button_width,1 * button_height,button_width,button_height)
        val button_7:ImageButton = ImageButton("UI/RockerButton.png",assets,2 * button_width,1 * button_height,button_width,button_height)
        val button_8:ImageButton = ImageButton("UI/RockerButton.png",assets,3 * button_width,1 * button_height,button_width,button_height)
        val button_9:ImageButton = ImageButton("UI/RockerButton.png",assets,0 * button_width,2 * button_height,button_width,button_height)
        val button_10:ImageButton = ImageButton("UI/RockerButton.png",assets,1 * button_width,2 * button_height,button_width,button_height)
        val button_11:ImageButton = ImageButton("UI/RockerButton.png",assets,2 * button_width,2 * button_height,button_width,button_height)
        val button_12:ImageButton = ImageButton("UI/RockerButton.png",assets,3 * button_width,2 * button_height,button_width,button_height)

        skill_button.SetClick { player.Skill(this) }
        button_1.SetClick { player.skill_button_1 = true }
        button_2.SetClick { player.skill_button_2 = true }
        button_3.SetClick { player.skill_button_3 = true }
        button_4.SetClick { player.skill_button_4 = true }
        button_5.SetClick { player.skill_button_5 = true }
        button_6.SetClick { player.skill_button_6 = true }
        button_7.SetClick { player.skill_button_7 = true }
        button_8.SetClick { player.skill_button_8 = true }
        button_9.SetClick { player.skill_button_9 = true }
        button_10.SetClick { player.skill_button_10 = true }
        button_11.SetClick { player.skill_button_11 = true }
        button_12.SetClick { player.skill_button_12 = true }

        ui_view.AddWidget(skill_button)
        ui_view.AddWidget(button_1)
        ui_view.AddWidget(button_2)
        ui_view.AddWidget(button_3)
        ui_view.AddWidget(button_4)
        ui_view.AddWidget(button_5)
        ui_view.AddWidget(button_6)
        ui_view.AddWidget(button_7)
        ui_view.AddWidget(button_8)
        ui_view.AddWidget(button_9)
        ui_view.AddWidget(button_10)
        ui_view.AddWidget(button_11)
        ui_view.AddWidget(button_12)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean
    {
        if (event?.action == MotionEvent.ACTION_DOWN)
        {
            when (v?.id)
            {
                R.id.JumpButton -> player.jump = true
                R.id.LeftButton -> player.run = WayLeft
                R.id.RightButton -> player.run = WayRight
            }
        }
        else if (event?.action == MotionEvent.ACTION_UP)
        {
            when(v?.id)
            {
                R.id.JumpButton -> player.jump = false
                R.id.LeftButton -> player.run = None
                R.id.RightButton -> player.run = None
            }
        }

        return true
    }

}
