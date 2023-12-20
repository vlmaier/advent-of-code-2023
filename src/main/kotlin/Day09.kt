
fun main() {
    val input = readInput("day09")
    solveDay9Part1(input).let { println("Part 1: $it") }
}

fun solveDay9Part1(input: List<String>): Int {
    return input.sumOf { extrapolateNextValue(it) }
}

fun extrapolateNextValue(history: String): Int {
    val values = history.split(" ").map { it.toInt() }.toMutableList()
    var sequences: MutableList<MutableList<Int>> = mutableListOf(values)
    while (true) {
        val differences = generateDifferences(sequences.last().toMutableList())
        sequences = sequences.plusElement(differences).toMutableList()
        if (differences.all { it == 0 }) {
            break
        }
    }
    return fillPlaceholders(sequences.toMutableList()).last()
}

fun generateDifferences(sequence: List<Int>): MutableList<Int> {
    return sequence.zipWithNext { a, b -> b - a }.toMutableList()
}

fun fillPlaceholders(sequences: MutableList<MutableList<Int>>): List<Int> {
    for (i in sequences.size - 2 downTo 0) {
        for (j in 0..<sequences[i].size) {
            sequences[i][j] = sequences[i][j] + sequences[i + 1][j.coerceAtMost(sequences[i + 1].size - 1)]
        }
    }
    return sequences.first()
}
