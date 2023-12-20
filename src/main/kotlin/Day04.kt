
fun main() {
    val input = readInput("day04")
    solveDay4Part1(input).let { println("Part 1: $it") }
    solveDay4Part2(input).let { println("Part 2: $it") }
}

fun solveDay4Part1(input: List<String>): Long {
    var points = 0L
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

fun solveDay4Part2(input: List<String>): Long {
    val winningCards = emptyList<String>().toMutableList()
    for (line in input) {
        processCard(input, winningCards, input.indexOf(line))
    }
    return winningCards.size.toLong()
}

fun processCard(allCards: List<String>, winningCards: MutableList<String>, cardIndex: Int = 0) {
    if (cardIndex >= allCards.size) {
        return
    }
    val line = allCards[cardIndex]
    val card = Card(line)
    val countWinningNumbers = card.countWinningNumbers()
    winningCards.add(line)
    for (i in 1..countWinningNumbers) {
        processCard(allCards, winningCards, i + cardIndex)
    }
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
