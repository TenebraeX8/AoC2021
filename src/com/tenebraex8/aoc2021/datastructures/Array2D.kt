package com.tenebraex8.aoc2021.datastructures

import java.lang.StringBuilder

data class ArrayIndex(val row: Int, val col: Int){
    val x = col
    val y = row

    fun incCol(value: Int = 1) = ArrayIndex(row, col + value)
    fun incRow(value: Int = 1) = ArrayIndex(row + value, col)
    fun inc(value: Int = 1)    = ArrayIndex(row + value, col + value)
    fun decCol(value: Int = 1) = ArrayIndex(row, col - value)
    fun decRow(value: Int = 1) = ArrayIndex(row - value, col)
    fun dec(value: Int = 1)    = ArrayIndex(row - value, col - value)

    fun adjacent() = listOf(
        this.dec(), this.decRow(), this.decRow().incCol(),
        this.decCol(),             this.incCol(),
        this.incRow().decCol(), this.incRow(), this.inc()
    )

    fun kernel() = listOf(
        this.dec(), this.decRow(), this.decRow().incCol(),
        this.decCol(), this.clone(), this.incCol(),
        this.incRow().decCol(), this.incRow(), this.inc()
    )

    fun clone() = ArrayIndex(this.row, this.col)

}

class Array2D<T>(val n: Int, val m: Int, initializer: (Int)->T) : Iterable<T>{
    private val innerStructure = List(n){ row->
            MutableList(m){col->
                initializer.invoke(m * row + col)
            }
        }

    operator fun get(row: Int, col: Int) = innerStructure[row][col]
    operator fun get(idx: ArrayIndex) = innerStructure[idx.row][idx.col]
    operator fun set(row: Int, col: Int, value: T){ innerStructure[row][col] = value }
    operator fun set(idx: ArrayIndex, value: T){ innerStructure[idx.row][idx.col] = value }

    fun flat(): List<T> = innerStructure.flatMap { it.toList() }
    fun inRow(idx: Int) = idx in 0 until n
    fun inCol(idx: Int) = idx in 0 until m
    fun inBounds(row: Int, col: Int) = inRow(row) && inCol(col)
    fun inBounds(idx: ArrayIndex) = inBounds(idx.row, idx.col)

    fun lines() = this.innerStructure.map { it.toList() }.toList()

    fun adjacentIndicess(idx: ArrayIndex) = idx.adjacent().filter { this.inBounds(it) }
    fun kernelIndicess(idx: ArrayIndex) = idx.kernel().filter { this.inBounds(it) }

    fun indices() = iterator{
        innerStructure.indices.forEach {line->
            innerStructure[line].indices.forEach {col->
                yield(ArrayIndex(line, col))
            }
        }
    }

    fun frameIndices(offset: Int) = iterator {
        val start = 0 - offset
        val lineEnd = this@Array2D.n + offset
        val colEnd = this@Array2D.m + offset
        for(lineIdx in start until lineEnd){
            for(colIdx in start until colEnd) yield(ArrayIndex(lineIdx, colIdx))
        }
    }

    override fun iterator() = iterator {
       indices().forEach { yield(this@Array2D[it]) }
    }

    override fun toString(): String {
        val builder = StringBuilder("[")
        var first = true
        this.lines().forEach {
            if(first) first = false
            else builder.append(", ")
            builder.append("[")
            builder.append(it.map { it.toString() }.joinToString(","))
            builder.append("]")
        }
        return builder.append("]").toString()
    }

    inline fun <reified U> map(mapper: (T)->U) = Array(n){row->
        Array(m){col->
            mapper.invoke(this[row, col])
        }
    }
    inline fun <reified U> mapIndex(mapper: (ArrayIndex)->U) = Array(n){row->
        Array(m){col->
            mapper.invoke(ArrayIndex(row, col))
        }
    }

    companion object{
        fun <T> fromIterable(values: Iterable<Iterable<T>>) = Array2D(values.count(), values.first().count()){
            values.elementAt(it / values.count()).elementAt(it % values.count())
        }
    }
}

fun <T> Iterable<Iterable<T>>.toArray2D() = Array2D.fromIterable(this)