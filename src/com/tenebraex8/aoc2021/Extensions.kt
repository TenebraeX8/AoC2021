package com.tenebraex8.aoc2021

import java.io.File
import java.util.concurrent.TimeUnit
import java.util.stream.IntStream

fun String.readContent(): String = File(this).readText()
fun String.readLines(): List<String> = File(this).readLines()
fun String.readSections(delimiter: String ="\r\n\r\n") = this.readContent().split(delimiter)
fun String.readLinesAsInt() = this.readLines().map { it.toInt() }
fun String.readLinesAsLong() = this.readLines().map { it.toLong() }

fun String.charMapping(pattern: String, target: String): String{
    var newStr = this
    for(i in pattern.indices) newStr = newStr.replace(pattern[i], target[i])
    return newStr
}
fun String.remove(str: String) = this.replace(str, "")

fun <C,D> String.splitTwoMap(delimiter: String, trim: Boolean = false, mapper: (Pair<String,String>)->Pair<C,D>): Pair<C,D>
        = this.splitTwo(delimiter, trim).mapTo(mapper)

fun String.splitTwo(delimiter: String, trim: Boolean = false): Pair<String, String>{
    val splitter = this.split(delimiter).map { if(trim) it.trim() else it }
    return Pair(splitter[0], splitter.slice(1 until splitter.size).joinToString(delimiter))
}

fun String.splitAndRemoveEmpty(pattern: String) = this.split(pattern).filter { it.isNotEmpty() }

fun String.splitLine() = this.split("\r\n")

fun String.runCommand() {
    ProcessBuilder(*this.split(" ").toTypedArray())
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(60, TimeUnit.MINUTES)
}

fun java.lang.StringBuilder.deleteLast(){
    this.deleteCharAt(this.length-1)
}

fun <A,B,C,D> Pair<A,B>.mapTo(mapper: (Pair<A,B>)->Pair<C,D>): Pair<C,D> = mapper.invoke(this)
fun <A, B> Pair<A, A>.mapping(mapper: (A)->B): Pair<B, B> = Pair(mapper.invoke(this.first), mapper.invoke(this.second))
fun <A,B,C,D,E,F> Triple<A,B,C>.mapTo(mapper: (Triple<A,B,C>)->Triple<D,E,F>): Triple<D,E,F> = mapper.invoke(this)
fun Pair<Int, Int>.toRange() = this.first..this.second

fun IntStream.collectToString() = this.collect(::StringBuilder, StringBuilder::appendCodePoint, StringBuilder::append).toString()


fun Any.doPrintln(prefix: String = "") {
    print(prefix)
    println(this)
}

fun List<Any>.inBounds(idx: Int) = idx in this.indices
fun <T> List<T>.enumerate(enumerator: (Int, T)->Unit){
    for(idx in this.indices) enumerator(idx, this[idx])
}

fun <T> List<T>.allElementsEquals(other: List<T>): Boolean{
    if(this.size != other.size) return false
    for(idx in this.indices){
        if(this[idx]!! != other[idx]) return false
    }
    return true
}

fun List<String>.collect(): String = this.joinToString("")
fun <T> List<T>.copy(): List<T> = this.toMutableList()


fun Any.firstSolution(){
    this.doPrintln("Solution for Part 1: ")
}

fun Any.secondSolution(){
    this.doPrintln("Solution for Part 2: ")
}

fun Iterable<Int>.median(): Int {
    val sorted = this.sorted()
    return if (sorted.size % 2 == 0) ((sorted[sorted.size / 2] + sorted[sorted.size / 2 - 1]) / 2)
           else (sorted[sorted.size / 2])
}

fun <T: Any> List<List<T>>.adjacents2D(lineIdx: Int, colIdx: Int): List<Pair<Int, Int>>{
    val indicess = mutableListOf<Pair<Int, Int>>()
    val leftInBound = this.first().inBounds(colIdx - 1)
    val rightInBound = this.first().inBounds(colIdx + 1)
    if(this.inBounds(lineIdx - 1)){
        if(leftInBound) indicess.add(Pair(lineIdx - 1, colIdx - 1))
        indicess.add(Pair(lineIdx - 1, colIdx))
        if(rightInBound) indicess.add(Pair(lineIdx - 1, colIdx + 1))
    }
    if(leftInBound) indicess.add(Pair(lineIdx, colIdx - 1))
    if(rightInBound) indicess.add(Pair(lineIdx, colIdx + 1))
    if(this.inBounds(lineIdx + 1)){
        if(leftInBound) indicess.add(Pair(lineIdx + 1, colIdx - 1))
        indicess.add(Pair(lineIdx + 1, colIdx))
        if(rightInBound) indicess.add(Pair(lineIdx + 1, colIdx + 1))
    }
    return indicess
}

fun <T: Any> List<List<T>>.adjacents2DNonDiagonal(lineIdx: Int, colIdx: Int): List<Pair<Int, Int>>{
    val indicess = mutableListOf<Pair<Int, Int>>()
    val leftInBound = this.first().inBounds(colIdx - 1)
    val rightInBound = this.first().inBounds(colIdx + 1)
    if(this.inBounds(lineIdx - 1)) indicess.add(Pair(lineIdx - 1, colIdx))
    if(leftInBound) indicess.add(Pair(lineIdx, colIdx - 1))
    if(rightInBound) indicess.add(Pair(lineIdx, colIdx + 1))
    if(this.inBounds(lineIdx + 1)) indicess.add(Pair(lineIdx + 1, colIdx))
    return indicess
}


typealias Index2D = Pair<Int, Int>

fun <T: Any> List<List<T>>.pairIdx(idx: Index2D) = this[idx.first][idx.second]
fun <T: Any> List<MutableList<T>>.pairAssign(idx: Index2D, value: T){ this[idx.first][idx.second] = value}
fun <T: Any> List<List<T>>.inBounds(idx: Index2D) = (idx.first in this.indices) && (idx.second in this.first().indices)
fun <T: Any> List<List<T>>.adjacents2D(idx: Index2D) = this.adjacents2D(idx.first, idx.second)
fun <T: Any> List<List<T>>.adjacents2DNonDiagonal(idx: Index2D) = this.adjacents2DNonDiagonal(idx.first, idx.second)
fun Index2D.incX(value: Int = 1) = Pair(this.first, this.second + value)
fun Index2D.incY(value: Int = 1) = Pair(this.first + value, this.second)


//Stack
fun <T> MutableList<T>.push(item: T) = add(item)
fun <T> MutableList<T>.pop() = if(isNotEmpty()) removeLast() else null
fun <T> MutableList<T>.peek() = lastOrNull()