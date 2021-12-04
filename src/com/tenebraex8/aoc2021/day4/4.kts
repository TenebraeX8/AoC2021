import com.tenebraex8.aoc2021.*
import com.tenebraex8.aoc2021.day4.GridOpt

var input = "4.inp".readSections()
val numbers = input.first().split(",").map { it.toInt() }
val grids = input.slice(1 until input.size).map {
    GridOpt(it.splitLine().map { it.split(" ").filter { it != "" }.map { it.toInt() }.toTypedArray() }.toTypedArray())
}.toMutableList()

for(idx in numbers.indices){
    val nr = numbers[idx]
    var winnerfound = false
    for(grid in grids){
        if(grid.check(nr)){
            (grid.uncheckedSum(numbers.slice(0..idx).toSet()) * nr).firstSolution()
            winnerfound = true
        }
        if(winnerfound) break
    }
    if(winnerfound) break
}

grids.forEach { it.reset() }

for(idx in numbers.indices){
    val nr = numbers[idx]
    val removableIndices = mutableListOf<Int>()
    for(i in grids.indices){
        if(grids[i].check(nr)){
            removableIndices.add(0,i)   //insert first as descending
            if(grids.size == 1) (grids[i].uncheckedSum(numbers.slice(0..idx).toSet()) * nr).secondSolution()
        }
    }
    for(remIdx in removableIndices) grids.removeAt(remIdx)
    if(grids.isEmpty()) break
}
