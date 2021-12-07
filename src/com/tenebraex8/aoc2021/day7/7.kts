import com.tenebraex8.aoc2021.*
import kotlin.math.abs
import kotlin.math.floor

val input = "7.inp".readContent().splitAndRemoveEmpty(",").map { it.toInt() }
val median = input.median()
input.map { abs(it.toDouble() - median) }.sum().toInt().firstSolution()

val mean = input.sum().toDouble() / input.size.toDouble()
val meanInt = floor(mean).toInt()       //I donno why, but seems like they want floor for this solution

input.map { (1..abs(it-meanInt)).sum() }.sum().secondSolution()
