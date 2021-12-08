import com.tenebraex8.aoc2021.*

val input = "8.inp".readLines().map { it.splitTwo("|") }.map { it.mapTo { Pair(it.first.splitAndRemoveEmpty(" "), it.second.splitAndRemoveEmpty(" ")) } }
val digitCount = arrayOf(6, 2, 5, 5, 4, 5, 6, 3, 7, 6)
input.map { it.second }.map { it.map { it.length } }.sumBy { it.count { it in digitCount.sliceArray(listOf(1, 4, 7, 8)).toList() }}.firstSolution()

val sorted = input.map { it.mapTo { Pair(it.first.map { it.toCharArray().sorted().joinToString("") }, it.second.map { it.toCharArray().sorted().joinToString("") }) } }
fun mappingArray(values: List<String>): Array<String>{
    val mapping = Array(10){""}
    mapping[1] = values.first{it.length == 2}
    mapping[4] = values.first{it.length == 4}
    mapping[7] = values.first{it.length == 3}
    mapping[8] = values.first{it.length == 7}

    mapping[9] = values.first{digits -> (digits.length == 6) and mapping[4].all { it in digits }}
    mapping[6] = values.first{digits -> (digits.length == 6) and mapping[1].any { it !in digits }}
    mapping[0] = values.first { (it.length == 6) and (it != mapping[9]) and (it != mapping[6]) }

    mapping[3] = values.first { digits -> (digits.length == 5) and mapping[1].all{it in digits} }
    mapping[5] = values.first { digits -> (digits.length == 5) and (mapping[6].count { it !in digits } == 1) }
    mapping[2] = values.first { it !in mapping }
    return mapping
}

var sum = 0
for(line in sorted){
    val mapping = mappingArray(line.first)
    sum += line.second.map { mapping.indexOf(it).toString() }.collect().toInt()
}
sum.secondSolution()