import com.tenebraex8.aoc2021.*
import com.tenebraex8.aoc2021.datastructures.Array2D
import com.tenebraex8.aoc2021.datastructures.toArray2D
import java.lang.StringBuilder

val (section1, section2) = "20.inp".readSections()

val algorithm = section1.trim().map { it == '#' }.toTypedArray()
var image = section2.trim().splitLine().map { it.map { it == '#' } }.toArray2D()

fun applyAlgorithm(algorithm: Array<Boolean>, image: Array2D<Boolean>, offset: Int, infinityPadding: Boolean): Array2D<Boolean> {
    val outImage = Array2D(image.n + 2*offset, image.m + 2*offset){false}
    image.frameIndices(offset).forEach { idx ->
        val builder = StringBuilder()
        idx.kernel().forEach {
            if(image.inBounds(it)) builder.append(if (image[it]) "1" else "0")
            else                   builder.append(if (infinityPadding) "1" else "0")
        }
        val algorithmIdx = builder.toString().toInt(2)
        outImage[idx.inc(offset)] = algorithm[algorithmIdx]
    }
    return outImage
}

fun Array2D<Boolean>.output(){
    this.lines().forEach {
        it.map { if(it) "#" else "." }.collect().doPrintln()
    }
}

val paddingOdd = algorithm.first()  //odd occurs after even, in even, there will be lit up fields
val paddingEven = algorithm.last()  //even occurs after even or in the beginning, thus oscillating back

for(step in 0 until 50){
    image = applyAlgorithm(algorithm, image, 1, if(step % 2 == 0) paddingEven else paddingOdd)
    if(step == 1) image.count { it }.firstSolution()
}
image.count { it }.secondSolution()