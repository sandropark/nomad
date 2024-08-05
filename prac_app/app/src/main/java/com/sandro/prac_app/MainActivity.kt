package com.sandro.prac_app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.tv_hello)
        val btn: Button = findViewById(R.id.btn_kr)

        btn.setOnClickListener {
            if (btn.text == "한글로") {
                tv.text = "안녕"
                btn.text = "영어로"
            }

            if (btn.text == "영어로") {
                tv.text = "Hello"
                btn.text = "한글로"
            }
        }
    }
}