package com.example.alarm_simple_ex

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm_simple_ex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, AlarmReceiver.NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        binding.apply {
            AlarmBtn.setOnClickListener {
                Toast.makeText(this@MainActivity, "알람등록!", Toast.LENGTH_SHORT).show()
                val triggerTime = (SystemClock.elapsedRealtime()
                        + 5 * 1000)//초 * 1000 -> 60000 == (1분)
                alarmManager.set(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
            cancleBtn.setOnClickListener {
                if (alarmManager != null){
                    alarmManager.cancel(pendingIntent)
                }
            }
        }
    }
}