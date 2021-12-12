import com.tenebraex8.aoc2021.*

data class Cave(val name: String){
    val successors = mutableListOf<Cave>()
    val bigCave = name.first().isUpperCase()

    fun add(cave: Cave) = successors.add(cave)

    fun pathsTo(goal: Cave, curPath: MutableList<Cave>): List<List<Cave>>{
        if(this.bigCave || this !in curPath) {
            curPath.add(this)
            if (this == goal) return listOf(curPath)
            val paths = mutableListOf<List<Cave>>()
            successors.forEach {
                val successorPath = curPath.toMutableList()
                paths.addAll(it.pathsTo(goal, successorPath))
            }
            return paths
        }
        else return emptyList()
    }

    fun multivisitPathsTo(start:Cave, goal: Cave, curPath: MutableList<Cave>, multivisited: Boolean = false): List<List<Cave>>{
        var hasMultivisit = multivisited
        var visit = this.bigCave || this !in curPath
        if(!visit && !hasMultivisit && this != start) {
            hasMultivisit = true
            visit = true
        }
        if(visit) {
            curPath.add(this)
            if (this == goal) return listOf(curPath)
            val paths = mutableListOf<List<Cave>>()
            successors.forEach {
                val successorPath = curPath.toMutableList()
                paths.addAll(it.multivisitPathsTo(start, goal, successorPath, hasMultivisit))
            }
            return paths
        }
        else return emptyList()
    }
}

val lines = "12.inp".readLines().map { it.splitTwo("-") }

val caves = lines.flatMap { listOf(it.first, it.second) }.distinct().map { it to Cave(it)}.toMap()
lines.forEach {
    caves[it.first]!!.add(caves[it.second]!!)
    caves[it.second]!!.add(caves[it.first]!!)
}

caves["start"]!!.pathsTo(caves["end"]!!, mutableListOf()).count().firstSolution()
caves["start"]!!.multivisitPathsTo(caves["start"]!!, caves["end"]!!, mutableListOf()).count().secondSolution()