import com.tenebraex8.aoc2021.day4.Grid
import com.tenebraex8.aoc2021.firstSolution
import com.tenebraex8.aoc2021.readSections
import com.tenebraex8.aoc2021.secondSolution
import com.tenebraex8.aoc2021.splitLine

var input = "4.inp".readSections()
val numbers = input.first().split(",").map { it.toInt() }
val grids = input.slice(1 until input.size).map {
    Grid(it.splitLine().map { it.split(" ").filter { it != "" }.map { it.toInt() }.toTypedArray() }.toTypedArray())
}.toMutableList()

for (nr in numbers) {
    var winnerfound = false
    for (grid in grids) {
        if (grid.check(nr)) {
            (grid.uncheckedSum() * nr).firstSolution()
            winnerfound = true
        }
    }
    if (winnerfound) break
}

grids.forEach { it.reset() }

for (nr in numbers) {
    val removableIndices = mutableListOf<Int>()
    for (i in grids.indices) {
        if (grids[i].check(nr)) {
            removableIndices.add(0, i)   //insert first as descending
            if (grids.size == 1) (grids[i].uncheckedSum() * nr).secondSolution()
        }
    }
    for (idx in removableIndices) grids.removeAt(idx)
    if (grids.isEmpty()) break
}