import com.tenebraex8.aoc2021.doPrintln
import com.tenebraex8.aoc2021.firstSolution
import com.tenebraex8.aoc2021.readLines
import com.tenebraex8.aoc2021.secondSolution

val input = "10.inp".readLines()
val opening = setOf('(', '[', '{', '<')
val soundnessMap = mapOf(
    ')' to '(',
    ']' to '[',
    '}' to '{',
    '>' to '<'
)

val corruptedScoreMap = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

var stack = mutableListOf<Char>()
var corruptedScore = 0
val remainingStacks = mutableListOf<MutableList<Char>>()
input.forEach { line ->
    var corruped = false
    for(value in line){
        if(value in opening) stack.add(0, value)
        else{
            val last = stack.removeFirst()
            if(soundnessMap[value] != last) {
                corruptedScore += corruptedScoreMap[value] ?: 0
                corruped = true
                break
            }
        }
    }
    if(!corruped) remainingStacks.add(stack)
    stack = mutableListOf()    //clear
}
corruptedScore.firstSolution()

val reverseSoundnessMap = soundnessMap.map { it.value to it.key}.toMap()
val repairScoreMap = mapOf<Char, Int>(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4
)

var scores = mutableListOf<Long>()
remainingStacks.forEach { stack ->
    var curScore = 0L
    stack.forEach {
        val missing = reverseSoundnessMap[it]
        curScore = (curScore * 5L) + (repairScoreMap[missing] ?: 0).toLong()
    }
    scores.add(curScore)
}

scores.sorted()[scores.size / 2].secondSolution()