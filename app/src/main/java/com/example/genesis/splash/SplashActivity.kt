package com.example.genesis.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.genesis.MainActivity
import com.example.genesis.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private var progressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        // on below line we are calling
        // handler to run a task
        // for specific time interval
    }

    override fun onStart() {
        super.onStart()
        val a = findViewById<ProgressBar>(R.id.idPBLoading)
        lifecycleScope.launch {
            while(true){
                progressStatus +=1
                delay(1000)

                if (a.progress < 100) {
                    a?.setProgress(progressStatus)
                    progressStatus += 50

                } else {
                    var intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    break
                }
            }
        }
    }
}