package com.colman.tictactoe

sealed class GameState {
    object InProgress : GameState()
    data class Winner(val player: Player) : GameState()
    object Draw : GameState()
}

