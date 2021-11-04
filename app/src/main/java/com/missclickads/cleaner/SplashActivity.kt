package com.missclickads.cleaner

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    var closed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        MobileAds.initialize(this) {}
        val progressBar = findViewById<ProgressBar>(R.id.progressBarSplash)
        val timer = (8 * 1000).toLong()
        val animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animation.duration = timer
        animation.start()
        lifecycleScope.launch {
            delay(timer)
//            if (mInterstitialAd != null) {
//                mInterstitialAd?.show(this@SplashActivity)
//            } else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent)
                closed = true
                finish()
//            }
        }

        //ads
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            getString(R.string.splash_ads),
            adRequest,
            object : InterstitialAdLoadCallback() {

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                mInterstitialAd = null
                                closed = true
                                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            override fun onAdShowedFullScreenContent() {
                                mInterstitialAd = null
                            }
                        }
                }
        })
    }

    override fun onResume() {
        super.onResume()
        if(closed) finish()

    }


}