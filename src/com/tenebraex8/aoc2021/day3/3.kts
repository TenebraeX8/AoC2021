import com.tenebraex8.aoc2021.*


val content = "3.inp".readLines()
val bitArray = Array(content.first().length) { 0 }
content.forEach {
    for(idx in it.indices) bitArray[idx] += if(it[idx] == '0') -1 else 1
}
val gamma = bitArray.map { if(it > 0) "1" else "0" }.collect()
val epsilon = bitArray.map { if(it <= 0) "1" else "0" }.collect()
(gamma.toInt(2) * epsilon.toInt(2)).firstSolution()

var oxygen = 0
var remnants = content
val buckets = Pair(mutableListOf<String>(), mutableListOf<String>())
for(idx in gamma.indices){
    buckets.first.clear(); buckets.second.clear()
    remnants.forEach {
        if(it[idx] == '0') buckets.first.add(it)
        else buckets.second.add(it)
    }
    remnants = if(buckets.first.size > buckets.second.size) buckets.first.copy() else buckets.second.copy()
    if(remnants.size == 1){
        oxygen = remnants.first().toInt(2)
        break
    }
}
var scrubber = 0
remnants = content
for(idx in gamma.indices){
    buckets.first.clear(); buckets.second.clear()
    remnants.forEach {
        if(it[idx] == '0') buckets.first.add(it)
        else buckets.second.add(it)
    }
    remnants = if(buckets.first.size <= buckets.second.size) buckets.first.copy() else buckets.second.copy()
    if(remnants.size == 1){
        scrubber = remnants.first().toInt(2)
        break
    }
}

(scrubber * oxygen).secondSolution()