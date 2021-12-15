import com.tenebraex8.aoc2021.*
import java.util.*

val cave = "15.inp".readLines().map { it.toCharArray().map { it.toString().toInt() } }


fun dijkstra(cave: List<List<Int>>, start: Index2D, distances: List<MutableList<Int>>, ancestors: List<MutableList<Index2D>>){
    val visited = List(cave.size){ MutableList(cave.first().size){false} }
    val queued = List(cave.size){ MutableList(cave.first().size){false} }
    val adj = PriorityQueue<Pair<Index2D, Int>> { o1, o2 -> o1.second - o2.second }
    adj.add(Pair(start, 0))
    while(adj.isNotEmpty()) {
        val cur = adj.poll().first
        visited.pairAssign(cur, true)

        cave.adjacents2DNonDiagonal(cur).forEach {
            val weight = distances.pairIdx(cur) + cave.pairIdx(it)
            if (distances.pairIdx(it) > weight) {
                distances.pairAssign(it, weight)
                ancestors.pairAssign(it, cur)
            }
            if (!visited.pairIdx(it) && !queued.pairIdx(it)) {
                adj.add(Pair(it, weight))
                queued.pairAssign(it, true)
            }
        }
    }
}

val distances = List(cave.size){
    MutableList(cave.first().size){Int.MAX_VALUE}
}
distances[0][0] = 0

val ancestors = List(cave.size){
    MutableList(cave.first().size){Pair(-1, -1)}
}

dijkstra(cave, Pair(0,0), distances, ancestors)
distances.last().last().firstSolution()

val bigCave = List(5*cave.size){line ->
    List(5*cave.first().size){ col ->
        var value = (cave[line % cave.size][col % cave.first().size] + (line / cave.size) + (col / cave.first().size))
        while(value > 9) value -= 9
        value
    }
}

val bigDistances = List(bigCave.size){
    MutableList(bigCave.first().size){Int.MAX_VALUE}
}
bigDistances[0][0] = 0

val bigAncestors = List(bigCave.size){
    MutableList(bigCave.first().size){Pair(-1, -1)}
}

dijkstra(bigCave, Pair(0,0), bigDistances, bigAncestors)
bigDistances.last().last().firstSolution()