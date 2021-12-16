import com.tenebraex8.aoc2021.collect
import com.tenebraex8.aoc2021.firstSolution
import com.tenebraex8.aoc2021.readContent
import com.tenebraex8.aoc2021.secondSolution
import java.lang.IllegalArgumentException

val mapping = mapOf(
    '0' to "0000",
    '1' to "0001",
    '2' to "0010",
    '3' to "0011",
    '4' to "0100",
    '5' to "0101",
    '6' to "0110",
    '7' to "0111",
    '8' to "1000",
    '9' to "1001",
    'A' to "1010",
    'B' to "1011",
    'C' to "1100",
    'D' to "1101",
    'E' to "1110",
    'F' to "1111"
)

class Packet{
    var version: String = ""
    var type: String = ""
    var numberBits = mutableListOf<String>()

    var lengthType = ""
    var subPacketCount = 0
    val subPackets = mutableListOf<Packet>()

    private var curIdx = 0

    fun read(sequence: String, startIdx: Int): Int{
        curIdx = startIdx
        version = sequence.substring(curIdx..curIdx+2)
        curIdx += 3
        type = sequence.substring(curIdx..curIdx+2)
        curIdx += 3
        curIdx = when(type){
            "100" -> readLiteral(sequence, curIdx)
            else -> readOperator(sequence, curIdx)
        }
        return curIdx
    }

    private fun readLiteral(sequence: String, startIdx: Int): Int{
        curIdx = startIdx
        var nextPackageExist = true
        while(nextPackageExist){
            nextPackageExist = sequence[curIdx] == '1'
            numberBits.add(sequence.substring(curIdx+1..curIdx+4))
            curIdx += 5
        }
        return curIdx
    }

    private fun readOperator(sequence: String, startIdx: Int): Int{
        curIdx = startIdx
        lengthType = sequence[curIdx].toString()
        curIdx++
        if(lengthType == "0"){
            subPacketCount = sequence.substring(curIdx..curIdx+14).toInt(2)
            curIdx += 15
            val goal = curIdx + subPacketCount
            while(curIdx < goal) curIdx = Packet().also{ this.subPackets.add(it) }.read(sequence, curIdx)
        }
        else{
            subPacketCount = sequence.substring(curIdx..curIdx+10).toInt(2)
            curIdx += 11
            for(subPacketIdx in (0 until subPacketCount)) curIdx = Packet().also{ this.subPackets.add(it) }.read(sequence, curIdx)
        }
        return curIdx
    }

    fun versionNumber(): Int = this.version.toInt(2) + subPackets.sumOf { it.versionNumber() }
    fun value(): Long = when(type){
        "100" -> this.numberBits.collect().toLong(2)
        "000" -> this.subPackets.fold(0){acc, packet -> acc + packet.value() }
        "001" -> this.subPackets.fold(1) {acc, packet -> acc * packet.value() }
        "010" -> this.subPackets.minOf { it.value() }
        "011" -> this.subPackets.maxOf { it.value() }
        "101" -> if(this.subPackets.first().value() > this.subPackets[1].value()) 1 else 0
        "110" -> if(this.subPackets.first().value() < this.subPackets[1].value()) 1 else 0
        "111" -> if(this.subPackets.first().value() == this.subPackets[1].value()) 1 else 0
        else -> throw IllegalArgumentException("$type is no valid opcode")
    }

}

class PacketReader{
    companion object{
        fun read(input: String): List<Packet>{
            var idx = 0
            val packets = mutableListOf<Packet>()
            while(idx < input.count()) {
                idx = Packet().apply { packets.add(this) }.read(input, idx)
                if(input.count() <= idx + 8) break
            }
            return packets
        }
    }
}

val input = "16.inp".readContent().trim().map {mapping[it] }.joinToString("")

val packets =  PacketReader.read(input)
packets.sumBy { it.versionNumber() }.firstSolution()
packets.first().value().secondSolution()