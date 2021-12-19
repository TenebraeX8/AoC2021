import com.tenebraex8.aoc2021.*
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.math.ceil
import kotlin.math.floor

class SnailfishNumber(var value: Int? = null) {
    constructor(leftSide: SnailfishNumber, rightSide: SnailfishNumber) : this() {
        setPair(leftSide,rightSide)
    }

    var left: SnailfishNumber? = null
    var right: SnailfishNumber? = null
    var parent: SnailfishNumber? = null
    var isLeftBranch: Boolean = false
    var depth: Int = 0

    fun isValue() = value != null

    fun magnitude(): Int =
        if(this.isValue()) value!!
        else 3*left!!.magnitude() + 2*right!!.magnitude()

    fun setPair(left: SnailfishNumber?, right: SnailfishNumber?){
        left?.setParent(this, true)
        right?.setParent(this, false)
        this.left = left
        this.right = right
    }

    fun setParent(parent: SnailfishNumber, left: Boolean){
        this.parent = parent
        this.isLeftBranch = left
    }

    fun depthCorrection(depth: Int){
        this.depth = depth
        this.left?.depthCorrection(depth + 1)
        this.right?.depthCorrection(depth + 1)
    }

    operator fun plus(other: SnailfishNumber): SnailfishNumber{
        val pair = SnailfishNumber(this.copy(), other.copy())
        pair.depthCorrection(this.depth)
        return pair
    }

    fun fullReduction(){
        var reduction: Boolean
        do {
            reduction = false
            while (true) {
                try {
                    this.applyExplosion()
                } catch (ex: ExecutionStopException) {
                    reduction = true
                    continue
                }
                break
            }
            try {
                this.applySplit()
            } catch (ex: ExecutionStopException) {
                reduction = true
            }
            this.depthCorrection(0)
        }
        while(reduction)
    }

    fun applyExplosion(){
        if(!this.isValue() && this.depth >= 4) explode()   // go to the pair where the numbers are contained
        this.left?.applyExplosion()
        this.right?.applyExplosion()
    }

    fun applySplit(){
        if(this.isValue() && this.value!! >= 10) split()
        this.left?.applySplit()
        this.right?.applySplit()
    }

    fun split(){
        left = SnailfishNumber(floor(value!! / 2.0).toInt()).also { it.setParent(this, true)}
        right = SnailfishNumber(ceil(value!! / 2.0).toInt()).also { it.setParent(this, false) }
        this.value = null
        throw ExecutionStopException()
    }

    fun explode(){
        val rightSucc = this.inOrderSuccessor()
        if(rightSucc != null) rightSucc.value = rightSucc.value!! + this.right!!.value!!
        val leftSucc = this.inOrderPredecessor()
        if(leftSucc != null) leftSucc.value = leftSucc.value!! + this.left!!.value!!

        this.value = 0
        this.left = null
        this.right = null
        throw ExecutionStopException()
    }

    fun inOrderSuccessor(): SnailfishNumber?{
        var cur: SnailfishNumber
        if(this.isLeftBranch){
            cur = this.parent ?: return null
            cur = cur.right!!
            while(!cur.isValue()) cur = cur.left!!
            return cur
        }
        else{
            cur = this.parent ?: return null
            while(!cur.isLeftBranch) cur = cur.parent ?: return null
            cur = cur.parent?.right ?: return null
            while(!cur.isValue()) cur = cur.left!!
            return cur
        }
    }

    fun inOrderPredecessor(): SnailfishNumber?{
        var cur: SnailfishNumber
        if(this.isLeftBranch){
            cur = this.parent ?: return null
            while(cur.isLeftBranch) cur = cur.parent ?: return null
            cur = cur.parent?.left ?: return null
            while(!cur.isValue()) cur = cur.right!!
            return cur
        }
        else{
            cur = this.parent ?: return null
            cur = cur.left!!
            while(!cur.isValue()) cur = cur.right!!
            return cur
        }
    }

    fun copy(): SnailfishNumber{
        val copy = SnailfishNumber(value)
        copy.depth = this.depth
        copy.setPair(this.left?.copy(), this.right?.copy())
        return copy
    }

    override fun toString() =
        if(isValue()) "$value"
        else "[${left.toString()},${right.toString()}]"

    companion object{
        fun parse(input: String): SnailfishNumber{
            val stack = mutableListOf<SnailfishNumber>()
            var uppermostElement: SnailfishNumber? = null
            input.indices.forEach {idx ->
                when(input[idx]){
                    '[' -> stack.push(SnailfishNumber())
                    ']' -> {
                        val right = stack.pop()!!
                        val left = stack.pop()!!
                        val elem = stack.peek()!!
                        elem.setPair(left, right)
                        if(stack.size == 1) uppermostElement = elem
                    }
                    in "0123456789" -> stack.push(SnailfishNumber(input[idx].toString().toInt()))
                }
            }
            uppermostElement?.depthCorrection(0)
            return uppermostElement ?: throw IllegalStateException("Parsing failed!")
        }
    }
}

class ExecutionStopException : Exception("")

val values = "18.inp".readLines().map { SnailfishNumber.parse(it)}
var cur = values.first()
for(number in values.subList(1, values.size)){
    cur += number
    cur.fullReduction()
}
cur.magnitude().firstSolution()

values.indices.maxOf { idx1 ->
    values.indices.maxOf { idx2 ->
        if(idx1 != idx2) (values[idx1] + values[idx2]).apply { this.fullReduction() }.magnitude()
        else Int.MIN_VALUE
    }
}.secondSolution()