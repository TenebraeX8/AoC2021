package com.tenebraex8.aoc2021.day4

data class GridOpt(val values: Array<Array<Int>>){
    private val numbers = values.flatten()
    private val mapping: MutableMap<Int, Pair<Int, Int>> = mutableMapOf()
    private val n = values.size
    private val m = values.firstOrNull()?.size ?: 0
    private var lineHits = Array(n) {0}
    private var colHits = Array(m) {0}

    init {
        for(i in 0 until n){
            for(j in 0 until m) mapping[values[i][j]] = Pair(i, j)
        }
    }

    fun check(nr: Int): Boolean{
        if(nr in numbers){
            val (line, col) = mapping[nr]!!
            lineHits[line]++
            colHits[col]++
            return checkWinning()
        }
        else return false
    }

    private fun checkWinning() = lineHits.any{ it == m} || colHits.any { it == n}

    fun uncheckedSum(alreadyOccured: Set<Int>): Int{
        var sum = 0
        for(i in 0 until n){
            for(j in 0 until m){
                if(values[i][j] !in alreadyOccured) sum += values[i][j]
            }
        }
        return sum
    }

    fun reset(){
        this.lineHits = Array(n){0}
        this.colHits = Array(m){0}
    }

}
