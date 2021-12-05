import com.tenebraex8.aoc2021.*
import kotlin.math.abs

data class Point(val x: Int, val y: Int){
    fun stepTo(other: Point) = Point(this.x + (other.x - this.x) / abs(other.x - this.x), this.y +  (other.y - this.y) /abs(other.y - this.y))
    fun pair() = Pair(x, y)
}

data class Line(val start: Point, val end: Point){
    fun xRange(): IntRange = if(start.x < end.x) start.x..end.x else end.x..start.x
    fun yRange(): IntRange = if(start.y < end.y) start.y..end.y else end.y..start.y
}


val input = "5.inp".readLines()
                   .map { it.splitTwo(" -> ", true) }
                   .map { Pair(it.first.splitTwo(",", true), it.second.splitTwo(",", true))}
                   .map { Line(Point(it.first.first.toInt(), it.first.second.toInt()), Point(it.second.first.toInt(), it.second.second.toInt())) }

val straightLines = input.filter { (it.start.x == it.end.x) or (it.start.y == it.end.y) }
val IndicessMaps = mutableMapOf<Pair<Int, Int>, Int>()
straightLines.forEach {
    for(x in it.xRange()){
        for(y in it.yRange()) IndicessMaps[Pair(x,y)] = (IndicessMaps[Pair(x,y)] ?: 0) +  1
    }
}
IndicessMaps.values.filter { it > 1 }.count().firstSolution()

val diagonalLines = input.filter { (it.start.x != it.end.x) and (it.start.y != it.end.y) }
diagonalLines.forEach {
    var current = it.start
    while(current != it.end) {
        IndicessMaps[current.pair()] = (IndicessMaps[current.pair()] ?: 0) +  1
        current = current.stepTo(it.end)
    }
    IndicessMaps[current.pair()] = (IndicessMaps[current.pair()] ?: 0) +  1
}

IndicessMaps.values.filter { it > 1 }.count().secondSolution()