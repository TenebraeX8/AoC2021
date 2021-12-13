import com.tenebraex8.aoc2021.*

fun Array<Array<Boolean>>.visualize(){
    this.forEach {
        it.forEach { if(it) print("#") else print(" ") }
        println()
    }
}

val (dotList, foldings) = "13.inp".readSections().map { it.splitLine() }
var dots = dotList.map { it.splitTwo(",").mapTo { Pair(it.first.toInt(), it.second.toInt()) } }
var foldInstr = foldings.map { it.split(" ").last().splitTwo("=").mapTo { Pair(it.first, it.second.toInt()) } }

var maxX = dots.maxOf { it.first }
var maxY = dots.maxOf { it.second }

var paper = Array(maxY + 1){Array(maxX + 1){false} }
dots.forEach { paper[it.second][it.first] = true }

var first = true
foldInstr.forEach {(along, foldIdx) ->
    if(along == "x"){
        paper = Array(paper.size){line->
            Array(foldIdx){ col->
                val mirrorIdx = foldIdx + (foldIdx - col)
                paper[line][col] || ((mirrorIdx in paper.first().indices) && paper[line][mirrorIdx])
            }
        }
    }
    else{
        paper = Array(foldIdx){line->
            val mirrorIdx = foldIdx + (foldIdx - line)
            Array(paper.first().size){ col ->
                paper[line][col] || ((mirrorIdx in paper.indices) && paper[mirrorIdx][col])
            }
        }
    }
    if(first){
        paper.sumBy {  it.count { it } }.firstSolution()
        first = false
    }
}
"".secondSolution()
paper.visualize()