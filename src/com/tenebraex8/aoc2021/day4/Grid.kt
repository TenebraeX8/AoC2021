package com.tenebraex8.aoc2021.day4

data class Grid(val values: Array<Array<Int>>){
    private var checked: Array<Array<Boolean>>
    private val numbers = values.flatten()
    private val lineMapping: MutableMap<Int, Int> = mutableMapOf()
    private val n = values.size
    private val m = values.firstOrNull()?.size ?: 0

    init {
        checked = Array(values.size){Array(values.first().size){false} }
        for(i in 0 until n){
            for(j in 0 until m) lineMapping[values[i][j]] = i
        }
    }

    fun check(nr: Int): Boolean{
        if(nr in numbers){
            val line = lineMapping[nr]!!
            val col = values[line].indexOf(nr)
            checked[line][col] = true
            return checkWinning()
        }
        else return false
    }

    private fun checkWinning(): Boolean {
        if(checked.any { it.all { it } }) return true

        for(j in 0 until m) {
            var allTrue = true
            for (i in 0 until n) {
                allTrue = checked[i][j]
                if(!allTrue) break
            }
            if(allTrue) return true
        }
        return false
    }

    fun uncheckedSum(): Int{
        var sum = 0
        for(i in 0 until n){
            for(j in 0 until m){
                if(!checked[i][j]) sum += values[i][j]
            }
        }
        return sum
    }

    fun reset(){
        this.checked = Array(values.size){Array(values.first().size){false} }
    }

}
