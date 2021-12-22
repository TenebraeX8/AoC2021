import com.tenebraex8.aoc2021.*
import kotlin.math.max
import kotlin.math.min

data class Range3D(val x: IntRange, val y: IntRange, val z: IntRange){
    operator fun minus(other: Range3D): List<Range3D>{
        val splitting = mutableListOf<Range3D>()
        if(overlaps(other)){
            val overlapping = this.boundBy(other)
            if(overlapping.x.first != this.x.first) splitting.add(Range3D(this.x.first until overlapping.x.first, this.y, this.z))
            if(overlapping.x.last != this.x.last) splitting.add(Range3D((overlapping.x.last + 1)..this.x.last, this.y, this.z))
            if(overlapping.y.first != this.y.first) splitting.add(Range3D(overlapping.x, this.y.first until overlapping.y.first, this.z))
            if(overlapping.y.last != this.y.last) splitting.add(Range3D(overlapping.x, (overlapping.y.last + 1)..this.y.last, this.z))
            if(overlapping.z.first != this.z.first) splitting.add(Range3D(overlapping.x, overlapping.y, this.z.first until overlapping.z.first))
            if(overlapping.z.last != this.z.last) splitting.add(Range3D(overlapping.x, overlapping.y, (overlapping.z.last + 1)..this.z.last))
        }
        else splitting.add(this)
        return splitting.filter { it.count() > 0 }
    }

    fun overlaps(other: Range3D) =
        ((this.x.last >= other.x.first) && (this.x.first <= other.x.last)) &&
        ((this.y.last >= other.y.first) && (this.y.first <= other.y.last)) &&
        ((this.z.last >= other.z.first) && (this.z.first <= other.z.last))

    fun boundBy(bounds: Range3D) = Range3D(
        max(x.first, bounds.x.first)..min(x.last, bounds.x.last),
        max(y.first, bounds.y.first)..min(y.last, bounds.y.last),
        max(z.first, bounds.z.first)..min(z.last, bounds.z.last)
    )

    fun count() = x.count().toLong() * y.count().toLong() * z.count().toLong()

    override fun toString() = "(x=${x.first}..${x.last}, y=${y.first}..${y.last}, z=${z.first}..${z.last})"
    companion object{
        fun fromInput(value: String): Range3D{
            val (xRange, yRange, zRange) = value.split(",").map { it.substring(2) }
            return Range3D(
                xRange.splitTwo("..").mapping { it.toInt() }.toRange(),
                yRange.splitTwo("..").mapping { it.toInt() }.toRange(),
                zRange.splitTwo("..").mapping { it.toInt() }.toRange()
            )
        }
   }
 }


val input = "22.inp".readLines().map { it.splitTwo(" ") }
val state = input.map { it.first == "on" }
val ranges = input.map { Range3D.fromInput(it.second.trim()) }
val bound = Range3D(-50..50,-50..50,-50..50)

val boundedRanges = ranges.indices.map { Pair(state[it], ranges[it].boundBy(bound)) }.filter { it.second.count() > 0 }

var litRanges = mutableListOf<Range3D>()

boundedRanges.forEach {(lit, range)->
    val curRanges = mutableListOf<Range3D>()
    litRanges.forEach {
        curRanges.addAll(it - range)
    }
    if(lit) curRanges.add(range)
    litRanges = curRanges
}
litRanges.sumOf { it.count() }.firstSolution()

litRanges.clear()
ranges.indices.map { Pair(state[it], ranges[it]) }.forEach {(lit, range)->
    val curRanges = mutableListOf<Range3D>()
    litRanges.forEach {
        curRanges.addAll(it - range)
    }
    if(lit) curRanges.add(range)
    litRanges = curRanges
}
litRanges.sumOf { it.count() }.secondSolution()