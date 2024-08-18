package com.example.prac_timer

import com.example.prac_timer.databinding.ActivityStartBinding

class StartView(
    private val binding: ActivityStartBinding,
    private var totalPeople: Int
) {
    init {
        renderCount()
        setOnclickListenerOnBtnMinus()
        setOnclickListenerOnBtnPlus()
    }

    private fun setOnclickListenerOnBtnMinus() {
        binding.btnMinus.setOnClickListener {
            if (totalPeople > 1) {
                totalPeople--
                binding.tvCount.text = totalPeople.toString()
            }
        }
    }

    private fun setOnclickListenerOnBtnPlus() {
        binding.btnPlus.setOnClickListener {
            if (totalPeople < 99) {
                totalPeople++
                binding.tvCount.text = totalPeople.toString()
            }
        }
    }

    fun setOnclickListenerOnBtnStart(function: () -> Unit) {
        binding.btnStart.setOnClickListener {
            function()
        }
    }

    private fun renderCount() {
        binding.tvCount.text = totalPeople.toString()
    }

    fun isTotalPeopleBiggerThan(people: Int): Boolean {
        return totalPeople > people
    }

}