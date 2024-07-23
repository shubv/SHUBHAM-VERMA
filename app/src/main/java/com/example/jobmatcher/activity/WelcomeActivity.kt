package com.example.jobmatcher.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.example.jobmatcher.R

class WelcomeActivity : AppCompatActivity() {
    var btn_register : Button?=null
    var btn_login : Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        setContentView(R.layout.activity_welcome)


        btn_register = findViewById(R.id.btn_register) as Button
        btn_register!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@WelcomeActivity, RegisterActivity::class.java))
            finish()
        })
        btn_login = findViewById(R.id.btn_login) as Button
        btn_login!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
            finish()
        })
    }
}