package com.example.prac_timer

import android.widget.Button
import android.widget.TextView

class StartView(
    private val tvCount: TextView,
    private val btnMinus: Button,
    private val btnPlus: Button,
    private val btnStart: Button,
    private var totalPeople: Int
) {
    init {
        renderCount()
        setOnclickListenerOnBtnMinus()
        setOnclickListenerOnBtnPlus()
    }

    private fun setOnclickListenerOnBtnMinus() {
        btnMinus.setOnClickListener {
            if (totalPeople > 1) {
                totalPeople--
                tvCount.text = totalPeople.toString()
            }
        }
    }

    private fun setOnclickListenerOnBtnPlus() {
        btnPlus.setOnClickListener {
            if (totalPeople < 99) {
                totalPeople++
                tvCount.text = totalPeople.toString()
            }
        }
    }

    fun setOnclickListenerOnBtnStart(function: () -> Unit) {
        btnStart.setOnClickListener {
            function()
        }
    }

    private fun renderCount() {
        tvCount.text = totalPeople.toString()
    }

    fun isTotalPeopleBiggerThan(people: Int): Boolean {
        return totalPeople > people
    }

}