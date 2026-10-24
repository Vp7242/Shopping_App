package com.shoppingapp.store.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.shoppingapp.store.R

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val openIntro = Runnable {
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.postDelayed(openIntro, SPLASH_DELAY)
    }

    override fun onDestroy() {
        handler.removeCallbacks(openIntro)
        super.onDestroy()
    }

    companion object {
        private const val SPLASH_DELAY = 1800L
    }
}
