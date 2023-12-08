import java.io.File

fun main() {
    solveDay4Part1().let { println("Part 1: $it") }
}

fun solveDay4Part1(): Int {
    val input = readInput("day04")
    var points = 0
    for (line in input) {
        val card = Card(line)
        val countWinningNumbers = card.countWinningNumbers()
        var pointsPerCard = if (countWinningNumbers > 0) 1 else 0
        for (i in 1..<countWinningNumbers) {
            pointsPerCard *= 2
        }
        points += pointsPerCard
    }
    return points
}

data class Card(val input: String) {

    private fun extractNumbers(isWinningNumbers: Boolean): List<Int> =
        input.trim().split(":")[1]
             .trim().split("|")[if (isWinningNumbers) 0 else 1]
             .trim().split(" ")
             .filter { it.isNotEmpty() }
             .map { it.trim().toInt() }

    private val winningNumbers by lazy { extractNumbers(true) }
    private val numbers by lazy { extractNumbers(false) }

    fun countWinningNumbers() = winningNumbers.count { numbers.contains(it) }
}