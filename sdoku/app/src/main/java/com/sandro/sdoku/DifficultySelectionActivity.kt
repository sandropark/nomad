package com.sandro.sdoku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sudoku.MainActivity

class DifficultySelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiti_difficulty_selection)

        val easyButton: Button = findViewById(R.id.easyButton)
        val mediumButton: Button = findViewById(R.id.mediumButton)
        val hardButton: Button = findViewById(R.id.hardButton)
        val expertButton: Button = findViewById(R.id.expertButton)

        easyButton.setOnClickListener { startGame("easy") }
        mediumButton.setOnClickListener { startGame("medium") }
        hardButton.setOnClickListener { startGame("hard") }
        expertButton.setOnClickListener { startGame("expert") }
    }

    private fun startGame(difficulty: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("DIFFICULTY", difficulty)
        startActivity(intent)
        finish() // 현재 액티비티 종료
    }
}
