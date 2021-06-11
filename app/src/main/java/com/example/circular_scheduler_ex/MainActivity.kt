package com.example.circular_scheduler_ex

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.circular_scheduler_ex.databinding.ActivityMainBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.utils.ColorTemplate

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val startTime = System.currentTimeMillis()
    var fromAngle = 0f
    val standardTime = 60000F//임시로 1분!
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val handler = Handler()
        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(500f, "술래잡기"))
        entries.add(PieEntry(500f, "고무줄놀이"))
        entries.add(PieEntry(500f, "말뚝박기"))
        entries.add(PieEntry(500f, "망까기"))
        entries.add(PieEntry(500f, "말타기"))
        entries.add(PieEntry(500f, "잠자기"))

        val colorsItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(entries,"")
        pieDataSet.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 20f
        }

        val pieData = PieData(pieDataSet)
        binding.chart.apply {
            data = pieData
            isHighlightPerTapEnabled = false
            pieDataSet.setDrawValues(false)
            description.isEnabled = false
            isRotationEnabled = false
            centerText = "둥글지"
            legend.isEnabled = false
            setUsePercentValues(false)
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }

        binding.clickedChart.apply {
            data = pieData
            pieDataSet.setDrawValues(false)
            isHighlightPerTapEnabled = false
            description.isEnabled = false
            isRotationEnabled = true
            legend.isEnabled = false
            setUsePercentValues(false)
            setEntryLabelColor(Color.BLACK)
            rotationAngle = 0f
            extraBottomOffset = -420f
        }

        val gesture = object : OnChartGestureListener{//클릭을 보자!!
            override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
            }

            override fun onChartFling(me1: MotionEvent?, me2: MotionEvent?, velocityX: Float, velocityY: Float) {
            }

            override fun onChartSingleTapped(me: MotionEvent?) {
                binding.apply {
                    if (chart.visibility == View.VISIBLE && clickedChart.visibility == View.GONE){
                        chart.visibility = View.GONE
                        clickedChart.visibility = View.VISIBLE
                    }
                    else{
                        chart.visibility = View.VISIBLE
                        clickedChart.visibility = View.GONE
                    }
                }
            }

            override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
            }

            override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {

            }

            override fun onChartLongPressed(me: MotionEvent?) {
            }

            override fun onChartDoubleTapped(me: MotionEvent?) {
            }

            override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
            }
        }

        binding.chart.onChartGestureListener = gesture
        binding.clickedChart.onChartGestureListener = gesture

        val handlerTask = object : Runnable {//주기적으로 시간표를 돌리는 핸들러
            override fun run() {
                val currentTime = System.currentTimeMillis()//현재 시간
                val elapsedTime = (startTime - currentTime) * (-1)//총 지난 시간
                var toAngle = 360F * (elapsedTime/standardTime)//움직일 각도

                binding.chart.apply {
                    spin(1000, fromAngle, toAngle, Easing.Linear)
                    fromAngle = toAngle
                }
                handler.postDelayed(this, 1000)//1초 = 1000
            }
        }
        handler.post(handlerTask) // 주기적으로 실행
    }
}

