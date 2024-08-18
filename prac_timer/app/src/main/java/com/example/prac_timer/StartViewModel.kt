package com.example.prac_timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {
    val liveDataTotalPeople = MutableLiveData<Int>().apply { value = 2 }

    fun minus() {
        liveDataTotalPeople.value?.let { totalPeople ->
            if (totalPeople > 1) {
                liveDataTotalPeople.value = totalPeople - 1
            }
        }
    }

    fun plus() {
        liveDataTotalPeople.value?.let { totalPeople ->
            if (totalPeople < 99) {
                liveDataTotalPeople.value = totalPeople + 1
            }
        }
    }
}