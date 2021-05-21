package com.example.favdish.view.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.favdish.R
import com.example.favdish.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            //for android R and above
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {

            //setting the flags for full screen
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
//        }

        //initializing the splash activity binding
        val splashBinding: ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        /*
            declare the splash Animation with anim file and setting up the animation
            to the text of splash screen
         */
        val splashAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        splashBinding.tvAppName.animation = splashAnimation


        //setting up the animation listener for declared splash Animation
        splashAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                //
            }

            //apply alldishes activity at the end of the animation
            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }, 1000)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                //
            }

        })

    }
}