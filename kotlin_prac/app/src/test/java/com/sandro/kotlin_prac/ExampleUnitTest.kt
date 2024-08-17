package com.sandro.kotlin_prac

import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun 람다() {
        val result = b { str -> "$str 람다함수" }
        assertEquals("b가 호출한 람다함수", result)
    }

    private fun b(function: (String) -> String): String {
        return function("b가 호출한")
    }
}