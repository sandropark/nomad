package com.example.prac_timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var startViewModel: StartViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var endViewModel: EndViewModel

    private var timerTask: Timer? = null
    private val scores: MutableList<Float> = mutableListOf()
    private var people = 1
    private var isBlind = true
    private var tempScore: Float = 0.0f
    private var stage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding = ActivityStartBinding.inflate(layoutInflater)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        endBinding = ActivityEndBinding.inflate(layoutInflater)

        initStartView()
        initMainView()
        initEndView()
    }

    override fun onStart() {
        super.onStart()
        startView()
    }

    // startView
    private fun startView() {
        setContentView(startBinding.root)
    }

    private fun initStartView() {
        startViewModel = ViewModelProvider(this)[StartViewModel::class.java]
        startBinding.btnMinus.setOnClickListener { startViewModel.minus() }
        startBinding.btnPlus.setOnClickListener { startViewModel.plus() }
        startBinding.btnStart.setOnClickListener {
            people = 1
            mainView()
        }
        startViewModel.liveDataTotalPeople.observe(this) { startBinding.totalPeople = it.toString() }
    }

    // mainView
    private fun initMainView() {
        setMainButtonOnClickListener()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.mainButton.observe(this) { mainBinding.buttonText = it }
        mainViewModel.score.observe(this) { mainBinding.score = it.toString() }
        mainViewModel.timer.observe(this) { mainBinding.timer = it }
        mainViewModel.people.observe(this) { mainBinding.people = it }
        mainViewModel.goal.observe(this) { mainBinding.goal = it.toString() }
    }

    private fun mainView() {
        setContentView(mainBinding.root)
        mainViewModel.mainButton.value = "Start"
        mainViewModel.people.value = "참가자 $people"
        mainViewModel.score.value = ""
        mainViewModel.timer.value = "0.00"
        mainViewModel.goal.value = Random().nextInt(1001) / 100f
        stage = 1
    }

    private fun setMainButtonOnClickListener() {
        mainBinding.btnCommand.setOnClickListener {
            when (stage % 3) {
                0 -> {
                    if (people < startViewModel.liveDataTotalPeople.value!!) {
                        people++
                        mainView()
                    } else {
                        endView()
                    }
                }

                1 -> {
                    start()
                    mainViewModel.mainButton.value = "Stop"
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
                    mainViewModel.timer.value = "???"
                } else {
                    mainViewModel.timer.value = tempScore.toString()
                }
            }
        }
    }

    private fun stop() {
        timerTask?.cancel()
        mainViewModel.mainButton.value = "Next"

        val score = abs(mainViewModel.goal.value!! - tempScore)
        scores.add(score)

        mainBinding.score = "score: ${String.format("%.2f", score)}"
    }

    // endView
    private fun initEndView() {
        endViewModel = ViewModelProvider(this)[EndViewModel::class.java]
        endViewModel.liveDataBiggestScore.observe(this) { endBinding.biggestScore = it.toString() }
        endViewModel.liveDataEndPeople.observe(this) { endBinding.endPeople = it }
        endBinding.btnRestart.setOnClickListener {
            scores.clear()
            startView()
        }
    }

    private fun endView() {
        setContentView(endBinding.root)
        val maxScore = scores.max()
        endViewModel.liveDataBiggestScore.value = maxScore
        endViewModel.liveDataEndPeople.value = "참가자 ${scores.indexOf(maxScore) + 1}"
    }

}