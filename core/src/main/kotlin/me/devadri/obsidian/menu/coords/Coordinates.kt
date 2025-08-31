package me.devadri.obsidian.menu.coords

import kotlin.math.abs

class Coordinates(raw: Int, column: Int) {

    val raw = abs(raw)
    val column = abs(column)

    fun toSlot(): Int {
        return column * raw
    }
}