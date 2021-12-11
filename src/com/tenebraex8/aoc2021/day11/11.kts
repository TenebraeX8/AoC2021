import com.tenebraex8.aoc2021.*

data class Octopus(var value: Int){
    val THRESHHOLD = 9
    val adjacents = mutableListOf<Octopus>()
    var flashed = false

    fun setAdjacent(octopus: Octopus) =  adjacents.add(octopus)
    fun inc() = value++
    fun flash() =
        if(value > THRESHHOLD && !flashed){
            adjacents.forEach(Octopus::inc)
            flashed = true
            true
        }
        else false

    fun reset(){
        if(value > THRESHHOLD) value = 0
        flashed = false
    }
}

val content = "11.inp".readLines().map { it.splitAndRemoveEmpty("").map { Octopus(it.toInt()) } }

content.indices.forEach { lineIdx->
    content[lineIdx].indices.forEach { colIdx ->
        val current = content[lineIdx][colIdx]
        content.adjacents2D(lineIdx, colIdx).forEach { current.setAdjacent(content.pairIdx(it)) }
    }
}

val octopus = content.flatten()
var flashes = 0
var step = 1
var (part1Done, part2Done) = Pair(false, false)
while(!part1Done || !part2Done) {
    octopus.forEach { it.inc() }
    var flashed = true
    while(flashed){
        flashed = octopus.any { it.flash() }
    }
    flashes += octopus.count { it.flashed }
    if(octopus.all { it.flashed }) {
        step.secondSolution()
        part2Done = true
    }
    octopus.forEach { it.reset() }
    if(step == 100) {
        flashes.firstSolution()
        part1Done = true
    }
    step++
}