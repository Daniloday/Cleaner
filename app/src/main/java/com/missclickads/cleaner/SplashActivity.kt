package com.missclickads.cleaner

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progressBarSplash)
        val timer = 4 * 1000
        val animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animation.duration = (timer).toLong()
        animation.start()
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }, (timer).toLong())
    }


}