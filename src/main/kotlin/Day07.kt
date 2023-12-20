
fun main() {
    val input = readInput("day07")
    solveDay7Part1(input).let { println("Part 1: $it") }
}

fun solveDay7Part1(input: List<String>): Int {
    val hands = getHands(input)
    return calculateWinnings(hands)
}

private fun getHands(input: List<String>) = input.associate {
    val (cards, bid) = it.split(" ")
    // rank default: 0 (will be filled later)
    Pair(cards, bid.toInt()) to 0
}.toMutableMap()

private fun calculateWinnings(hands: MutableMap<Pair<String, Int>, Int>): Int {
    val ratedHands = hands.toList()
        .sortedWith(
            // sort by type of hand first
            compareBy<Pair<Pair<String, Int>, Int>> { (hand, _) ->
                findType(hand.first).strength
            // sort by card rank second
            }.thenByDescending { (hand, _) ->
                hand.first.map { it.getCardRank() }.joinToString()
            })
    ratedHands.forEachIndexed { index, (hand, _) ->
        hands[hand] = index + 1
    }
    return hands.entries.sumOf { (pair, value) -> pair.second * value }
}

private fun findType(hand: String): Type {
    val occurrences = countOverallEqualChars(hand).values
    return when {
        5 in occurrences -> Type.FIVE_OF_A_KIND
        4 in occurrences -> Type.FOUR_OF_A_KIND
        3 in occurrences && 2 in occurrences -> Type.FULL_HOUSE
        3 in occurrences -> Type.THREE_OF_A_KIND
        occurrences.count { it == 2 } == 2 -> Type.TWO_PAIR
        2 in occurrences -> Type.ONE_PAIR
        else -> Type.HIGH_CARD
    }
}

private fun countOverallEqualChars(input: String): Map<Char, Int> {
    return input.groupBy { it }.mapValues { it.value.size }
}

/**
 * Custom mapping which allows alphanumeric sorting
 */
private fun Char.getCardRank(): Char {
    return when (this) {
        'A' -> 'A'
        'K' -> 'B'
        'Q' -> 'C'
        'J' -> 'D'
        'T' -> 'E'
        '9' -> 'F'
        '8' -> 'G'
        '7' -> 'H'
        '6' -> 'I'
        '5' -> 'J'
        '4' -> 'K'
        '3' -> 'L'
        '2' -> 'M'
        else -> 'N'
    }
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
