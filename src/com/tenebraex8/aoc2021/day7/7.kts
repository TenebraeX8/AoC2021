import com.tenebraex8.aoc2021.*
import kotlin.math.abs
import kotlin.math.floor

val input = "7.inp".readContent().splitAndRemoveEmpty(",").map { it.toInt() }
val median = input.median()
input.map { abs(it - median) }.sum().firstSolution()
val mean = floor(input.sum().toDouble() / input.size.toDouble()).toInt()       //I donno why, but seems like they want floor for this solution
input.map { (1..abs(it-mean)).sum() }.sum().secondSolution()