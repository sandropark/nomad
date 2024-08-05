package com.example.sudoku

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sandro.sdoku.R

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private val sudokuGrid = Array(9) { Array(9) { 0 } }
    private var selectedCell: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        generateSudoku()
        displaySudoku()

        // 숫자 버튼에 클릭 리스너 추가
        val numberButtons = findViewById<GridLayout>(R.id.numberButtons)

        for (i in 0 until numberButtons.childCount) {
            val button = numberButtons.getChildAt(i) as Button
            button.setOnClickListener {
                selectedCell?.setText(button.text)
            }
        }

        // onCreate() 메서드 내에서
        val clearButton = findViewById<Button>(R.id.clearButton)

        clearButton.setOnClickListener {
            selectedCell?.setText("") // 현재 선택된 셀의 내용 지우기
        }
    }

    private fun generateSudoku() {
        // 스도쿠 퍼즐을 생성하는 로직을 추가합니다.
        // 여기서는 간단한 예제로 기본 값을 설정합니다.
        sudokuGrid[0] = arrayOf(5, 3, 0, 0, 7, 0, 0, 0, 0)
        sudokuGrid[1] = arrayOf(6, 0, 0, 1, 9, 5, 0, 0, 0)
        sudokuGrid[2] = arrayOf(0, 9, 8, 0, 0, 0, 0, 6, 0)
        sudokuGrid[3] = arrayOf(8, 0, 0, 0, 6, 0, 0, 0, 3)
        sudokuGrid[4] = arrayOf(4, 0, 0, 8, 0, 3, 0, 0, 1)
        sudokuGrid[5] = arrayOf(7, 0, 0, 0, 2, 0, 0, 0, 6)
        sudokuGrid[6] = arrayOf(0, 6, 0, 0, 0, 0, 2, 8, 0)
        sudokuGrid[7] = arrayOf(0, 0, 0, 4, 1, 9, 0, 0, 5)
        sudokuGrid[8] = arrayOf(0, 0, 0, 0, 8, 0, 0, 7, 9)
    }

    private fun displaySudoku() {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                val textView = TextView(this)
                textView.layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(j, 1f)
                    rowSpec = GridLayout.spec(i, 1f)
                }
                textView.text = if (sudokuGrid[i][j] != 0) sudokuGrid[i][j].toString() else ""
                textView.gravity = Gravity.CENTER
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
                textView.setTextColor(Color.BLACK)
                textView.setBackgroundResource(R.drawable.cell_background)

                // 클릭 리스너 추가
                textView.setOnClickListener {
                    selectedCell?.setBackgroundResource(R.drawable.cell_background)
                    selectedCell = textView
                    selectedCell?.setBackgroundResource(R.drawable.selected_cell_background) // 선택된 셀 강조
                }

                gridLayout.addView(textView)
            }
        }
    }


}
