import com.tenebraex8.aoc2021.*

val input = "9.inp".readLines().map { it.splitAndRemoveEmpty("").map { it.toInt() } }

var lowPoints = mutableListOf<Pair<Int, Int>>()
input.indices.forEach {lineIdx ->
    input[lineIdx].indices.forEach { colIdx ->
        val value = input[lineIdx][colIdx]
        val left = (colIdx <= 0) || (input[lineIdx][colIdx-1] > value)
        val right = (colIdx >= input[lineIdx].size - 1) || (input[lineIdx][colIdx+1] > value)
        val up = (lineIdx <= 0) || (input[lineIdx-1][colIdx] > value)
        val down = (lineIdx >= input.size - 1) || (input[lineIdx+1][colIdx] > value)
        if(left and right and up and down) lowPoints.add(Pair(lineIdx, colIdx))
    }
}
lowPoints.map { input[it.first][it.second] + 1 }.sum().firstSolution()

val END_OF_FLOW = 9

fun recursiveFlow(idx: Pair<Int, Int>, input: List<List<Int>>, visited: MutableSet<Pair<Int, Int>>): Int =
    if((input[idx.first][idx.second] != END_OF_FLOW) && (idx !in visited)){
        var count = 1   //current
        visited.add(idx)
        if(input.inBounds(idx.first - 1)) count += recursiveFlow(Pair(idx.first - 1, idx.second), input, visited)   //up
        if(input.inBounds(idx.first + 1)) count += recursiveFlow(Pair(idx.first + 1, idx.second), input, visited)   //down
        if(input[idx.first].inBounds(idx.second - 1)) count += recursiveFlow(Pair(idx.first, idx.second-1), input, visited)   //left
        if(input[idx.first].inBounds(idx.second + 1)) count += recursiveFlow(Pair(idx.first, idx.second+1), input, visited)   //right
        count
    }
    else 0

lowPoints.map { recursiveFlow(it, input, mutableSetOf()) }.sorted().takeLast(3).reduce(Int::times).secondSolution()