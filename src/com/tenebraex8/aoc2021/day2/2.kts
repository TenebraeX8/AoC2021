import com.tenebraex8.aoc2021.*

val input = "2.inp".readLines()

var location = Pair(0,0)

input.forEach {
    var (direction, steps) = it.splitTwo(" ").mapTo { Pair(it.first, it.second.toInt()) }
    location = when(direction){
        "forward" -> location.mapTo { Pair(location.first + steps, location.second) }
        "up" -> location.mapTo { Pair(location.first, location.second - steps) }
        "down" -> location.mapTo { Pair(location.first, location.second + steps) }
        else -> {
            print("Illegal direction $direction")
            location
        }
    }
}
(location.first * location.second).firstSolution()

var location2 = Triple(0,0,0)
input.forEach {
    var (direction, steps) = it.splitTwo(" ").mapTo { Pair(it.first, it.second.toInt()) }
    location2 = when(direction){
        "forward" -> location2.mapTo { Triple(location2.first + steps, location2.second + (location2.third * steps), location2.third) }
        "up" -> location2.mapTo { Triple(location2.first, location2.second, location2.third - steps) }
        "down" -> location2.mapTo { Triple(location2.first, location2.second, location2.third + steps) }
        else -> {
            print("Illegal direction $direction")
            location2
        }
    }
}
(location2.first * location2.second).secondSolution()
