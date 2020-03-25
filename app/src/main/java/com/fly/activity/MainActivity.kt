package com.fly.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.fly.game.Game
import com.fly.mygame.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener
{
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        StartButton.setOnClickListener(this)
        ExitButton.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        if (v != null)
        {
            when (v.id)
            {
                R.id.StartButton -> StartGame()
                R.id.ExitButton -> ExitGame()
            }
        }
    }

    fun StartGame()
    {
        val intent = Intent(this,Game::class.java)
        startActivity(intent);  //开始跳转
    }

    fun ExitGame()
    {
        this.finish();
        val id = Process.myPid()
        Process.killProcess(id)
    }
}
