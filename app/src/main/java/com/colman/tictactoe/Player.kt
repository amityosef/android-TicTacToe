package com.colman.tictactoe

enum class Player(val symbol: String) {
    X("X"),
    O("O");

    fun other(): Player = if (this == X) O else X
}
