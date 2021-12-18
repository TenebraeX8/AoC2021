import com.tenebraex8.aoc2021.*
import kotlin.math.max

val (xBounds, yBounds) = "17.inp".readContent()
                                 .remove("target area: ")
                                 .splitTwo(", ")
                                 .mapping { it.trim().substring(2).splitTwo("..").mapping { it.toInt() } }

val xRange = if(xBounds.first < xBounds.second) xBounds.first..xBounds.second else xBounds.second..xBounds.first
val yRange = if(yBounds.first < yBounds.second) yBounds.first..yBounds.second else yBounds.second..yBounds.first

fun computeValue(xVel: Int, yVel: Int): Pair<Int, Boolean>{
    var (x, y) = Pair(0, 0)
    var (curXVel, curYVel) = Pair(xVel, yVel)
    var max = 0
    var hitArea = false

    while(x < xRange.last && y > yRange.first){   //brute force
        x += curXVel
        y += curYVel
        curXVel += if(curXVel > 0) -1 else (if(curXVel < 0) 1 else 0)
        curYVel -= 1
        max = max(max, y)
        if((x in xRange) && (y in yRange)) hitArea = true
    }
    return Pair(max, hitArea)
}
var count = 0
(yRange.first..500).maxOf {yVel->
    (0..xRange.last).maxOf { xVel->
        val (value, hit) = computeValue(xVel, yVel)
        if(hit){
            count++
            value
        }
        else 0
    }
}.firstSolution()
count.secondSolution()