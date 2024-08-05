package com.example.sudoku

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sandro.sdoku.R
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gameBoard: GridLayout
    private val sudokuGrid = Array(9) { Array(9) { 0 } }
    private var selectedCell: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initClearButton()
        initNumberButtons()
        gameBoard = findViewById(R.id.gameBoard)
        initializeSudokuGrid() // 게임판 초기화
        displaySudoku()
    }

    private fun initNumberButtons() {
        val numberButtons = findViewById<GridLayout>(R.id.numberButtons)

        for (i in 0 until numberButtons.childCount) {
            val button = numberButtons.getChildAt(i) as Button
            button.setOnClickListener {
                selectedCell?.setText(button.text)
            }
        }
    }

    private fun initClearButton() {
        val clearButton = findViewById<Button>(R.id.clearButton)

        clearButton.setOnClickListener {
            selectedCell?.text = null // 현재 선택된 셀의 내용 지우기
        }
    }

    private fun initializeSudokuGrid() {
        val random = Random

        for (i in 0 until 9) {
            for (j in 0 until 9) {
                // 1부터 9까지의 랜덤 숫자 생성
                sudokuGrid[i][j] = random.nextInt(1, 10) // 1~9 사이의 숫자
            }
        }
    }

    private fun displaySudoku() {
        gameBoard.removeAllViews() // 기존 뷰 제거

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

                gameBoard.addView(textView)

                generateVerticalLine(j)
            }
            generateHorizontalLine(i)
        }
    }

    private fun generateHorizontalLine(i: Int) {
//        if (i == 2 || i == 5) {
//            val horizontalLine = View(this)
//            horizontalLine.layoutParams = GridLayout.LayoutParams().apply {
//                width = GridLayout.LayoutParams.MATCH_PARENT
//                height = 2 // 경계선 두께
//                rowSpec = GridLayout.spec(i + 1) // 경계선 위치
//                columnSpec = GridLayout.spec(0, 9) // 전체 너비
//            }
//            horizontalLine.setBackgroundColor(Color.BLACK) // 경계선 색상
//            gameBoard.addView(horizontalLine)
//        }
    }

    private fun generateVerticalLine(j: Int) {
//        if (j == 2 || j == 5) {
//            val verticalLine = View(this)
//            verticalLine.layoutParams = GridLayout.LayoutParams().apply {
//                width = 2 // 경계선 두께
//                height = GridLayout.LayoutParams.MATCH_PARENT
//                rowSpec = GridLayout.spec(0, 9) // 전체 높이
//                columnSpec = GridLayout.spec(j + 1) // 경계선 위치
//            }
//            verticalLine.setBackgroundColor(Color.BLACK) // 경계선 색상
//            gameBoard.addView(verticalLine)
//        }
    }
}
