package com.example.prac_timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.prac_timer.databinding.ActivityEndBinding
import com.example.prac_timer.databinding.ActivityMainBinding
import com.example.prac_timer.databinding.ActivityStartBinding
import java.util.Random
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var startBinding: ActivityStartBinding
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var endBinding: ActivityEndBinding

    private var timerTask: Timer? = null
    private val scores: MutableList<Float> = mutableListOf()
    private var people = 1
    private var isBlind = true
    private var tempScore: Float = 0.0f

    private var stage = 1

    // startView
    private val liveDataTotalPeople = MutableLiveData<Int>().apply { value = 2 }

    // mainView
    private val liveDataMainButton = MutableLiveData<String>().apply { value = "Start" }
    private val liveDataScore = MutableLiveData<String>().apply { value = "" }
    private val liveDataTimer = MutableLiveData<String>().apply { value = "0.00" }
    private val liveDataPeople = MutableLiveData<String>()
    private val liveDataGoal = MutableLiveData<Float>()

    // endView
    private val liveDataBiggestScore = MutableLiveData<Float>()
    private val liveDataEndPeople = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding = ActivityStartBinding.inflate(layoutInflater)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        endBinding = ActivityEndBinding.inflate(layoutInflater)

        // startView
        initStartView()

        // mainView
        setMainButtonOnClickListener()
        liveDataMainButton.observe(this) { mainBinding.buttonText = it }
        liveDataScore.observe(this) { mainBinding.score = it }
        liveDataTimer.observe(this) { mainBinding.timer = it }
        liveDataPeople.observe(this) { mainBinding.people = it }
        liveDataGoal.observe(this) { mainBinding.goal = it.toString() }

        // endView
        liveDataBiggestScore.observe(this) { endBinding.biggestScore = it.toString() }
        liveDataEndPeople.observe(this) { endBinding.endPeople = it }
        setRestartBtnOnclickListener()

        startView()
    }

    // startView
    private fun startView() {
        setContentView(startBinding.root)
    }

    private fun initStartView() {
        setOnclickListenerOnBtnMinus()
        setOnclickListenerOnBtnPlus()
        setStartBtnOnclickListener()
        liveDataTotalPeople.observe(this) {
            startBinding.totalPeople = it.toString()
        }
    }

    private fun setStartBtnOnclickListener() {
        startBinding.btnStart.setOnClickListener {
            people = 1
            mainView()
        }
    }

    private fun setOnclickListenerOnBtnMinus() {
        startBinding.btnMinus.setOnClickListener {
            liveDataTotalPeople.value?.let { totalPeople ->
                if (totalPeople > 1) {
                    liveDataTotalPeople.value = totalPeople - 1
                }
            }
        }
    }

    private fun setOnclickListenerOnBtnPlus() {
        startBinding.btnPlus.setOnClickListener {
            liveDataTotalPeople.value?.let { totalPeople ->
                if (totalPeople < 99) {
                    liveDataTotalPeople.value = totalPeople + 1
                }
            }
        }
    }

    // mainView
    private fun mainView() {
        setContentView(mainBinding.root)

        liveDataMainButton.value = "Start"
        liveDataPeople.value = "참가자 $people"
        liveDataScore.value = ""
        liveDataTimer.value = "0.00"
        liveDataGoal.value = Random().nextInt(1001) / 100f
        stage = 1
    }

    private fun setMainButtonOnClickListener() {
        mainBinding.btnCommand.setOnClickListener {
            when (stage % 3) {
                0 -> {
                    if (people < liveDataTotalPeople.value!!) {
                        people++
                        mainView()
                        stage++
                    } else {
                        endView()
                    }
                }

                1 -> {
                    start()
                    liveDataMainButton.value = "Stop"
                    stage++
                }

                2 -> {
                    stop()
                    stage++
                }
            }
        }
    }

    private fun start() {
        var sec = 0f
        timerTask = timer(period = 10) {
            runOnUiThread { // UI 변경은 runOnUiThread로 처리
                sec++
                tempScore = (sec / 100)
                if (isBlind) {
                    liveDataTimer.value = "???"
                } else {
                    liveDataTimer.value = tempScore.toString()
                }
            }
        }
    }

    private fun stop() {
        timerTask?.cancel()
        liveDataMainButton.value = "Next"

        val score = abs(liveDataGoal.value!! - tempScore)
        scores.add(score)

        mainBinding.score = "score: ${String.format("%.2f", score)}"
    }

    // endView
    private fun endView() {
        setContentView(endBinding.root)
        val maxScore = scores.max()
        liveDataBiggestScore.value = maxScore
        liveDataEndPeople.value = "참가자 ${scores.indexOf(maxScore) + 1}"
    }


    private fun setRestartBtnOnclickListener() {
        endBinding.btnRestart.setOnClickListener {
            scores.clear()
            startView()
        }
    }
}