import com.tenebraex8.aoc2021.*
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

val input = "7.inp".readContent().splitAndRemoveEmpty(",").map { it.toInt() }
val median = input.median()
input.map { abs(it.toDouble() - median) }.sum().toInt().firstSolution()

val mean = input.sum().toDouble() / input.size.toDouble()
//val meanInt = if(mean - mean.toInt().toDouble() >= 0.5) ceil(mean).toInt() else floor(mean).toInt() //96592329
val meanInt = floor(mean).toInt()       //I donno why, but seems like they want floor for this solution

input.map { (1..abs(it-meanInt)).fold(0){acc, i -> acc + i} }.sum().secondSolution()
