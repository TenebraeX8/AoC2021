import com.tenebraex8.aoc2021.firstSolution
import com.tenebraex8.aoc2021.readContent
import com.tenebraex8.aoc2021.secondSolution
import com.tenebraex8.aoc2021.splitAndRemoveEmpty

val input = "6.inp".readContent().splitAndRemoveEmpty(",").map { it.toInt() }

fun calculatePopulation(input: Iterable<Int>, time: Int): Long{
    var fish = Array(9){0L}
    input.forEach { fish[it] += 1L }
    for(step in 0 until time){
        val timerUp = fish[0]
        fish = fish.sliceArray(1 until fish.size) + Array(1){timerUp}
        fish[6] += timerUp
    }
    return fish.sum()
}

calculatePopulation(input, 80).firstSolution()
calculatePopulation(input, 256).secondSolution()