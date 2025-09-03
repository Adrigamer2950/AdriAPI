package me.devadri.obsidian.menu.coords

import kotlin.math.abs

class Coordinates(raw: Int, column: Int) {

    val raw = abs(raw)
    val column = abs(column)

    fun toSlot(): Int {
        return column * raw
    }

    companion object {
        fun fromSlot(slot: Int): Coordinates {
            val row = slot / 9
            val column = slot % 9
            return Coordinates(row, column)
        }
    }
}