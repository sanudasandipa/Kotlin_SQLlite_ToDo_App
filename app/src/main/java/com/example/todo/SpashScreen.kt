package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper

class SpashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)
        android.os.Handler(Looper.getMainLooper())
            .postDelayed(
                {
                    startActivity(Intent(this@SpashScreen,MainActivity::class.java))
                    finish()
                },5000
            )
    }
}