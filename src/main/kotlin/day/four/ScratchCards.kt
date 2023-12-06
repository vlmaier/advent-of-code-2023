package day.four

import java.io.File

fun main() {
    val lines = File("src/main/resources/day/four/input.txt").readLines()
    var points = 0
    for (line in lines) {
        val card = Card(line)
        val countWinningNumbers = card.countWinningNumbers()
        var pointsPerCard = if (countWinningNumbers > 0) 1 else 0
        for (i in 1..<countWinningNumbers) {
            pointsPerCard *= 2
        }
        points += pointsPerCard
    }
    println(points)
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