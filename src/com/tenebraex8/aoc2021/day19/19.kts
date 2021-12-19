import com.tenebraex8.aoc2021.*
import kotlin.math.abs

data class Coord3D(val x: Int, val y: Int, val z: Int){
    var rotations = mutableListOf<Coord3D>()

    fun initRotations(){
        rotations = mutableListOf(
            Coord3D( x, y, z), Coord3D( y, z, x), Coord3D( z, x, y), Coord3D( z, y,-x), Coord3D( y, x,-z), Coord3D( x, z,-y),
            Coord3D( x,-y,-z), Coord3D( y,-z,-x), Coord3D( z,-x,-y), Coord3D( z,-y, x), Coord3D( y,-x, z), Coord3D( x,-z, y),
            Coord3D(-x, y,-z), Coord3D(-y, z,-x), Coord3D(-z, x,-y), Coord3D(-z, y, x), Coord3D(-y, x, z), Coord3D(-x, z, y),
            Coord3D(-x,-y, z), Coord3D(-y,-z, x), Coord3D(-z,-x, y), Coord3D(-z,-y,-x), Coord3D(-y,-x,-z), Coord3D(-x,-z,-y)
        )
    }

    fun distanceTo(other: Coord3D) = abs(this.x - other.x) + abs(this.y - other.y) + abs(this.z - other.z)


    operator fun plus(other: Coord3D) = Coord3D(this.x + other.x, this.y + other.y, this.z + other.z)
    operator fun minus(other: Coord3D) = Coord3D(this.x - other.x, this.y - other.y, this.z - other.z)
    operator fun get(idx: Int) = rotations[idx]

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coord3D

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }
    override fun toString() = "($x, $y, $z)"

    companion object{
        val ROTATION_COUNT = 24
    }
}

class BeaconScanner(val coordinates: MutableList<Coord3D>){
    val INTERSECTION_THRESHOLD = 12

    var location = Coord3D(0,0,0)
    var rotationIdx = 0

    fun compareTo(other: BeaconScanner): Boolean{
        val refRelatives = relativePoints(0)
        for(i in 0 until Coord3D.ROTATION_COUNT){   //iterate possible rotations
            val otherRelatives = other.relativePoints(i)
            refRelatives.forEach { point1 ->
                otherRelatives.forEach{ point2 ->
                    if(point1.key != point2.key && point1.value.intersect(point2.value).count() >= INTERSECTION_THRESHOLD){ //offset
                        other.location = point1.key - point2.key  //resulting delta is location
                        other.rotationIdx = i
                        val correctedCoordinates = other.coordinates.map { it[other.rotationIdx] + other.location }.onEach { it.initRotations() }
                        other.coordinates.clear()
                        other.coordinates.addAll(correctedCoordinates)
                        return true
                    }
                }
            }
        }
        return false
    }

    fun relativePoints(rotation: Int) = coordinates.map { it[rotation] }.associateWith { key ->
        coordinates.map { it[rotation] - key }
    }

    companion object{
        fun create(coords: List<String>): BeaconScanner{
            val innerCoordinates = mutableListOf<Coord3D>()
            coords.forEach {
                val (x, y, z) = it.splitAndRemoveEmpty(",").map { it.toInt() }
                innerCoordinates.add(Coord3D(x,y,z).also { it.initRotations() })
            }
            return BeaconScanner(innerCoordinates)
        }
    }
}


val input = "19.inp".readSections().map { it.splitLine() }
                                   .map { it.slice(1 until it.size)}
                                   .map { BeaconScanner.create(it) }

val identified = mutableListOf(input.first())
val unidentified = input.slice(1 until input.size).toMutableList()

while(unidentified.size > 0){
    val newlyIdentified = mutableListOf<Int>()
    identified.forEach {refScanner ->
        unidentified.indices.forEach { otherIdx ->
            val otherScanner = unidentified[otherIdx]
            if(refScanner.compareTo(otherScanner)) newlyIdentified.add(otherIdx)
        }
    }
    newlyIdentified.sortedDescending().forEach {
        identified.add(unidentified.removeAt(it))
    }
}

identified.flatMap { it.coordinates }.distinct().count().firstSolution()

identified.map { it.location }.maxOf {l1->
    identified.map { it.location }.maxOf {l2 ->
        l1.distanceTo(l2)
    }
}.secondSolution()