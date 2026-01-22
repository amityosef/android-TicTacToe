package com.colman.tictactoe

class TicTacToeGame {
    private val board = Array(3) { arrayOfNulls<Player>(3) }
    private var currentPlayer = Player.X
    private var gameState: GameState = GameState.InProgress

    fun makeMove(row: Int, col: Int): Boolean {
        if (gameState != GameState.InProgress || board[row][col] != null) {
            return false
        }

        board[row][col] = currentPlayer
        gameState = checkGameState()

        if (gameState == GameState.InProgress) {
            currentPlayer = currentPlayer.other()
        }

        return true
    }

    fun getCellValue(row: Int, col: Int): Player? = board[row][col]

    fun getCurrentPlayer(): Player = currentPlayer

    fun getGameState(): GameState = gameState

    fun reset() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = null
            }
        }
        currentPlayer = Player.X
        gameState = GameState.InProgress
    }

    private fun checkGameState(): GameState {
        // Check rows
        for (i in 0..2) {
            if (board[i][0] != null &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2]
            ) {
                return GameState.Winner(board[i][0]!!)
            }
        }

        // Check columns
        for (j in 0..2) {
            if (board[0][j] != null &&
                board[0][j] == board[1][j] &&
                board[1][j] == board[2][j]
            ) {
                return GameState.Winner(board[0][j]!!)
            }
        }

        // Check diagonals
        if (board[0][0] != null &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]
        ) {
            return GameState.Winner(board[0][0]!!)
        }

        if (board[0][2] != null &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]
        ) {
            return GameState.Winner(board[0][2]!!)
        }

        // Check for draw
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == null) {
                    return GameState.InProgress
                }
            }
        }

        return GameState.Draw
    }
}

