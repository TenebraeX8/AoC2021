import com.tenebraex8.aoc2021.*

val lines = "1.inp".readLinesAsInt()

//Short Version:

lines.indices.toList().stream().skip(1).map { lines[it-1] < lines[it]}.filter { it }.count().firstSolution()
val sums = lines.indices.toList().stream().skip(2).map { lines.slice(it-2..it).sum() }.mapToInt { it }.toArray()
sums.indices.toList().stream().skip(1).map { sums[it-1] < sums[it] }.filter { it }.count().secondSolution()


//Readable Version:
var last = lines[0]
var count = 0
lines.slice(1 until lines.count()).forEach {
    if(it > last) count ++
    last = it
}
println("Solution for Part 1: $count")

var lastPair = Pair<Int?, Int?>(null, null)
var partialSums = mutableListOf<Int>()
lines.forEach { value->
    when {
        lastPair.first == null -> lastPair = lastPair.mapTo { Pair<Int?, Int?>(value, null) }
        lastPair.second == null -> lastPair = lastPair.mapTo { Pair<Int?, Int?>(lastPair.first, value) }
        else -> {
            partialSums.add(lastPair.first!! + lastPair.second!! + value)
            lastPair = lastPair.mapTo { Pair<Int?, Int?>(lastPair.second, value) }
        }
    }
}

last = partialSums.first()
count = 0
partialSums.slice(1 until partialSums.count()).forEach {
    if(it > last) count ++
    last = it
}
println("Solution for Part 2: $count")