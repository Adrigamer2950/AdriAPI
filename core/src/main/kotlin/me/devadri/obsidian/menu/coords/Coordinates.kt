package me.devadri.obsidian.menu.coords

import kotlin.math.abs

class Coordinates(row: Int, column: Int) {

    val row = abs(row)
    val column = abs(column)

    fun toSlot(): Int {
        return column * row
    }

    companion object {
        fun fromSlot(slot: Int): Coordinates {
            val row = slot / 9
            val column = slot % 9
            return Coordinates(row, column)
        }
    }
}