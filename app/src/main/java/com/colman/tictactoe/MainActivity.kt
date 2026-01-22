package com.colman.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var playAgainButton: Button
    private lateinit var boardButtons: List<Button>
    private val game = TicTacToeGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupClickListeners()
        updateUI()
    }

    private fun initializeViews() {
        statusTextView = findViewById(R.id.statusTextView)
        playAgainButton = findViewById(R.id.playAgainButton)

        boardButtons = listOf(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8)
        )
    }

    private fun setupClickListeners() {
        boardButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onCellClicked(index)
            }
        }

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(position: Int) {
        val row = position / 3
        val col = position % 3

        if (game.makeMove(row, col)) {
            updateUI()
        }
    }

    private fun updateUI() {
        updateBoard()
        updateStatus()
    }

    private fun updateBoard() {
        for (i in boardButtons.indices) {
            val row = i / 3
            val col = i % 3
            val player = game.getCellValue(row, col)

            boardButtons[i].text = player?.symbol ?: ""

            when (player) {
                Player.X -> boardButtons[i].setTextColor(
                    ContextCompat.getColor(this, R.color.player_x)
                )
                Player.O -> boardButtons[i].setTextColor(
                    ContextCompat.getColor(this, R.color.player_o)
                )
                null -> boardButtons[i].setTextColor(
                    ContextCompat.getColor(this, R.color.text_primary)
                )
            }
        }
    }

    private fun updateStatus() {
        when (val state = game.getGameState()) {
            is GameState.InProgress -> {
                statusTextView.text = if (game.getCurrentPlayer() == Player.X) {
                    getString(R.string.player_x_turn)
                } else {
                    getString(R.string.player_o_turn)
                }
                playAgainButton.isEnabled = false
            }
            is GameState.Winner -> {
                statusTextView.text = if (state.player == Player.X) {
                    getString(R.string.player_x_wins)
                } else {
                    getString(R.string.player_o_wins)
                }
                playAgainButton.isEnabled = true
            }
            is GameState.Draw -> {
                statusTextView.text = getString(R.string.draw)
                playAgainButton.isEnabled = true
            }
        }
    }

    private fun resetGame() {
        game.reset()
        updateUI()
    }
}

