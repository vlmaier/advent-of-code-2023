/**
 * --- Day 6: Wait For It ---
 */
fun main() {
    solveDay7Part1().let { println("Part 1: $it") }
    // solveDay7Part2().let { println("Part 2: $it") }
}

fun solveDay7Part1(): Int {
    val input = readInput("day07")
    val hands = input.associate {
        it.split(" ")[0] to it.split(" ")[1].toInt() to 0
    }.toMutableMap()
    findRank(hands)
    return 0
}

private fun findRank(hands: MutableMap<Pair<String, Int>, Int>) {
    hands.keys.forEach {
        findType(it.first)
    }
}

private fun findType(hand: String) {
    countOverallEqualChars(hand).forEach { (char, count) ->
        println("$hand: $char occurs $count times.")
    }
}

fun countOverallEqualChars(input: String): Map<Char, Int> {
    val charCounts = mutableMapOf<Char, Int>()
    for (char in input) {
        charCounts[char] = charCounts.getOrDefault(char, 0) + 1
    }
    return charCounts
}

enum class Type(val strength: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1),
}