import com.tenebraex8.aoc2021.*
import kotlin.math.ceil

val (section1, section2) = "14.inp".readSections()
val data = section1.trim()
val rules = section2.splitLine().map { it.splitTwo(" -> ") }.toMap()

var counts = rules.map{it.key to 0L}.toMap().toMutableMap()
(1 until data.count()).map {
    val key = data[it-1].toString() + data[it].toString()
    counts[key] = counts[key]!! + 1L
}

fun computeSolution(counts: Map<String, Long>): Long{
    val letters = mutableMapOf<Char, Long>()
    counts.forEach { (key, value)->
        key.forEach { letters[it] = (letters[it] ?: 0L) + value }
    }
    letters.keys.forEach { letters[it] = ceil(letters[it]!!.toDouble() / 2.0).toLong() }    //if count uneven, ceil because of start/end
    return letters.values.maxOf { it } - letters.values.minOf { it }
}

(0 until 40).forEach {
    val nextCounts = rules.map{it.key to 0L}.toMap().toMutableMap()
    rules.forEach { (key, value) ->
        if (key in counts) {
            val (first, second) = Pair(key.first().toString() + value, value + key[1].toString())
            nextCounts[first] = counts[key]!! + nextCounts[first]!!
            nextCounts[second] = counts[key]!! + nextCounts[second]!!
        }
    }
    counts = nextCounts
    if(it == 9) computeSolution(counts).firstSolution()
}
computeSolution(counts).secondSolution()

