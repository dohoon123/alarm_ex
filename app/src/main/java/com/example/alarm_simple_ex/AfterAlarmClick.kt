package com.example.alarm_simple_ex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm_simple_ex.databinding.ActivityAfterAlarmClickBinding

class AfterAlarmClick : AppCompatActivity() {
    lateinit var binding: ActivityAfterAlarmClickBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterAlarmClickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init()
        if(intent != null){
            if(intent.hasExtra("AlarmPoint")){
                val AlarmPoint = intent.getIntExtra("AlarmPoint", 0)
                binding.textView.text = AlarmPoint.toString() + "번 알람봤죠?"
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            if (intent.hasExtra("AlarmPoint")) {//스케줄 db에 알람횟수 항목도 넣어야 할 듯 합니당..
                val AlarmPoint = intent.getIntExtra("AlarmPoint", 0)
                binding.textView.text = AlarmPoint.toString() + "번 알람봤죠?"
            }
        }
    }
}