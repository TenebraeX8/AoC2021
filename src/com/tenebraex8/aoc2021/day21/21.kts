import com.tenebraex8.aoc2021.firstSolution
import com.tenebraex8.aoc2021.readLines
import com.tenebraex8.aoc2021.secondSolution

val (player1, player2) = "21.inp".readLines().map { it.last().toString().toInt() }
//val (player1, player2) = Pair(4,8)

val field = (1..10).toList().toTypedArray()
val player1Start = field.indexOf(player1)
val player2Start = field.indexOf(player2)


class PractiseGame(var player1Score: Int = 0, var player2Score: Int = 0) {
    val field = (1..10).toList().toTypedArray()
    fun deterministicDice(diceRoll: Int) = (diceRoll % 100) + 1

    fun rollDice(diceRoll: Int) =
        deterministicDice(diceRoll) + deterministicDice(diceRoll + 1) + deterministicDice(diceRoll + 2)

    fun play(player1Start: Int, player2Start: Int, goal: Int): Int{
        var player1 = player1Start
        var player2 = player2Start
        var diceRoll = 0
        while(player2Score < goal)
        {
            player1 = (player1 + rollDice(diceRoll)) % 10
            player1Score += field[player1]
            diceRoll += 3
            if (player1Score >= goal) {
                return player2Score * diceRoll
            }
            player2 = (player2 + rollDice(diceRoll)) % 10
            player2Score += field[player2]
            diceRoll += 3
        }
        return player1Score * diceRoll
    }
}
val game = PractiseGame()
game.play(player1Start, player2Start, 1000).firstSolution()

class DiracDiceGame(var player1Score: Int = 0, var player2Score: Int = 0) {
    fun play(player1: Int, player2: Int, goal: Int): Pair<Long, Long>{
        var player1Won = 0L
        var player2Won = 0L

        var worlds = mutableMapOf(WorldState(player1Score, player2Score, player1, player2) to 1L)
        while(worlds.count() > 0){
            val nextStepP1 = mutableMapOf<WorldState, Long>()
            worlds.forEach {
                it.key.generateNextPlayer1().forEach { world->
                    if(world.player1Score >= goal) player1Won += it.value
                    else nextStepP1[world] = (nextStepP1[world] ?: 0L) + it.value
                }
            }
            worlds = mutableMapOf()
            nextStepP1.forEach {
                it.key.generateNextPlayer2().forEach { world->
                    if(world.player2Score >= goal) player2Won += it.value
                    else worlds[world] = (worlds[world] ?: 0L) + it.value
                }
            }
        }
        return Pair(player1Won, player2Won)
    }

    class WorldState(val player1Score: Int, val player2Score: Int, val player1: Int, val player2: Int){
        val field = (1..10).toList().toTypedArray()
        fun generateNextPlayer1(): List<WorldState>{
            val successors = mutableListOf<WorldState>()
            for(dice1 in 1..3){
                for(dice2 in 1..3) {
                    for(dice3 in 1..3) {
                        val diceValue = dice1 + dice2 + dice3
                        val player = (player1 + diceValue) % 10
                        val playerScore = player1Score + field[player]
                        successors.add(WorldState(playerScore, player2Score, player, player2))
                    }
                }
            }
            return successors
        }

        fun generateNextPlayer2(): List<WorldState>{
            val successors = mutableListOf<WorldState>()
            for(dice1 in 1..3){
                for(dice2 in 1..3) {
                    for(dice3 in 1..3) {
                        val diceValue = dice1 + dice2 + dice3
                        val player = (player2 + diceValue) % 10
                        val playerScore = player2Score + field[player]
                        successors.add(WorldState(player1Score, playerScore, player1, player))
                    }
                }
            }
            return successors
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as WorldState

            if (player1Score != other.player1Score) return false
            if (player2Score != other.player2Score) return false
            if (player1 != other.player1) return false
            if (player2 != other.player2) return false

            return true
        }

        override fun hashCode(): Int {
            var result = player1Score
            result = 31 * result + player2Score
            result = 31 * result + player1
            result = 31 * result + player2
            return result
        }
    }
}

DiracDiceGame().play(player1Start, player2Start, 21).toList().maxOf { it }.secondSolution()